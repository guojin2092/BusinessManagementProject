package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteAccountInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.UploadPictureContract;
import com.africa.crm.businessmanagement.main.station.presenter.UploadPicturePresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.error.ComException;
import com.africa.crm.businessmanagement.network.util.RxUtils;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.LoginOutDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

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
    @BindView(R.id.tv_user_role)
    TextView tv_user_role;
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

    /**
     * 企业信息本地数据库
     */
    private GreendaoManager<CompanyInfo, CompanyInfoDao> mCompanyInfoDaoGreendaoManager;
    private List<CompanyInfo> mCompanyInfoLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteInfo, CompanyDeleteInfoDao> mCompanyDeleteInfoDaoGreendaoManager;
    private List<CompanyDeleteInfo> mCompanyDeleteInfoList = new ArrayList<>();//本地数据

    /**
     * 企业账号本地数据库
     */
    private GreendaoManager<CompanyAccountInfo, CompanyAccountInfoDao> mAccountInfoDaoManager;
    private List<CompanyAccountInfo> mCompanyAccountInfoLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteAccountInfo, CompanyDeleteAccountInfoDao> mDeleteAccountInfoDaoManager;
    private List<CompanyDeleteAccountInfo> mCompanyDeleteAccountLocalList = new ArrayList<>();//本地数据

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
        //企业信息dao
        mCompanyInfoDaoGreendaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyInfoDao());
        mCompanyInfoLocalList = mCompanyInfoDaoGreendaoManager.queryAll();
        //删除企业信息dao
        mCompanyDeleteInfoDaoGreendaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteInfoDao());
        mCompanyDeleteInfoList = mCompanyDeleteInfoDaoGreendaoManager.queryAll();
        //企业账号dao
        mAccountInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyAccountInfoDao());
        mCompanyAccountInfoLocalList = mAccountInfoDaoManager.queryAll();
        //删除企业账号dao
        mDeleteAccountInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteAccountInfoDao());
        mCompanyDeleteAccountLocalList = mDeleteAccountInfoDaoManager.queryAll();
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
                upLoadDatasMethod();
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
        tv_username.setText(userInfo.getName());
        tv_company_name.setText(userInfo.getCompanyName());
        tv_user_role.setText(userInfo.getRoleName());
        mHead = userInfo.getHead();
        GlideUtil.showImg(iv_head_icon, mHead);
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
            mPresenter.saveUserInfo(mUserId, mUserName, mType, mRoleIds, "", mName, mPhone, mAddress, mEmail, mState, mCompanyId, mHead);
        }
    }

    @Override
    public void saveUserInfo(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            GlideUtil.showImg(iv_head_icon, mHead);
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

    /**
     * 上传本地数据到后台
     */
    private void upLoadDatasMethod() {
        /**
         * 企业信息模块
         */
        for (final CompanyDeleteInfo companyDeleteInfo : mCompanyDeleteInfoList) {
            addDisposable(mDataManager.deleteCompanyInfo(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mCompanyDeleteInfoDaoGreendaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanyInfo companyInfo : mCompanyInfoLocalList) {
            final String[] mHeadCode = {""};
            if (companyInfo.isLocal()) {
                if (companyInfo.getHead().contains(".jpg")) {
                    addDisposable(mDataManager.uploadImages(companyInfo.getHead())
                            .flatMap(new Function<FileInfoBean, ObservableSource<UploadInfoBean>>() {
                                @Override
                                public ObservableSource<UploadInfoBean> apply(FileInfoBean fileInfoBean) throws Exception {
                                    if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
                                        mHeadCode[0] = fileInfoBean.getCode();
                                        return mDataManager.saveCompanyInfo(companyInfo.getId(), mHeadCode[0], companyInfo.getName(), companyInfo.getCode(), companyInfo.getType(), companyInfo.getAddress(), companyInfo.getPhone(), companyInfo.getEmail(), companyInfo.getMid(), companyInfo.getArea(), companyInfo.getProfession(), companyInfo.getNumA(), companyInfo.getState());
                                    } else {
                                        return Observable.error(new ComException("上传失败，请重试"));
                                    }
                                }
                            }).compose(RxUtils.<UploadInfoBean>ioToMain(this))
                            .subscribe(new Consumer<UploadInfoBean>() {
                                @Override
                                public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                    companyInfo.setId(uploadInfoBean.getId());
                                    companyInfo.setHead(mHeadCode[0]);
                                    companyInfo.setIsLocal(false);
                                    companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                    mCompanyInfoDaoGreendaoManager.correct(companyInfo);
                                }
                            }, new ComConsumer(this)));
                } else {
                    addDisposable(mDataManager.saveCompanyInfo(companyInfo.getId(), companyInfo.getHead(), companyInfo.getName(), companyInfo.getCode(), companyInfo.getType(), companyInfo.getAddress(), companyInfo.getPhone(), companyInfo.getEmail(), companyInfo.getMid(), companyInfo.getArea(), companyInfo.getProfession(), companyInfo.getNumA(), companyInfo.getState())
                            .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                            .subscribe(new Consumer<UploadInfoBean>() {
                                @Override
                                public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                    companyInfo.setId(uploadInfoBean.getId());
                                    companyInfo.setIsLocal(false);
                                    companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                    mCompanyInfoDaoGreendaoManager.correct(companyInfo);
                                }
                            }, new ComConsumer(this)));
                }
            }
        }

        /**
         * 企业账号模块
         */
        for (final CompanyDeleteAccountInfo companyDeleteInfo : mCompanyDeleteAccountLocalList) {
            addDisposable(mDataManager.deleteCompanyAccount(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeleteAccountInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanyAccountInfo companyInfo : mCompanyAccountInfoLocalList) {
            final String[] mHeadCode = {""};
            if (companyInfo.isLocal()) {
                if (companyInfo.getHead().contains(".jpg")) {
                    addDisposable(mDataManager.uploadImages(companyInfo.getHead())
                            .flatMap(new Function<FileInfoBean, ObservableSource<UploadInfoBean>>() {
                                @Override
                                public ObservableSource<UploadInfoBean> apply(FileInfoBean fileInfoBean) throws Exception {
                                    if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
                                        mHeadCode[0] = fileInfoBean.getCode();
                                        return mDataManager.saveCompanyAccount(companyInfo.getId(), companyInfo.getUserName(), companyInfo.getType(), companyInfo.getRoleId(), companyInfo.getPassword(), companyInfo.getName(), companyInfo.getPhone(), companyInfo.getAddress(), companyInfo.getEmail(), companyInfo.getState(), companyInfo.getCompanyId(), mHeadCode[0]);
                                    } else {
                                        return Observable.error(new ComException("上传失败，请重试"));
                                    }
                                }
                            }).compose(RxUtils.<UploadInfoBean>ioToMain(this))
                            .subscribe(new Consumer<UploadInfoBean>() {
                                @Override
                                public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                    companyInfo.setId(uploadInfoBean.getId());
                                    companyInfo.setHead(mHeadCode[0]);
                                    companyInfo.setIsLocal(false);
                                    companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                    mAccountInfoDaoManager.correct(companyInfo);
                                    toastMsg("数据上传成功");
                                }
                            }, new ComConsumer(this)));
                } else {
                    addDisposable(mDataManager.saveCompanyAccount(companyInfo.getId(), companyInfo.getUserName(), companyInfo.getType(), companyInfo.getRoleId(), companyInfo.getPassword(), companyInfo.getName(), companyInfo.getPhone(), companyInfo.getAddress(), companyInfo.getEmail(), companyInfo.getState(), companyInfo.getCompanyId(), companyInfo.getHead())
                            .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                            .subscribe(new Consumer<UploadInfoBean>() {
                                @Override
                                public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                    companyInfo.setId(uploadInfoBean.getId());
                                    companyInfo.setIsLocal(false);
                                    companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                    mAccountInfoDaoManager.correct(companyInfo);
                                    toastMsg("数据上传成功");
                                }
                            }, new ComConsumer(this)));
                }
            }
        }
    }
}
