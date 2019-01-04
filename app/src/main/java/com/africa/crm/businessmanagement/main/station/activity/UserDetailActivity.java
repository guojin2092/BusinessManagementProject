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

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveUserEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.UserDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.UserDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.MySpinner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class UserDetailActivity extends BaseMvpActivity<UserDetailPresenter> implements UserDetailContract.View, SinglePopup.OnPopItemClickListener, CameraCore.CameraResult {
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

    private List<DicInfo> mSpinnerRoleList = new ArrayList<>();
    private String mRoleId = "";

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private static final String CONTACT_USER_TYPE = "USERTYPE";
    private List<DicInfo> mSpinnerUserList = new ArrayList<>();
    private String mUserType = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private static final String CONTACT_STATE_TYPE = "STATE";
    private List<DicInfo> mSpinnerStateList = new ArrayList<>();
    private String mStateType = "";
    private String mUserId = "";

    private CameraCore cameraCore;
    private SinglePopup singlePopup;
    private String mHeadCode = "";//头像ID

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String userId) {
        Intent intent = new Intent(activity, UserDetailActivity.class);
        intent.putExtra("userId", userId);
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
        titlebar_name.setText("用户详情");
        tv_add_icon.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        spinner_type.getTextView().setEnabled(false);
        if (!TextUtils.isEmpty(mUserId)) {
            ll_password.setVisibility(View.GONE);
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        } else {
            ll_password.setVisibility(View.VISIBLE);
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        }
        cameraCore = new CameraCore.Builder(this)
                .setNeedCrop(true)
                .setZipInfo(new CameraCore.ZipInfo(true, 200, 200, 100 * 1024))
                .build();

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
        mPresenter.getUserType(CONTACT_USER_TYPE);
        mPresenter.getStateType(CONTACT_STATE_TYPE);
        mPresenter.getAllCompany("");
        mPresenter.getAllRoles("");
        if (!TextUtils.isEmpty(mUserId))
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
        spinner_type.setListDatas(this, mSpinnerUserList);

        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mUserType = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getStateType(List<DicInfo> dicInfoList) {
        mSpinnerStateList.clear();
        mSpinnerStateList.addAll(dicInfoList);
        spinner_state.setListDatas(this, mSpinnerStateList);

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateType = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getUserInfo(UserInfo userInfo) {
        mUserType = userInfo.getType();
        mStateType = userInfo.getState();
        mRoleId = userInfo.getRoleId();
        mCompanyId = userInfo.getCompanyId();
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
    }

    @Override
    public void saveOrcreateUser(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String msg = "";
            if (TextUtils.isEmpty(mUserId)) {
                msg = "用户创建成功";
            } else {
                msg = "用户修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveUserEvent(msg));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }

    @Override
    public void getAllCompany(List<DicInfo2> dicInfoList) {
        if (!ListUtils.isEmpty(dicInfoList)) {
            for (int i = 0; i < dicInfoList.size(); i++) {
                DicInfo dicInfo = new DicInfo(dicInfoList.get(i).getName(), dicInfoList.get(i).getId());
                mSpinnerCompanyList.add(dicInfo);
            }
            spinner_company.setListDatas(getBVActivity(), mSpinnerCompanyList);
            spinner_company.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mCompanyId = dicInfo.getCode();
                }
            });
        }
    }

    @Override
    public void getAllRoles(List<RoleInfoBean> roleInfoBeanList) {
        if (!ListUtils.isEmpty(roleInfoBeanList)) {
            for (RoleInfoBean roleInfoBean : roleInfoBeanList) {
                DicInfo dicInfo = new DicInfo(roleInfoBean.getRoleName(), roleInfoBean.getId());
                mSpinnerRoleList.add(dicInfo);
            }
            spinner_role.setListDatas(getBVActivity(), mSpinnerRoleList);
            spinner_role.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mRoleId = dicInfo.getCode();
                    if (mRoleId.equals("13") || mRoleId.equals("14")) {
                        spinner_type.setText("企业用户");
                        mUserType = "2";
                        spinner_company.getTextView().setEnabled(true);
                    } else if (mRoleId.equals("1")) {
                        spinner_type.setText("系统用户");
                        mUserType = "1";
                        spinner_company.getTextView().setEnabled(false);
                    } else {
                        mUserType = "";
                        spinner_type.setText("");
                        spinner_company.getTextView().setEnabled(false);
                    }
                }
            });
        }
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
            mPresenter.uploadImages(path);
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
}
