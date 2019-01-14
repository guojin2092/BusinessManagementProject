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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveUserEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyUserInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyUserInfoBeanDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.UserDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.UserDetailPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_COMPANY;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_ROLES;
import static com.africa.crm.businessmanagement.network.api.DicUtil.STATE_CODE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.USER_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_GET_ALL_COMPANY;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_GET_USER_INFO;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_QUERY_ALL_ROLES;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_USER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_USER_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyUserDetailActivity extends BaseMvpActivity<UserDetailPresenter> implements UserDetailContract.View, SinglePopup.OnPopItemClickListener, CameraCore.CameraResult {
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_add_icon)
    TextView tv_add_icon;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.ll_password)
    LinearLayout ll_password;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.spinner_company)
    MySpinner spinner_company;
    @BindView(R.id.spinner_role)
    MySpinner spinner_role;

    private List<DicInfo> mSpinnerCompanyList = new ArrayList<>();
    private String mCompanyId = "";
    private String mCompanyName = "";

    private List<DicInfo> mSpinnerRoleList = new ArrayList<>();
    private String mRoleId = "";
    private String mRoleName = "";

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private List<DicInfo> mSpinnerUserList = new ArrayList<>();
    private String mUserType = "";
    private String mTypeName = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private List<DicInfo> mSpinnerStateList = new ArrayList<>();
    private String mStateType = "";
    private String mStateName = "";

    private CameraCore cameraCore;
    private SinglePopup singlePopup;
    private String mHeadCode = "";//头像ID
    private String mLocalPath = "";

    private String mUserId = "";
    private Long mLocalId = 0l;//本地数据库ID


    private GreendaoManager<CompanyUserInfoBean, CompanyUserInfoBeanDao> mUserInfoBeanDaoManager;
    private List<CompanyUserInfoBean> mUserInfoLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String userId, Long localId) {
        Intent intent = new Intent(activity, CompanyUserDetailActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_detail);
    }

    @Override
    public void initView() {
        super.initView();
        mUserId = getIntent().getStringExtra("userId");
        mLocalId = getIntent().getLongExtra("localId", 0l);
        titlebar_name.setText("用户详情");
        tv_add_icon.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        spinner_type.getTextView().setEnabled(false);
        if (TextUtils.isEmpty(mUserId) && mLocalId == 0l) {
            ll_password.setVisibility(View.VISIBLE);
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mUserId) || mLocalId != 0l) {
            ll_password.setVisibility(View.GONE);
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        }
        cameraCore = new CameraCore.Builder(this)
                .setNeedCrop(true)
                .setZipInfo(new CameraCore.ZipInfo(true, 200, 200, 100 * 1024))
                .build();
        //得到Dao对象管理器
        mUserInfoBeanDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyUserInfoBeanDao());
        //得到本地数据
        mUserInfoLocalList = mUserInfoBeanDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
    }

    @Override
    public void initData() {

    }

    @Override
    protected UserDetailPresenter injectPresenter() {
        return new UserDetailPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getUserType(USER_TYPE);
        mPresenter.getStateType(STATE_CODE);
        mPresenter.getAllCompany("");
        mPresenter.getAllRoles();
        if (!TextUtils.isEmpty(mUserId) || mLocalId != 0l)
            mPresenter.getUserInfo(mUserId);
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
                if (!TextUtils.isEmpty(mUserId)) {
                    if (titlebar_right.getText().toString().equals(getString(R.string.edit))) {
                        titlebar_right.setText(R.string.cancel);
                        tv_save.setVisibility(View.VISIBLE);
                        setEditTextInput(true);
                    } else {
                        titlebar_right.setText(R.string.edit);
                        tv_save.setVisibility(View.GONE);
                        setEditTextInput(false);
                    }
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_account.getText().toString().trim())) {
                    toastMsg("尚未填写账号");
                    return;
                }
                if (TextUtils.isEmpty(spinner_type.getText())) {
                    toastMsg("尚未选择账号类型");
                    return;
                }
                if (TextUtils.isEmpty(spinner_role.getText())) {
                    toastMsg("尚未选择账号角色");
                    return;
                }
                if (TextUtils.isEmpty(mUserId)) {
                    if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
                        toastMsg("尚未填写密码");
                        return;
                    }
                    if (et_password.getText().toString().trim().length() < 6) {
                        toastMsg("密码不得小于6位");
                        return;
                    }
                }
                if (mRoleId.equals("13") || mRoleId.equals("14")) {
                    if (TextUtils.isEmpty(mCompanyId)) {
                        toastMsg("尚未选择所属企业");
                        return;
                    }
                }
                mPresenter.saveOrcreateUser(mUserId, et_account.getText().toString().trim(), mUserType, mRoleId, et_password.getText().toString().trim(), et_nickname.getText().toString().trim(), et_phone.getText().toString().trim(), et_address.getText().toString().trim(), et_email.getText().toString().trim(), mStateType, mCompanyId, mHeadCode);
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
        et_account.setEnabled(canInput);
        et_nickname.setEnabled(canInput);
        et_password.setEnabled(canInput);
        et_phone.setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_email.setEnabled(canInput);
        spinner_state.getTextView().setEnabled(canInput);
        spinner_role.getTextView().setEnabled(canInput);
        spinner_company.getTextView().setEnabled(canInput);
    }

    @Override
    public void getUserType(List<DicInfo> dicInfoList) {
        mSpinnerUserList.clear();
        mSpinnerUserList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerUserList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(USER_TYPE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_type.setListDatas(this, mSpinnerUserList);
        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mUserType = dicInfo.getCode();
                mTypeName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getStateType(List<DicInfo> dicInfoList) {
        mSpinnerStateList.clear();
        mSpinnerStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(STATE_CODE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_type.setListDatas(this, mSpinnerUserList);
        spinner_state.setListDatas(this, mSpinnerStateList);

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateType = dicInfo.getCode();
                mStateName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getUserInfo(CompanyUserInfoBean userInfo) {
        if (userInfo != null) {
            mUserType = userInfo.getType();
            mTypeName = userInfo.getTypeName();
            mStateType = userInfo.getState();
            mStateName = userInfo.getStateName();
            mRoleId = userInfo.getRoleId();
            mRoleName = userInfo.getRoleName();
            mCompanyId = userInfo.getCompanyId();
            mCompanyName = userInfo.getCompanyName();
            et_account.setText(userInfo.getUserName());
            et_nickname.setText(userInfo.getName());
            et_phone.setText(userInfo.getPhone());
            et_address.setText(userInfo.getAddress());
            et_email.setText(userInfo.getEmail());
            spinner_type.setText(userInfo.getTypeName());
            spinner_company.setText(userInfo.getCompanyName());
            spinner_state.setText(userInfo.getStateName());
            spinner_role.setText(userInfo.getRoleName());
            mHeadCode = userInfo.getHead();
            GlideUtil.showImg(iv_icon, mHeadCode);
            for (CompanyUserInfoBean localInfo : mUserInfoLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(userInfo.getId())) {
                    if (localInfo.getId().equals(userInfo.getId())) {
                        userInfo.setLocalId(localInfo.getLocalId());
                        mUserInfoBeanDaoManager.correct(userInfo);
                    }
                }
            }
        }
    }

    @Override
    public void getAllCompany(List<DicInfo2> dicInfoList) {
        mSpinnerCompanyList.clear();
        if (!ListUtils.isEmpty(dicInfoList)) {
            for (DicInfo2 dicInfo2 : dicInfoList) {
                DicInfo dicInfo = new DicInfo(dicInfo2.getId(), QUERY_ALL_COMPANY, dicInfo2.getName(), dicInfo2.getCode());
                mSpinnerCompanyList.add(dicInfo);
            }
            List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerCompanyList, mDicInfoLocalList);
            for (DicInfo dicInfo : addList) {
                dicInfo.setType(QUERY_ALL_COMPANY);
                mDicInfoDaoManager.insertOrReplace(dicInfo);
            }
            spinner_company.setListDatas(getBVActivity(), mSpinnerCompanyList);
            spinner_company.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mCompanyId = dicInfo.getCode();
                    mCompanyName = dicInfo.getText();
                }
            });
        }
    }

    @Override
    public void getAllRoles(List<RoleInfoBean> roleInfoBeanList) {
        mSpinnerRoleList.clear();
        if (!ListUtils.isEmpty(roleInfoBeanList)) {
            if (roleInfoBeanList.size() == 3) {
                mSpinnerRoleList.add(new DicInfo(roleInfoBeanList.get(0).getId(), QUERY_ALL_ROLES, roleInfoBeanList.get(0).getRoleName(), roleInfoBeanList.get(0).getId()));
                mSpinnerRoleList.add(new DicInfo(roleInfoBeanList.get(1).getId(), QUERY_ALL_ROLES, roleInfoBeanList.get(1).getRoleName(), roleInfoBeanList.get(1).getId()));
            } else {
                if (!ListUtils.isEmpty(roleInfoBeanList)) {
                    for (RoleInfoBean roleInfoBean : roleInfoBeanList) {
                        DicInfo dicInfo = new DicInfo(roleInfoBean.getId(), QUERY_ALL_ROLES, roleInfoBean.getRoleName(), roleInfoBean.getId());
                        mSpinnerRoleList.add(dicInfo);
                    }
                }
            }
            List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerRoleList, mDicInfoLocalList);
            for (DicInfo dicInfo : addList) {
                mDicInfoDaoManager.insertOrReplace(dicInfo);
            }
            spinner_role.setListDatas(getBVActivity(), mSpinnerRoleList);
            spinner_role.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mRoleId = dicInfo.getCode();
                    mRoleName = dicInfo.getText();
                    if (mRoleId.equals("13") || mRoleId.equals("14")) {
                        spinner_type.setText("企业用户");
                        mUserType = "2";
                        mTypeName = "企业用户";
                        spinner_company.getTextView().setEnabled(true);
                    } else if (mRoleId.equals("1")) {
                        spinner_type.setText("系统用户");
                        mUserType = "1";
                        mTypeName = "系统用户";
                        spinner_company.getTextView().setEnabled(false);
                    } else {
                        mUserType = "";
                        mTypeName = "";
                        spinner_type.setText("");
                        spinner_company.getTextView().setEnabled(false);
                    }
                }
            });
        }
    }

    @Override
    public void saveOrcreateUser(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String msg = "";
        if (TextUtils.isEmpty(mUserId) && mLocalId == 0l) {
            msg = "用户创建成功";
        } else {
            msg = "用户修改成功";
        }
        if (isLocal) {
            CompanyUserInfoBean companyUserInfoBean = null;
            if (mLocalId == 0l) {
                companyUserInfoBean = new CompanyUserInfoBean(mUserId, mHeadCode, mCompanyId, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), mCompanyName, StringUtil.getText(et_nickname), mRoleName, mStateType, mStateName, StringUtil.getText(et_account), mUserType, mTypeName, StringUtil.getText(et_password), StringUtil.getText(et_address), StringUtil.getText(et_phone), StringUtil.getText(et_email), mRoleId, mRoleName, mRoleId, false, isLocal);
                mUserInfoBeanDaoManager.insertOrReplace(companyUserInfoBean);
            } else {
                for (CompanyUserInfoBean info : mUserInfoLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyUserInfoBean = new CompanyUserInfoBean(info.getLocalId(), mUserId, mHeadCode, mCompanyId, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), mCompanyName, StringUtil.getText(et_nickname), mRoleName, mStateType, mStateName, StringUtil.getText(et_account), mUserType, mTypeName, StringUtil.getText(et_password), StringUtil.getText(et_address), StringUtil.getText(et_phone), StringUtil.getText(et_email), mRoleId, mRoleName, mRoleId, false, isLocal);
                        mUserInfoBeanDaoManager.correct(companyUserInfoBean);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveUserEvent(msg));
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
            case REQUEST_UPLOAD_IMAGE:
                FileInfoBean localInfoBean = new FileInfoBean(mLocalPath);
                uploadImages(localInfoBean);
                break;
            case REQUEST_USER_TYPE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(USER_TYPE)) {
                        typeList.add(dicInfo);
                    }
                }
                getUserType(typeList);
                break;
            case REQUEST_COMPANY_STATE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(STATE_CODE)) {
                        stateList.add(dicInfo);
                    }
                }
                getStateType(stateList);
                break;
            case REQUEST_GET_USER_INFO:
                CompanyUserInfoBean companyInfo = null;
                for (CompanyUserInfoBean info : mUserInfoLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyInfo = info;
                    }
                }
                getUserInfo(companyInfo);
                break;
            case REQUEST_GET_ALL_COMPANY:
                List<DicInfo2> allCompany = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_COMPANY)) {
                        allCompany.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllCompany(allCompany);
                break;
            case REQUEST_QUERY_ALL_ROLES:
                List<RoleInfoBean> roleAllList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_ROLES)) {
                        roleAllList.add(new RoleInfoBean(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllRoles(roleAllList);
                break;
            case REQUEST_SAVE_COMPANY_USER:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mUserId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveOrcreateUser(uploadInfoBean, true);
                break;
        }
    }
}
