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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyAccountEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.CompanyAccountDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyAccountDetailPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_ROLES;
import static com.africa.crm.businessmanagement.network.api.DicUtil.STATE_CODE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_ACCOUNT_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_QUERY_ALL_ROLES;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_ACCOUNT;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;

public class CompanyAccountDetailActivity extends BaseMvpActivity<CompanyAccountDetailPresenter> implements CompanyAccountDetailContract.View, SinglePopup.OnPopItemClickListener, CameraCore.CameraResult {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_add_icon)
    TextView tv_add_icon;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.ll_password)
    LinearLayout ll_password;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.et_role_name)
    EditText et_role_name;
    @BindView(R.id.et_role_code)
    EditText et_role_code;
    @BindView(R.id.spinner_role)
    MySpinner spinner_role;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.tv_save)
    TextView tv_save;


    @BindView(R.id.spinner_user_type)
    MySpinner spinner_user_type;
    private String mUserType = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private List<DicInfo> mSpinnerStateList = new ArrayList<>();
    private String mState = "";

    private List<DicInfo> mSpinnerRoleList = new ArrayList<>();
    private String mRoleId = "";

    private String mAccountId;//账号ID
    private String mCompanyId;//所属企业ID
    private String mCompanyName;//所属企业名称
    private Long mLocalId = 0l;//本地数据库ID

    private CameraCore cameraCore;
    private SinglePopup singlePopup;
    private String mHeadCode = "";//头像ID
    private String mLocalPath = "";

    private GreendaoManager<CompanyAccountInfo, CompanyAccountInfoDao> mAccountInfoDaoManager;
    private List<CompanyAccountInfo> mCompanyAccountLocalInfoList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String accountId, Long localId) {
        Intent intent = new Intent(activity, CompanyAccountDetailActivity.class);
        intent.putExtra("accountId", accountId);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_account_detail);
    }

    @Override
    protected CompanyAccountDetailPresenter injectPresenter() {
        return new CompanyAccountDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mAccountId = getIntent().getStringExtra("accountId");
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        titlebar_name.setText(getString(R.string.Account_Details));
        tv_save.setOnClickListener(this);
        spinner_user_type.getTextView().setEnabled(false);
        spinner_user_type.setText(getString(R.string.Enterprise_user));
        mUserType = "2";
        if (TextUtils.isEmpty(mAccountId) && mLocalId == 0l) {
//            ll_password.setVisibility(View.VISIBLE);
            titlebar_right.setVisibility(View.GONE);
            spinner_role.setEnabled(true);
            tv_save.setText(R.string.Add);
            tv_save.setVisibility(View.VISIBLE);
            et_role_name.setEnabled(true);
            et_role_code.setEnabled(true);
        } else if (!TextUtils.isEmpty(mAccountId) || mLocalId != 0l) {
//            ll_password.setVisibility(View.GONE);
            titlebar_right.setText(R.string.Edit);
            tv_save.setText(R.string.Save);
            spinner_role.setEnabled(false);
            et_role_name.setEnabled(false);
            et_role_code.setEnabled(false);
            setEditTextInput(false);
        }
        tv_add_icon.setOnClickListener(this);
        cameraCore = new CameraCore.Builder(this)
                .setNeedCrop(true)
                .setZipInfo(new CameraCore.ZipInfo(true, 200, 200, 100 * 1024))
                .build();

        //得到Dao对象管理器
        mAccountInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyAccountInfoDao());
        //得到本地数据
        mCompanyAccountLocalInfoList = mAccountInfoDaoManager.queryAll();
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
        mPresenter.getState(STATE_CODE);
        mPresenter.getAllRoles();
        if (!TextUtils.isEmpty(mAccountId) || mLocalId != 0l)
            mPresenter.getCompanyAccountDetail(mAccountId);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_add_icon:
                if (singlePopup == null) {
                    List<String> list = new ArrayList<>();
                    list.add(getString(R.string.Takephoto));
                    list.add(getString(R.string.Select_from_album));
                    singlePopup = new SinglePopup(this, list, this);
                    singlePopup.setTitle(View.GONE, getString(R.string.Select_source));
                }
                singlePopup.showAtLocation(tv_add_icon, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.Edit))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_save.setVisibility(View.VISIBLE);
                    setEditTextInput(true);
                } else {
                    titlebar_right.setText(R.string.Edit);
                    tv_save.setVisibility(View.GONE);
                    setEditTextInput(false);
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_username.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_fill_in_the_name));
                    return;
                }
                if (TextUtils.isEmpty(mAccountId) && mLocalId == 0l) {
                    if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
                        toastMsg(getString(R.string.Please_fill_in_the_password));
                        return;
                    }
                }
                if (TextUtils.isEmpty(mRoleId)) {
                    toastMsg(getString(R.string.Please_select_a_role_classification));
                    return;
                }
                if (TextUtils.isEmpty(mState)) {
                    toastMsg(getString(R.string.Please_select_a_user_status));
                    return;
                }
                mPresenter.saveCompanyAccount(mAccountId, et_username.getText().toString().trim(), mUserType, mRoleId, et_password.getText().toString().trim(), et_nickname.getText().toString().trim(), et_phone.getText().toString().trim(), et_address.getText().toString().trim(), et_email.getText().toString().trim(), mState, mCompanyId, mHeadCode);
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_username.setEnabled(canInput);
        et_nickname.setEnabled(canInput);
        et_password.setEnabled(canInput);
        spinner_role.getTextView().setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_email.setEnabled(canInput);
        et_phone.setEnabled(canInput);
        spinner_state.getTextView().setEnabled(canInput);
        tv_add_icon.setEnabled(canInput);
    }

    @Override
    public void getState(List<DicInfo> dicInfoList) {
        mSpinnerStateList.clear();
        mSpinnerStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(STATE_CODE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
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
    public void getAllRoles(final List<RoleInfoBean> roleInfoBeanList) {
        mSpinnerRoleList.clear();
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
                et_role_code.setText(roleInfoBeanList.get(position).getRoleCode());
                et_role_name.setText(roleInfoBeanList.get(position).getRoleName());
                mRoleId = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyAccountDetail(CompanyAccountInfo companyAccountInfo) {
        if (companyAccountInfo != null) {
            et_username.setText(companyAccountInfo.getUserName());
            et_nickname.setText(companyAccountInfo.getName());
            et_role_name.setText(companyAccountInfo.getRoleName());
            et_role_code.setText(companyAccountInfo.getRoleCode());
            spinner_role.setText(companyAccountInfo.getRoleName());
            mRoleId = companyAccountInfo.getRoleId();
            et_address.setText(companyAccountInfo.getAddress());
            et_email.setText(companyAccountInfo.getEmail());
            et_phone.setText(companyAccountInfo.getPhone());
            spinner_user_type.setText(companyAccountInfo.getTypeName());
            mUserType = companyAccountInfo.getType();
            spinner_state.setText(companyAccountInfo.getStateName());
            mState = companyAccountInfo.getState();
            mCompanyName = companyAccountInfo.getCompanyName();
            //头像
            mHeadCode = companyAccountInfo.getHead();
            GlideUtil.showImg(iv_icon, mHeadCode);
            for (CompanyAccountInfo localInfo : mCompanyAccountLocalInfoList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyAccountInfo.getId())) {
                    if (localInfo.getId().equals(companyAccountInfo.getId())) {
                        companyAccountInfo.setLocalId(localInfo.getLocalId());
                        mAccountInfoDaoManager.correct(companyAccountInfo);
                    }
                }
            }
        }
    }

    @Override
    public void saveCompanyAccount(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mAccountId) && mLocalId == 0l) {
            toastString = getString(R.string.Added_Successfully);
        } else {
            toastString = getString(R.string.Successfully_Modified);
        }
        if (isLocal) {
            CompanyAccountInfo companyAccountInfo = null;
            if (mLocalId == 0l) {
                companyAccountInfo = new CompanyAccountInfo(mAccountId, TimeUtils.getCurrentTime(new Date()), StringUtil.getText(et_nickname), StringUtil.getText(et_username), mRoleId, spinner_role.getText(), mCompanyId, mHeadCode, mCompanyName, mUserType, spinner_user_type.getText(), mState, spinner_state.getText(), StringUtil.getText(et_address), spinner_role.getText(), StringUtil.getText(et_phone), mRoleId, StringUtil.getText(et_email), StringUtil.getText(et_password), false, isLocal);
                mAccountInfoDaoManager.insertOrReplace(companyAccountInfo);
            } else {
                for (CompanyAccountInfo info : mCompanyAccountLocalInfoList) {
                    if (mLocalId == info.getLocalId()) {
                        companyAccountInfo = new CompanyAccountInfo(info.getLocalId(), mAccountId, TimeUtils.getCurrentTime(new Date()), StringUtil.getText(et_nickname), StringUtil.getText(et_username), mRoleId, spinner_role.getText(), mCompanyId, mHeadCode, mCompanyName, mUserType, spinner_user_type.getText(), mState, spinner_state.getText(), StringUtil.getText(et_address), spinner_role.getText(), StringUtil.getText(et_phone), mRoleId, StringUtil.getText(et_email), StringUtil.getText(et_password), false, isLocal);
                        mAccountInfoDaoManager.correct(companyAccountInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyAccountEvent(toastString));
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
            case REQUEST_COMPANY_STATE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(STATE_CODE)) {
                        stateList.add(dicInfo);
                    }
                }
                getState(stateList);
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
            case REQUEST_COMPANY_ACCOUNT_DETAIL:
                CompanyAccountInfo companyAccountInfo = null;
                for (CompanyAccountInfo info : mCompanyAccountLocalInfoList) {
                    if (mLocalId == info.getLocalId()) {
                        companyAccountInfo = info;
                    }
                }
                getCompanyAccountDetail(companyAccountInfo);
                break;
            case REQUEST_UPLOAD_IMAGE:
                FileInfoBean localInfoBean = new FileInfoBean(mLocalPath);
                uploadImages(localInfoBean);
                break;
            case REQUEST_SAVE_COMPANY_ACCOUNT:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mAccountId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyAccount(uploadInfoBean, true);
                break;
        }
    }
}
