package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.FileInfoBeanDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.CompanyInfoContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyInfoPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_INFO_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_INFO;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;

public class CompanyInfoDetailActivity extends BaseMvpActivity<CompanyInfoPresenter> implements CompanyInfoContract.View, SinglePopup.OnPopItemClickListener, CameraCore.CameraResult {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_add_icon)
    TextView tv_add_icon;
    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_company_code)
    EditText et_company_code;
    @BindView(R.id.et_phone_number)
    EditText et_phone_number;
    @BindView(R.id.et_area)
    EditText et_area;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_profession)
    EditText et_profession;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_numA)
    EditText et_numA;
    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private static final String COMPANY_TYPE_CODE = "COMPANYTYPE";
    private List<DicInfo> mSpinnerCompanyTypeList = new ArrayList<>();
    private String mCompanyType = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private static final String STATE_CODE = "STATE";
    private List<DicInfo> mSpinnerStateList = new ArrayList<>();
    private String mState = "";

    private String mCompanyId = "";//企业ID
//    private String mLocalId = "";//本地数据库ID

    private CameraCore cameraCore;
    private SinglePopup singlePopup;
    private String mHeadCode = "";//头像ID

    private GreendaoManager<CompanyInfo, CompanyInfoDao> mCompanyInfoDaoGreendaoManager;
    private CompanyInfoDao mCompanyInfoDao;
    private List<CompanyInfo> mCompanyInfoLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoGreendaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据
    private DicInfoDao mDicInfoDao;
    private GreendaoManager<FileInfoBean, FileInfoBeanDao> mFileInfoBeanDaoGreendaoManager;
    private FileInfoBeanDao mFileInfoBeanDao;
    private List<FileInfoBean> mFileInfoLocalList = new ArrayList<>();//本地数据

    private String mLocalPath = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String companyId/*, String localId*/) {
        Intent intent = new Intent(activity, CompanyInfoDetailActivity.class);
        intent.putExtra("companyId", companyId);
//        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_info_detail);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = getIntent().getStringExtra("companyId");
//        mLocalId = getIntent().getStringExtra("localId");
        tv_save.setOnClickListener(this);
        titlebar_name.setText("企业详情");
        tv_add_icon.setOnClickListener(this);
        if (!TextUtils.isEmpty(mCompanyId)) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        }
        cameraCore = new CameraCore.Builder(this)
                .setNeedCrop(true)
                .setZipInfo(new CameraCore.ZipInfo(true, 200, 200, 100 * 1024))
                .build();
        //得到Dao对象
        mCompanyInfoDao = MyApplication.getInstance().getDaoSession().getCompanyInfoDao();
        //得到Dao对象管理器
        mCompanyInfoDaoGreendaoManager = new GreendaoManager<>(mCompanyInfoDao);
        //得到本地数据
        mCompanyInfoLocalList = mCompanyInfoDaoGreendaoManager.queryAll();

        //得到Dao对象
        mDicInfoDao = MyApplication.getInstance().getDaoSession().getDicInfoDao();
        //得到Dao对象管理器
        mDicInfoDaoGreendaoManager = new GreendaoManager<>(mDicInfoDao);
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoGreendaoManager.queryAll();

        //得到Dao对象
        mFileInfoBeanDao = MyApplication.getInstance().getDaoSession().getFileInfoBeanDao();
        //得到Dao对象管理器
        mFileInfoBeanDaoGreendaoManager = new GreendaoManager<>(mFileInfoBeanDao);
        //得到本地数据
        mFileInfoLocalList = mFileInfoBeanDaoGreendaoManager.queryAll();
    }

    @Override
    public void initData() {
    }

    @Override
    protected CompanyInfoPresenter injectPresenter() {
        return new CompanyInfoPresenter();
    }


    @Override
    protected void requestData() {
        mPresenter.getCompanyType(COMPANY_TYPE_CODE);
        mPresenter.getState(STATE_CODE);
        if (!TextUtils.isEmpty(mCompanyId)) {
            mPresenter.getCompanyInfoDetail(mCompanyId);
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_add_icon:
                if (singlePopup == null) {
                    List<String> list = new ArrayList<>();
                    list.add("拍照");
                    list.add("从相册选择");
                    singlePopup = new SinglePopup(this, list, this);
                    singlePopup.setTitle(View.GONE, "选择来源");
                }
                singlePopup.showAtLocation(tv_add_icon, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.edit))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_save.setVisibility(View.VISIBLE);
                    setEditTextInput(true);
                } else {
                    titlebar_right.setText(R.string.edit);
                    tv_save.setVisibility(View.GONE);
                    setEditTextInput(false);
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_company_name.getText().toString().trim())) {
                    toastMsg("尚未填写企业名称");
                    return;
                }
                if (TextUtils.isEmpty(et_numA.getText().toString().trim())) {
                    toastMsg("尚未填写可开账号数量");
                    return;
                }
                if (TextUtils.isEmpty(spinner_state.getText())) {
                    toastMsg("尚未选择企业状态");
                    return;
                }
                if (TextUtils.isEmpty(mCompanyId)) {
                    if (mState.equals("2")) {
                        toastMsg("新增企业状态不可禁用");
                        return;
                    }
                }
                mPresenter.saveCompanyInfo(mCompanyId, mHeadCode, et_company_name.getText().toString(), et_code.getText().toString().trim(), mCompanyType, et_address.getText().toString().trim(), et_phone_number.getText().toString().trim(), et_email.getText().toString().trim(), et_company_code.getText().toString().trim(), et_area.getText().toString().trim(), et_profession.getText().toString().trim(), et_numA.getText().toString().trim(), mState);
                break;

        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        tv_add_icon.setEnabled(canInput);
        et_company_name.setEnabled(canInput);
        et_company_code.setEnabled(canInput);
        et_phone_number.setEnabled(canInput);
        et_area.setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_email.setEnabled(canInput);
        et_profession.setEnabled(canInput);
        et_code.setEnabled(canInput);
        et_numA.setEnabled(canInput);
        spinner_type.getTextView().setEnabled(canInput);
        spinner_state.getTextView().setEnabled(canInput);
    }

    @Override
    public void getCompanyInfoDetail(CompanyInfo companyInfo, boolean isLocal) {
        if (companyInfo != null) {
            //企业名称
            et_company_name.setText(companyInfo.getName());
            //企业代码
            et_company_code.setText(companyInfo.getMid());
            //联系电话
            et_phone_number.setText(companyInfo.getPhone());
            //地区
            et_area.setText(companyInfo.getArea());
            //地址
            et_address.setText(companyInfo.getAddress());
            //邮箱
            et_email.setText(companyInfo.getEmail());
            //行业
            et_profession.setText(companyInfo.getProfession());
            //编号
            et_code.setText(companyInfo.getCode());
            //可开账号数量
            et_numA.setText(companyInfo.getNumA());
            //企业类型
            spinner_type.setText(companyInfo.getTypeName());
            mCompanyType = companyInfo.getType();
            //状态
            spinner_state.setText(companyInfo.getStateName());
            mState = companyInfo.getState();
            //头像
            mHeadCode = companyInfo.getHead();
            GlideUtil.showImg(iv_icon, mHeadCode);
            addImgToLocal(isLocal);
            for (CompanyInfo localInfo : mCompanyInfoLocalList) {
                if (localInfo.getId().equals(companyInfo.getId())) {
                    companyInfo.setLocalId(localInfo.getLocalId());
                    mCompanyInfoDaoGreendaoManager.correct(companyInfo);
                }
            }
        }

    }

    /**
     * 图片存储本地数据库
     *
     * @param isLocal
     */
    private void addImgToLocal(boolean isLocal) {
        Long mLocalId = 0l;
        for (FileInfoBean info : mFileInfoLocalList) {
            if (mHeadCode.equals(info.getCode())) {
                mLocalId = info.getLocalId();
            }
        }
        FileInfoBean localInfoBean = new FileInfoBean(mLocalId, mCompanyId, mHeadCode, isLocal);
        if (mLocalId == 0l) {
            mFileInfoBeanDaoGreendaoManager.insertOrReplace(localInfoBean);
        } else {
            mFileInfoBeanDaoGreendaoManager.correct(localInfoBean);
        }
    }

    @Override
    public void getCompanyType(List<DicInfo> dicInfoList) {
        mSpinnerCompanyTypeList.clear();
        mSpinnerCompanyTypeList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerCompanyTypeList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(COMPANY_TYPE_CODE);
            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
        }
        spinner_type.setListDatas(this, mSpinnerCompanyTypeList);
        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mCompanyType = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getState(List<DicInfo> dicInfoList) {
        mSpinnerStateList.clear();
        mSpinnerStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(STATE_CODE);
            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
        }
        spinner_state.setListDatas(this, mSpinnerStateList);
        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mState = dicInfo.getCode();
            }
        });
    }

    @Override
    public void uploadImages(FileInfoBean fileInfoBean, boolean isLocal) {
        if (isLocal) {
            mHeadCode = mLocalPath;
        } else {
            mHeadCode = fileInfoBean.getCode();
        }
        GlideUtil.showImg(iv_icon, mHeadCode);
        addImgToLocal(isLocal);
    }


    @Override
    public void saveCompanyInfo(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mCompanyId)) {
            toastString = "企业信息创建成功";
        } else {
            toastString = "企业信息修改成功";
        }
        Long mLocalId = 0l;
        for (CompanyInfo companyInfo : mCompanyInfoLocalList) {
            if (mCompanyId.equals(companyInfo.getId())) {
                mLocalId = companyInfo.getLocalId();
            }
        }
        CompanyInfo companyInfo = new CompanyInfo(mLocalId, StringUtil.getText(et_area), StringUtil.getText(et_profession), StringUtil.getText(et_code), StringUtil.getText(et_address), StringUtil.getText(et_numA), StringUtil.getText(et_company_code), mCompanyType, spinner_type.getText(), mHeadCode, TimeUtils.getCurrentTime(new Date()), StringUtil.getText(et_phone_number), StringUtil.getText(et_company_name), mCompanyId, mState, spinner_state.getText(), StringUtil.getText(et_email), false, false, isLocal);
        if (mLocalId == 0l) {
            mCompanyInfoDaoGreendaoManager.insertOrReplace(companyInfo);
        } else {
            mCompanyInfoDaoGreendaoManager.correct(companyInfo);
        }
        EventBus.getDefault().post(new AddOrSaveCompanyEvent(toastString));
        finish();
    }

    @Override
    public void itemClick(int position, String s) {
        switch (position) {
            case 0:
                cameraCore.openCamera();
                break;
            case 1:
                cameraCore.openAlbum();
                break;
        }
    }

    @Override
    public void success(String path) {
        if (!TextUtils.isEmpty(path)) {
            mLocalPath = path;
            mPresenter.uploadImages(mLocalPath);
        }
    }

    @Override
    public void fail(int code, String message) {
        toastMsg(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraCore.onResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraCore.onPermission(requestCode, permissions, grantResults);
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_COMPANY_TYPE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoGreendaoManager.queryAll()) {
                    if (dicInfo.getType().equals(COMPANY_TYPE_CODE)) {
                        typeList.add(dicInfo);
                    }
                }
                getCompanyType(typeList);
                break;
            case REQUEST_COMPANY_STATE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoGreendaoManager.queryAll()) {
                    if (dicInfo.getType().equals(STATE_CODE)) {
                        stateList.add(dicInfo);
                    }
                }
                getState(stateList);
                break;
            case REQUEST_UPLOAD_IMAGE:
                Long mLocalId = 0l;
                for (FileInfoBean info : mFileInfoLocalList) {
                    if (mHeadCode.equals(info.getCode())) {
                        mLocalId = info.getLocalId();
                    }
                }
                FileInfoBean localInfoBean = new FileInfoBean(mLocalId, mCompanyId, mHeadCode, true);
                uploadImages(localInfoBean, true);
                break;
            case REQUEST_COMPANY_INFO_DETAIL:
                CompanyInfo companyInfo = null;
                for (CompanyInfo info : mCompanyInfoLocalList) {
                    if (info.getId().equals(mCompanyId)) {
                        companyInfo = info;
                    }
                }
                getCompanyInfoDetail(companyInfo, true);
                break;
            case REQUEST_SAVE_COMPANY_INFO:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mCompanyId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyInfo(uploadInfoBean, true);
                break;
        }
    }
}
