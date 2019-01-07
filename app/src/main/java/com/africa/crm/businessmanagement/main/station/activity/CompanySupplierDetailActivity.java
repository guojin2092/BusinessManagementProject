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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanySupplierEvent;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanySupplierInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.CompanySupplierDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanySupplierDetailPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.SUPPLIER_TYPE_CODE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_SUPPLIER_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_SUPPLIER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SUPPLIER_TYPE;
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
public class CompanySupplierDetailActivity extends BaseMvpActivity<CompanySupplierDetailPresenter> implements CompanySupplierDetailContract.View, SinglePopup.OnPopItemClickListener, CameraCore.CameraResult {
    public final static String SUPPLIER_ID = "supplier_id";
    private String mSupplierId = "";
    @BindView(R.id.et_supplier_name)
    EditText et_supplier_name;
    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_area)
    EditText et_area;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_zip_code)
    EditText et_zip_code;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private List<DicInfo> mSpinnerCompanyTypeList = new ArrayList<>();
    private String mCompanyType = "";
    private String mCompanyId = "";
    private Long mLocalId = 0l;//本地数据库ID

    private CameraCore cameraCore;
    private SinglePopup singlePopup;
    private String mHeadCode = "";//头像ID
    private String mLocalPath = "";

    private GreendaoManager<CompanySupplierInfo, CompanySupplierInfoDao> mCompanySupplierInfoDaoManager;
    private List<CompanySupplierInfo> mCompanySupplierLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoGreendaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String supplierId, Long localId) {
        Intent intent = new Intent(activity, CompanySupplierDetailActivity.class);
        intent.putExtra(SUPPLIER_ID, supplierId);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_supplier_detail);
    }

    @Override
    protected CompanySupplierDetailPresenter injectPresenter() {
        return new CompanySupplierDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mSupplierId = getIntent().getStringExtra(SUPPLIER_ID);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mLocalId = getIntent().getLongExtra("localId", 0l);
        tv_save.setOnClickListener(this);
        titlebar_right.setText(R.string.edit);
        titlebar_name.setText("供应商详情");
        iv_icon.setOnClickListener(this);
        if (TextUtils.isEmpty(mSupplierId) && mLocalId == 0l) {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mSupplierId) || mLocalId != 0l) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        }
        cameraCore = new CameraCore.Builder(this)
                .setNeedCrop(true)
                .setZipInfo(new CameraCore.ZipInfo(true, 200, 200, 100 * 1024))
                .build();
        //得到Dao对象管理器
        mCompanySupplierInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanySupplierInfoDao());
        //得到本地数据
        mCompanySupplierLocalList = mCompanySupplierInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoGreendaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoGreendaoManager.queryAll();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void requestData() {
        mPresenter.getSupplierType(SUPPLIER_TYPE_CODE);
        if (!TextUtils.isEmpty(mSupplierId) || mLocalId != 0l) {
            mPresenter.getCompanySupplierDetail(mSupplierId);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_icon:
                if (singlePopup == null) {
                    List<String> list = new ArrayList<>();
                    list.add("拍照");
                    list.add("从相册选择");
                    singlePopup = new SinglePopup(this, list, this);
                    singlePopup.setTitle(View.GONE, "选择来源");
                }
                singlePopup.showAtLocation(iv_icon, Gravity.BOTTOM, 0, 0);
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
                if (TextUtils.isEmpty(et_supplier_name.getText().toString().trim())) {
                    toastMsg("尚未填写供应商名称");
                    return;
                }
                mPresenter.saveCompanySupplier(mSupplierId, mCompanyId, mHeadCode, et_supplier_name.getText().toString().trim(), mCompanyType, et_address.getText().toString().trim(), et_phone.getText().toString().trim(), et_email.getText().toString().trim(), et_zip_code.getText().toString().trim(), et_area.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_supplier_name.setEnabled(canInput);
        et_company_name.setEnabled(canInput);
        spinner_type.getTextView().setEnabled(canInput);
        et_email.setEnabled(canInput);
        et_phone.setEnabled(canInput);
        et_area.setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_zip_code.setEnabled(canInput);
        et_remark.setEnabled(canInput);
        iv_icon.setEnabled(canInput);
    }

    @Override
    public void getSupplierType(List<DicInfo> dicInfoList) {
        mSpinnerCompanyTypeList.clear();
        mSpinnerCompanyTypeList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerCompanyTypeList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(SUPPLIER_TYPE_CODE);
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
    public void getCompanySupplierDetail(CompanySupplierInfo companySupplierInfo) {
        if (companySupplierInfo != null) {
            et_supplier_name.setText(companySupplierInfo.getName());
            et_company_name.setText(companySupplierInfo.getCompanyName());
            spinner_type.setText(companySupplierInfo.getTypeName());
            mCompanyType = companySupplierInfo.getType();
            et_email.setText(companySupplierInfo.getEmail());
            et_phone.setText(companySupplierInfo.getPhone());
            et_area.setText(companySupplierInfo.getArea());
            et_address.setText(companySupplierInfo.getAddress());
            et_zip_code.setText(companySupplierInfo.getZipCode());
            et_remark.setText(companySupplierInfo.getRemark());
            //头像
            mHeadCode = companySupplierInfo.getHead();
            GlideUtil.showImg(iv_icon, mHeadCode);
            for (CompanySupplierInfo localInfo : mCompanySupplierLocalList) {
                if (localInfo.getId().equals(companySupplierInfo.getId())) {
                    companySupplierInfo.setLocalId(localInfo.getLocalId());
                    mCompanySupplierInfoDaoManager.correct(companySupplierInfo);
                }
            }
        }

    }

    @Override
    public void saveCompanySupplier(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mSupplierId) && mLocalId == 0l) {
            toastString = "企业供应商创建成功";
        } else {
            toastString = "企业供应商修改成功";
        }
        if (isLocal) {
            CompanySupplierInfo companyInfo = null;
            if (mLocalId == 0l) {
                companyInfo = new CompanySupplierInfo(mSupplierId, StringUtil.getText(et_area), StringUtil.getText(et_zip_code), StringUtil.getText(et_address), StringUtil.getText(et_company_name), spinner_type.getText(), StringUtil.getText(et_remark), mCompanyType, mHeadCode, mCompanyId, TimeUtils.getCurrentTime(new Date()), StringUtil.getText(et_phone), StringUtil.getText(et_supplier_name), StringUtil.getText(et_email), false, true);
                mCompanySupplierInfoDaoManager.insertOrReplace(companyInfo);
            } else {
                for (CompanySupplierInfo info : mCompanySupplierLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyInfo = new CompanySupplierInfo(info.getLocalId(), mSupplierId, StringUtil.getText(et_area), StringUtil.getText(et_zip_code), StringUtil.getText(et_address), StringUtil.getText(et_company_name), spinner_type.getText(), StringUtil.getText(et_remark), mCompanyType, mHeadCode, mCompanyId, TimeUtils.getCurrentTime(new Date()), StringUtil.getText(et_phone), StringUtil.getText(et_supplier_name), StringUtil.getText(et_email), false, true);
                        mCompanySupplierInfoDaoManager.correct(companyInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanySupplierEvent(toastString));
        finish();
    }

    @Override
    public void uploadImages(FileInfoBean fileInfoBean) {
        mHeadCode = fileInfoBean.getCode();
        GlideUtil.showImg(iv_icon, mHeadCode);
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
        mLocalPath = path;
        mPresenter.uploadImages(mLocalPath);
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
            case REQUEST_SUPPLIER_TYPE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoGreendaoManager.queryAll()) {
                    if (dicInfo.getType().equals(SUPPLIER_TYPE_CODE)) {
                        typeList.add(dicInfo);
                    }
                }
                getSupplierType(typeList);
                break;
            case REQUEST_UPLOAD_IMAGE:
                FileInfoBean localInfoBean = new FileInfoBean(mLocalPath);
                uploadImages(localInfoBean);
                break;
            case REQUEST_COMPANY_SUPPLIER_DETAIL:
                CompanySupplierInfo companyInfo = null;
                for (CompanySupplierInfo info : mCompanySupplierLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyInfo = info;
                    }
                }
                getCompanySupplierDetail(companyInfo);
                break;
            case REQUEST_SAVE_COMPANY_SUPPLIER:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mCompanyId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanySupplier(uploadInfoBean, true);
                break;
        }
    }
}
