package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.LoginActivity;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.glide.GlideApp;
import com.africa.crm.businessmanagement.main.photo.FileUtils;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.UploadPictureContract;
import com.africa.crm.businessmanagement.main.station.presenter.UploadPicturePresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.LoginOutDialog;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/27 0027 17:57
 * Modification  History:
 * Why & What is modified:
 */
public class SettingActivity extends BaseMvpActivity<UploadPicturePresenter> implements UploadPictureContract.View, SinglePopup.OnPopItemClickListener, CameraCore.CameraResult {
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;
    @BindView(R.id.iv_head_icon)
    ImageView iv_head_icon;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_user_type)
    TextView tv_user_type;
    @BindView(R.id.tv_unload_data)
    TextView tv_unload_data;
    @BindView(R.id.tv_login_out)
    TextView tv_login_out;

    private WorkStationInfo mWorkStationInfo;
    private LoginOutDialog mLoginOutDialog;

    private CameraCore cameraCore;
    private SinglePopup singlePopup;

    private String mUserId = "";
    private String mUserName = "";//用户名
    private String mType = "";//类型
    private String mRoleIds = "";//角色ID
    private String mName = "";//昵称
    private String mPhone = "";//手机号
    private String mAddress = "";//地址
    private String mEmail = "";//电子邮箱
    private String mState = "";//状态
    private String mCompanyId = "";//企业ID
    private String mHead = "";//头像ID
    private String mLocalPath = "";//头像本地路径

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, SettingActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        super.initView();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        iv_head_icon.setOnClickListener(this);
        tv_unload_data.setOnClickListener(this);
        tv_login_out.setOnClickListener(this);
        mLoginOutDialog = LoginOutDialog.getInstance(this);

        cameraCore = new CameraCore.Builder(this)
                .setNeedCrop(true)
                .setZipInfo(new CameraCore.ZipInfo(true, 200, 200, 100 * 1024))
                .build();
    }

    @Override
    public void initData() {
    }

    @Override
    protected UploadPicturePresenter injectPresenter() {
        return new UploadPicturePresenter();
    }

    @Override
    protected void requestData() {
        if (!TextUtils.isEmpty(mUserId)) {
            mPresenter.getUserInfo(mUserId);
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_head_icon:
                if (singlePopup == null) {
                    List<String> list = new ArrayList<>();
                    list.add("拍照");
                    list.add("从相册选择");
                    singlePopup = new SinglePopup(this, list, this);
                    singlePopup.setTitle(View.GONE, "选择来源");
                }
                singlePopup.showAtLocation(ll_setting, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_unload_data:
                toastMsg("上传数据");
                break;
            case R.id.tv_login_out:
                mLoginOutDialog.isCancelableOnTouchOutside(false)
                        .withDuration(300)
                        .withEffect(Effectstype.Fadein)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                UserInfoManager.deleteUserInfo(SettingActivity.this);
                                MyApplication.getInstance().finishAllActivities();
                                LoginActivity.startActivityForResult(SettingActivity.this, 0);
                                mLoginOutDialog.dismiss();
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mLoginOutDialog.dismiss();
                            }
                        })
                        .show();

                break;
        }
    }

    @Override
    public void getUserInfo(UserInfo userInfo) {
        mUserId = userInfo.getId();
        mUserName = userInfo.getUserName();
        mType = userInfo.getType();
        mRoleIds = userInfo.getRoleId();
        mName = userInfo.getName();
        mPhone = userInfo.getPhone();
        mAddress = userInfo.getAddress();
        mEmail = userInfo.getEmail();
        mState = userInfo.getState();
        mCompanyId = userInfo.getCompanyId();
        mHead = userInfo.getHead();
        tv_username.setText(userInfo.getName());
        tv_company_name.setText(userInfo.getCompanyName());
        tv_user_type.setText(userInfo.getTypeName());
        mPresenter.downLoadFile(userInfo.getHead());
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
    public void fail(int code, String message) {
        toastMsg(message);
    }

    @Override
    public void uploadImages(FileInfoBean fileInfoBean) {
        if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
            mHead = fileInfoBean.getCode();
            mPresenter.downLoadFile(fileInfoBean.getCode());
        }
    }


    @Override
    public void downLoadFile(ResponseBody responseBody) {
        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CRM";
        mLocalPath = FileUtils.saveFile(responseBody, fileDir, String.valueOf(System.currentTimeMillis()));
        mPresenter.saveUserInfo(mUserId, mUserName, mType, mRoleIds, "", mName, mPhone, mAddress, mEmail, mState, mCompanyId, mHead);
    }

    @Override
    public void saveUserInfo(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            GlideApp.with(this)
                    .load(mLocalPath)
                    .centerCrop()
                    .placeholder(R.drawable.iv_no_icon)
                    .transform(new RoundedCorners(DensityUtil.dp2px(1000)))
                    .into(iv_head_icon);
        }
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
