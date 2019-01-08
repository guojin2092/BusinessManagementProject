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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyContactEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyContactInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.CompanyContactDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyContactDetailPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.FROM_TYPE_CODE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_USERS;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_CONTACT_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_CONTACT_FROM_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_CONTACT;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyContactDetailActivity extends BaseMvpActivity<CompanyContactDetailPresenter> implements CompanyContactDetailContract.View, SinglePopup.OnPopItemClickListener, CameraCore.CameraResult {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_add_icon)
    TextView tv_add_icon;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_job)
    EditText et_job;
    @BindView(R.id.et_from_company_name)
    EditText et_from_company_name;
    @BindView(R.id.et_telephone)
    EditText et_telephone;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_zip_address)
    EditText et_zip_address;
    @BindView(R.id.et_remark)
    EditText et_remark;

    @BindView(R.id.spinner_from_type)
    MySpinner spinner_from_type;
    private List<DicInfo> mSpinnerFromTypeList = new ArrayList<>();
    private String mFromType = "";

    @BindView(R.id.ll_from_user)
    LinearLayout ll_from_user;
    @BindView(R.id.spinner_from_user)
    MySpinner spinner_from_user;
    private List<DicInfo> mSpinnerCompanyUserList = new ArrayList<>();
    private String mFromUserId = "";
    private String mUserId = "";

    private String mRoleCode = "";//角色code
    private String mContactId = "";//联系人ID
    private String mCompanyId = "";
    private Long mLocalId = 0l;//本地数据库ID

    private CameraCore cameraCore;
    private SinglePopup singlePopup;
    private String mHeadCode = "";//头像ID
    private String mLocalPath = "";

    private GreendaoManager<CompanyContactInfo, CompanyContactInfoDao> mContactInfoDaoManager;
    private List<CompanyContactInfo> mCompanyContactLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanyContactDetailActivity.class);
        intent.putExtra("contactId", id);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contact_detail);
    }

    @Override
    protected CompanyContactDetailPresenter injectPresenter() {
        return new CompanyContactDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mContactId = getIntent().getStringExtra("contactId");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        mLocalId = getIntent().getLongExtra("localId", 0l);
        titlebar_name.setText("联系人详情");
        tv_add_icon.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        if (TextUtils.isEmpty(mContactId) && mLocalId == 0l) {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mContactId) || mLocalId != 0l) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        }
        cameraCore = new CameraCore.Builder(this)
                .setNeedCrop(true)
                .setZipInfo(new CameraCore.ZipInfo(true, 200, 200, 100 * 1024))
                .build();
        //得到Dao对象管理器
        mContactInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyContactInfoDao());
        //得到本地数据
        mCompanyContactLocalList = mContactInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
    }


    @Override
    public void initData() {
    }

    @Override
    protected void requestData() {
        if ("companyRoot".equals(mRoleCode)) {
            ll_from_user.setVisibility(View.VISIBLE);
            mPresenter.getAllCompanyUsers(mCompanyId);
        } else {
            ll_from_user.setVisibility(View.GONE);
        }
        mPresenter.getFromType(FROM_TYPE_CODE);
        if (!TextUtils.isEmpty(mContactId) || mLocalId != 0l) {
            mPresenter.getContactDetail(mContactId);
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
                if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                    toastMsg("尚未填写姓名");
                    return;
                }
                if (mRoleCode.equals("companySales")) {
                    mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
                } else {
                    mUserId = mFromUserId;
                }
                mPresenter.saveCompanyContact(mContactId, mCompanyId, mUserId, mHeadCode, et_name.getText().toString().trim(), mFromType, et_address.getText().toString().trim(), et_zip_address.getText().toString().trim(), et_telephone.getText().toString().trim(), et_phone.getText().toString().trim(), et_email.getText().toString().trim(), et_job.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
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
    public void getFromType(List<DicInfo> dicInfoList) {
        mSpinnerFromTypeList.clear();
        mSpinnerFromTypeList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerFromTypeList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(FROM_TYPE_CODE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_from_type.setListDatas(this, mSpinnerFromTypeList);

        spinner_from_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mFromType = dicInfo.getCode();
            }
        });
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        tv_add_icon.setEnabled(canInput);
        et_name.setEnabled(canInput);
        et_job.setEnabled(canInput);
        et_from_company_name.setEnabled(canInput);
        spinner_from_user.getTextView().setEnabled(canInput);
        spinner_from_type.getTextView().setEnabled(canInput);
        et_telephone.setEnabled(canInput);
        et_phone.setEnabled(canInput);
        et_email.setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_zip_address.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void getContactDetail(CompanyContactInfo companyContactInfo) {
        if (companyContactInfo != null) {
            et_name.setText(companyContactInfo.getName());
            et_job.setText(companyContactInfo.getJob());
            et_from_company_name.setText(companyContactInfo.getCompanyName());
            spinner_from_user.setText(companyContactInfo.getUserNickName());
            mFromUserId = companyContactInfo.getUserId();
            spinner_from_type.setText(companyContactInfo.getFromTypeName());
            mFromType = companyContactInfo.getFromType();
            et_telephone.setText(companyContactInfo.getPhone());
            et_phone.setText(companyContactInfo.getTel());
            et_email.setText(companyContactInfo.getEmail());
            et_address.setText(companyContactInfo.getAddress());
            et_zip_address.setText(companyContactInfo.getMailAddress());
            et_remark.setText(companyContactInfo.getRemark());
            mHeadCode = companyContactInfo.getHead();
            GlideUtil.showImg(iv_icon, mHeadCode);
            mCompanyId = companyContactInfo.getCompanyId();
            for (CompanyContactInfo localInfo : mCompanyContactLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyContactInfo.getId())) {
                    if (localInfo.getId().equals(companyContactInfo.getId())) {
                        companyContactInfo.setLocalId(localInfo.getLocalId());
                        mContactInfoDaoManager.correct(companyContactInfo);
                    }
                }
            }
        }
    }

    @Override
    public void saveCompanyContact(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mContactId) && mLocalId == 0l) {
            toastString = "企业联系人创建成功";
        } else {
            toastString = "企业联系人修改成功";
        }
        if (isLocal) {
            CompanyContactInfo companyContactInfo = null;
            if (mLocalId == 0l) {
                companyContactInfo = new CompanyContactInfo(mContactId, TimeUtils.getCurrentTime(new Date()), StringUtil.getText(et_address), StringUtil.getText(et_from_company_name), StringUtil.getText(et_zip_address), StringUtil.getText(et_remark), mUserId, spinner_from_type.getText(), mHeadCode, mCompanyId, mFromType, StringUtil.getText(et_telephone), StringUtil.getText(et_name), StringUtil.getText(et_phone), spinner_from_user.getText(), StringUtil.getText(et_job), StringUtil.getText(et_email), false, isLocal);
                mContactInfoDaoManager.insertOrReplace(companyContactInfo);
            } else {
                for (CompanyContactInfo info : mCompanyContactLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyContactInfo = new CompanyContactInfo(info.getLocalId(), mContactId, TimeUtils.getCurrentTime(new Date()), StringUtil.getText(et_address), StringUtil.getText(et_from_company_name), StringUtil.getText(et_zip_address), StringUtil.getText(et_remark), mUserId, spinner_from_type.getText(), mHeadCode, mCompanyId, mFromType, StringUtil.getText(et_telephone), StringUtil.getText(et_name), StringUtil.getText(et_phone), spinner_from_user.getText(), StringUtil.getText(et_job), StringUtil.getText(et_email), false, isLocal);
                        mContactInfoDaoManager.correct(companyContactInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyContactEvent(toastString));
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
        mHeadCode = fileInfoBean.getCode();
        GlideUtil.showImg(iv_icon, mHeadCode);
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
            case REQUEST_CONTACT_FROM_TYPE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(FROM_TYPE_CODE)) {
                        typeList.add(dicInfo);
                    }
                }
                getFromType(typeList);
                break;
            case REQUEST_UPLOAD_IMAGE:
                FileInfoBean localInfoBean = new FileInfoBean(mLocalPath);
                uploadImages(localInfoBean);
                break;
            case REQUEST_COMPANY_CONTACT_DETAIL:
                CompanyContactInfo companyContactInfo = null;
                for (CompanyContactInfo info : mCompanyContactLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyContactInfo = info;
                    }
                }
                getContactDetail(companyContactInfo);
                break;
            case REQUEST_SAVE_COMPANY_CONTACT:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mContactId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyContact(uploadInfoBean, true);
                break;
        }
    }
}
