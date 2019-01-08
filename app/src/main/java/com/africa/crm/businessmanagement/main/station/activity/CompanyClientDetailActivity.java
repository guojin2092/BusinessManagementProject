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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyClientEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyClientInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.CompanyClientDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyClientDetailPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.INDUSTRY_CODE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_USERS;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_CLIENT_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_INDUSTRY_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_CLIENT;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/15 0015 10:34
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyClientDetailActivity extends BaseMvpActivity<CompanyClientDetailPresenter> implements CompanyClientDetailContract.View, SinglePopup.OnPopItemClickListener, CameraCore.CameraResult {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_add_icon)
    TextView tv_add_icon;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_from_company_name)
    EditText et_from_company_name;
    @BindView(R.id.et_company_staff_num)
    EditText et_company_staff_num;
    @BindView(R.id.et_income)
    EditText et_income;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_remark)
    EditText et_remark;

    @BindView(R.id.spinner_industry)
    MySpinner spinner_industry;
    private List<DicInfo> mSpinnerIndustryList = new ArrayList<>();
    private String mIndustryType = "";

    @BindView(R.id.ll_from_user)
    LinearLayout ll_from_user;
    @BindView(R.id.spinner_from_user)
    MySpinner spinner_from_user;
    private List<DicInfo> mSpinnerCompanyUserList = new ArrayList<>();
    private String mFromUserId = "";

    private String mClientId = "";//联系人Id
    private Long mLocalId = 0l;//本地数据库ID
    private String mRoleCode = "";//角色code
    private String mCompanyId = "";

    private CameraCore cameraCore;
    private SinglePopup singlePopup;
    private String mHeadCode = "";//头像ID
    private String mLocalPath = "";

    private GreendaoManager<CompanyClientInfo, CompanyClientInfoDao> mClientInfoDaoManager;
    private List<CompanyClientInfo> mCompanyClientLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanyClientDetailActivity.class);
        intent.putExtra("clientId", id);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_client_detail);
    }

    @Override
    protected CompanyClientDetailPresenter injectPresenter() {
        return new CompanyClientDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mClientId = getIntent().getStringExtra("clientId");
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        titlebar_name.setText("客户详情");
        tv_add_icon.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        if (TextUtils.isEmpty(mClientId) && mLocalId == 0l) {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mClientId) || mLocalId != 0l) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        }
        cameraCore = new CameraCore.Builder(this)
                .setNeedCrop(true)
                .setZipInfo(new CameraCore.ZipInfo(true, 200, 200, 100 * 1024))
                .build();
        //得到Dao对象管理器
        mClientInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyClientInfoDao());
        //得到本地数据
        mCompanyClientLocalList = mClientInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
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
                if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                    toastMsg("尚未填写姓名");
                    return;
                }
                String userId = "";
                if (mRoleCode.equals("companySales")) {
                    userId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
                } else {
                    userId = mFromUserId;
                }
                mPresenter.saveCompanyClient(mClientId, mCompanyId, userId, mHeadCode, et_name.getText().toString().trim(), mIndustryType, et_address.getText().toString().trim(), et_company_staff_num.getText().toString().trim(), et_phone.getText().toString().trim(), et_income.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void requestData() {
        if ("companyRoot".equals(mRoleCode)) {
            ll_from_user.setVisibility(View.VISIBLE);
            mPresenter.getAllCompanyUsers(mCompanyId);
        } else {
            ll_from_user.setVisibility(View.GONE);
        }
        mPresenter.getIndustry(INDUSTRY_CODE);
        if (!TextUtils.isEmpty(mClientId) || mLocalId != 0l) {
            mPresenter.getCompanyClientDetail(mClientId);
        }

    }

    @Override
    public void initData() {

    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        tv_add_icon.setEnabled(canInput);
        et_name.setEnabled(canInput);
        spinner_from_user.getTextView().setEnabled(canInput);
        et_from_company_name.setEnabled(canInput);
        et_company_staff_num.setEnabled(canInput);
        spinner_industry.getTextView().setEnabled(canInput);
        et_income.setEnabled(canInput);
        et_phone.setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void getAllCompanyUsers(List<DicInfo2> dicInfo2List) {
        mSpinnerCompanyUserList.clear();
        if (!ListUtils.isEmpty(dicInfo2List)) {
            for (DicInfo2 dicInfo2 : dicInfo2List) {
                DicInfo dicInfo = new DicInfo(dicInfo2.getId(), QUERY_ALL_USERS, dicInfo2.getName(), dicInfo2.getCode());
                mSpinnerCompanyUserList.add(dicInfo);
            }
            List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerCompanyUserList, mDicInfoLocalList);
            for (DicInfo dicInfo : addList) {
                dicInfo.setType(QUERY_ALL_USERS);
                mDicInfoDaoManager.insertOrReplace(dicInfo);
            }
            spinner_from_user.setListDatas(getBVActivity(), mSpinnerCompanyUserList);
            spinner_from_user.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mFromUserId = dicInfo.getCode();
                }
            });
        }
    }

    @Override
    public void getIndustry(List<DicInfo> dicInfoList) {
        mSpinnerIndustryList.clear();
        mSpinnerIndustryList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerIndustryList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(INDUSTRY_CODE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_industry.setListDatas(this, mSpinnerIndustryList);
        spinner_industry.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mIndustryType = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyClientDetail(CompanyClientInfo companyClientInfo) {
        if (companyClientInfo != null) {
            et_name.setText(companyClientInfo.getName());
            spinner_from_user.setText(companyClientInfo.getUserNickName());
            mFromUserId = companyClientInfo.getUserId();
            et_from_company_name.setText(companyClientInfo.getCompanyName());
            et_company_staff_num.setText(companyClientInfo.getWorkerNum());
            mIndustryType = companyClientInfo.getIndustry();
            spinner_industry.setText(companyClientInfo.getIndustryName());
            mIndustryType = companyClientInfo.getIndustry();
            et_income.setText(companyClientInfo.getYearIncome());
            et_phone.setText(companyClientInfo.getTel());
            et_address.setText(companyClientInfo.getAddress());
            et_remark.setText(companyClientInfo.getRemark());
            mHeadCode = companyClientInfo.getHead();
            GlideUtil.showImg(iv_icon, mHeadCode);
            mCompanyId = companyClientInfo.getCompanyId();
            for (CompanyClientInfo localInfo : mCompanyClientLocalList) {
                if (localInfo.getId().equals(companyClientInfo.getId())) {
                    companyClientInfo.setLocalId(localInfo.getLocalId());
                    mClientInfoDaoManager.correct(companyClientInfo);
                }
            }
        }
    }

    @Override
    public void saveCompanyClient(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mClientId) && mLocalId == 0l) {
            toastString = "企业客户创建成功";
        } else {
            toastString = "企业客户修改成功";
        }
        if (isLocal) {
            CompanyClientInfo companyClientInfo = null;
            if (mLocalId == 0l) {
                companyClientInfo = new CompanyClientInfo(mClientId, TimeUtils.getCurrentTime(new Date()), spinner_industry.getText(), StringUtil.getText(et_remark), StringUtil.getText(et_company_staff_num), StringUtil.getText(et_phone), StringUtil.getText(et_from_company_name), StringUtil.getText(et_address), StringUtil.getText(et_income), mFromUserId, spinner_from_user.getText(), StringUtil.getText(et_name), mCompanyId, mHeadCode, mIndustryType, false, isLocal);
                mClientInfoDaoManager.insertOrReplace(companyClientInfo);
            } else {
                for (CompanyClientInfo info : mCompanyClientLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyClientInfo = new CompanyClientInfo(info.getLocalId(), mClientId, TimeUtils.getCurrentTime(new Date()), spinner_industry.getText(), StringUtil.getText(et_remark), StringUtil.getText(et_company_staff_num), StringUtil.getText(et_phone), StringUtil.getText(et_from_company_name), StringUtil.getText(et_address), StringUtil.getText(et_income), mFromUserId, spinner_from_user.getText(), StringUtil.getText(et_name), mCompanyId, mHeadCode, mIndustryType, false, isLocal);
                        mClientInfoDaoManager.correct(companyClientInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyClientEvent(toastString));
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
    public void uploadImages(FileInfoBean fileInfoBean) {
        if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
            mHeadCode = fileInfoBean.getCode();
            GlideUtil.showImg(iv_icon, mHeadCode);
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
            case REQUEST_ALL_USERS_LIST:
                List<DicInfo2> allUserList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_USERS)) {
                        allUserList.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllCompanyUsers(allUserList);
                break;
            case REQUEST_INDUSTRY_TYPE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(INDUSTRY_CODE)) {
                        typeList.add(dicInfo);
                    }
                }
                getIndustry(typeList);
                break;
            case REQUEST_UPLOAD_IMAGE:
                FileInfoBean localInfoBean = new FileInfoBean(mLocalPath);
                uploadImages(localInfoBean);
                break;
            case REQUEST_COMPANY_CLIENT_DETAIL:
                CompanyClientInfo companyClientInfo = null;
                for (CompanyClientInfo info : mCompanyClientLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyClientInfo = info;
                    }
                }
                getCompanyClientDetail(companyClientInfo);
                break;
            case REQUEST_SAVE_COMPANY_CLIENT:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mClientId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyClient(uploadInfoBean, true);
                break;
        }
    }
}
