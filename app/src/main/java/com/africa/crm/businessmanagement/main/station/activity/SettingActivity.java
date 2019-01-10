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
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteAccountInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteClientInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteContactInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteDeliveryOrderInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeletePayOrderInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteProductInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteQuotationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteSalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteServiceRecordInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteSupplierInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteTradingOrderInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyClientInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyContactInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteClientInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteContactInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteDeliveryOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeletePayOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteProductInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteQuotationInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteSalesOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteServiceRecordInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteSupplierInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteTradingOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeliveryOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyInventoryInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyPayOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyProductInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyQuotationInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanySalesOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyServiceRecordInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanySupplierInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyTradingOrderInfoDao;
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
     * 企业供应商本地数据库
     */
    private GreendaoManager<CompanySupplierInfo, CompanySupplierInfoDao> mCompanySupplierInfoDaoManager;
    private List<CompanySupplierInfo> mCompanySupplierLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteSupplierInfo, CompanyDeleteSupplierInfoDao> mDeleteSupplierInfoDaoManager;
    private List<CompanyDeleteSupplierInfo> mCompanyDeleteSupplierInfoList = new ArrayList<>();//本地数据

    /**
     * 企业产品本地数据库
     */
    private GreendaoManager<CompanyProductInfo, CompanyProductInfoDao> mProductInfoDaoManager;
    private List<CompanyProductInfo> mProductInfoLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteProductInfo, CompanyDeleteProductInfoDao> mDeleteProductInfoDaoManager;
    private List<CompanyDeleteProductInfo> mDeleteProductInfoList = new ArrayList<>();//本地数据

    /**
     * 企业用户本地数据库
     */
    private GreendaoManager<CompanyClientInfo, CompanyClientInfoDao> mClientInfoDaoManager;
    private List<CompanyClientInfo> mCompanyClientLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteClientInfo, CompanyDeleteClientInfoDao> mDeleteClientInfoDaoManager;
    private List<CompanyDeleteClientInfo> mCompanyDeleteClientLocalList = new ArrayList<>();//本地数据

    /**
     * 企业联系人本地数据库
     */
    private GreendaoManager<CompanyContactInfo, CompanyContactInfoDao> mContactInfoDaoManager;
    private List<CompanyContactInfo> mCompanyContactLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteContactInfo, CompanyDeleteContactInfoDao> mDeleteContactInfoDaoManager;
    private List<CompanyDeleteContactInfo> mCompanyDeleteContactLocalList = new ArrayList<>();//本地数据

    /**
     * 企业交易单本地数据库
     */
    private GreendaoManager<CompanyTradingOrderInfo, CompanyTradingOrderInfoDao> mTradingOrderInfoDaoManager;
    private List<CompanyTradingOrderInfo> mTradingOrderLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteTradingOrderInfo, CompanyDeleteTradingOrderInfoDao> mDeleteTradingOrderInfoDaoManager;
    private List<CompanyDeleteTradingOrderInfo> mCompanyDeleteTradingOrderLocalList = new ArrayList<>();//本地数据

    /**
     * 企业报价单本地数据库
     */
    private GreendaoManager<CompanyQuotationInfo, CompanyQuotationInfoDao> mQuotationInfoDaoManager;
    private List<CompanyQuotationInfo> mCompanyQuotationLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteQuotationInfo, CompanyDeleteQuotationInfoDao> mDeleteQuotationInfoDaoManager;
    private List<CompanyDeleteQuotationInfo> mCompanyDeleteQuotationLocalList = new ArrayList<>();//本地数据

    /**
     * 企业销售单本地数据库
     */
    private GreendaoManager<CompanySalesOrderInfo, CompanySalesOrderInfoDao> mSalesOrderInfoDaoManager;
    private List<CompanySalesOrderInfo> mCompanySalesOrderLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteSalesOrderInfo, CompanyDeleteSalesOrderInfoDao> mDeleteSalesOrderInfoDaoManager;
    private List<CompanyDeleteSalesOrderInfo> mCompanyDeleteSalesOrderLocalList = new ArrayList<>();//本地数据

    /**
     * 企业发货单本地数据库
     */
    private GreendaoManager<CompanyDeliveryOrderInfo, CompanyDeliveryOrderInfoDao> mDeliveryOrderInfoDaoManager;
    private List<CompanyDeliveryOrderInfo> mDeliveryOrderLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteDeliveryOrderInfo, CompanyDeleteDeliveryOrderInfoDao> mDeleteDeliveryOrderInfoDaoManager;
    private List<CompanyDeleteDeliveryOrderInfo> mDeleteDeliveryOrderLocalList = new ArrayList<>();//本地数据

    /**
     * 企业付款单本地数据库
     */
    private GreendaoManager<CompanyPayOrderInfo, CompanyPayOrderInfoDao> mPayOrderInfoDaoManager;
    private List<CompanyPayOrderInfo> mPayOrderLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeletePayOrderInfo, CompanyDeletePayOrderInfoDao> mDeletePayOrderInfoDaoManager;
    private List<CompanyDeletePayOrderInfo> mDeletePayOrderLocalList = new ArrayList<>();//本地数据

    /**
     * 企业服务记录本地数据库
     */
    private GreendaoManager<CompanyServiceRecordInfo, CompanyServiceRecordInfoDao> mServiceRecordInfoDaoManager;
    private List<CompanyServiceRecordInfo> mServiceRecordLocalList = new ArrayList<>();//本地数据
    private GreendaoManager<CompanyDeleteServiceRecordInfo, CompanyDeleteServiceRecordInfoDao> mDeleteServiceRecordInfoDaoManager;
    private List<CompanyDeleteServiceRecordInfo> mDeleteServiceRecordLocalList = new ArrayList<>();//本地数据

    /**
     * 企业库存管理本地数据库
     */
    private GreendaoManager<CompanyInventoryInfo, CompanyInventoryInfoDao> mInventoryInfoDaoManager;
    private List<CompanyInventoryInfo> mInventoryLocalList = new ArrayList<>();//本地数据

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
        initGreenDao();
    }

    /**
     * 初始化各模块GreenDao
     */
    private void initGreenDao() {
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
        //企业供应商dao
        mCompanySupplierInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanySupplierInfoDao());
        mCompanySupplierLocalList = mCompanySupplierInfoDaoManager.queryAll();
        //删除企业供应商dao
        mDeleteSupplierInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteSupplierInfoDao());
        mCompanyDeleteSupplierInfoList = mDeleteSupplierInfoDaoManager.queryAll();
        //企业产品dao
        mProductInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyProductInfoDao());
        mProductInfoLocalList = mProductInfoDaoManager.queryAll();
        //删除企业产品dao
        mDeleteProductInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteProductInfoDao());
        mDeleteProductInfoList = mDeleteProductInfoDaoManager.queryAll();
        //企业用户dao
        mClientInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyClientInfoDao());
        mCompanyClientLocalList = mClientInfoDaoManager.queryAll();
        //删除企业用户dao
        mDeleteClientInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteClientInfoDao());
        mCompanyDeleteClientLocalList = mDeleteClientInfoDaoManager.queryAll();
        //企业联系人dao
        mContactInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyContactInfoDao());
        mCompanyContactLocalList = mContactInfoDaoManager.queryAll();
        //删除企业联系人dao
        mDeleteContactInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteContactInfoDao());
        mCompanyDeleteContactLocalList = mDeleteContactInfoDaoManager.queryAll();
        //企业交易单dao
        mTradingOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyTradingOrderInfoDao());
        mTradingOrderLocalList = mTradingOrderInfoDaoManager.queryAll();
        //删除企业交易单dao
        mDeleteTradingOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteTradingOrderInfoDao());
        mCompanyDeleteTradingOrderLocalList = mDeleteTradingOrderInfoDaoManager.queryAll();
        //企业报价单dao
        mQuotationInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyQuotationInfoDao());
        mCompanyQuotationLocalList = mQuotationInfoDaoManager.queryAll();
        //删除企业报价单dao
        mDeleteQuotationInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteQuotationInfoDao());
        mCompanyDeleteQuotationLocalList = mDeleteQuotationInfoDaoManager.queryAll();
        //企业销售单dao
        mSalesOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanySalesOrderInfoDao());
        mCompanySalesOrderLocalList = mSalesOrderInfoDaoManager.queryAll();
        //删除企业销售单dao
        mDeleteSalesOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteSalesOrderInfoDao());
        mCompanyDeleteSalesOrderLocalList = mDeleteSalesOrderInfoDaoManager.queryAll();
        //企业发货单dao
        mDeliveryOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeliveryOrderInfoDao());
        mDeliveryOrderLocalList = mDeliveryOrderInfoDaoManager.queryAll();
        //删除企业发货单dao
        mDeleteDeliveryOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteDeliveryOrderInfoDao());
        mDeleteDeliveryOrderLocalList = mDeleteDeliveryOrderInfoDaoManager.queryAll();
        //企业付款单dao
        mPayOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyPayOrderInfoDao());
        mPayOrderLocalList = mPayOrderInfoDaoManager.queryAll();
        //删除企业付款单dao
        mDeletePayOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeletePayOrderInfoDao());
        mDeletePayOrderLocalList = mDeletePayOrderInfoDaoManager.queryAll();
        //企业服务记录dao
        mServiceRecordInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyServiceRecordInfoDao());
        mServiceRecordLocalList = mServiceRecordInfoDaoManager.queryAll();
        //删除企业服务记录dao
        mDeleteServiceRecordInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteServiceRecordInfoDao());
        mDeleteServiceRecordLocalList = mDeleteServiceRecordInfoDaoManager.queryAll();
        //企业库存dao
        mInventoryInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyInventoryInfoDao());
        mInventoryLocalList = mInventoryInfoDaoManager.queryAll();
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
                                mCompanyInfoDaoGreendaoManager.deleteAll();
                                mCompanyDeleteInfoDaoGreendaoManager.deleteAll();
                                mAccountInfoDaoManager.deleteAll();
                                mDeleteAccountInfoDaoManager.deleteAll();
                                mCompanySupplierInfoDaoManager.deleteAll();
                                mDeleteSupplierInfoDaoManager.deleteAll();
                                mProductInfoDaoManager.deleteAll();
                                mDeleteProductInfoDaoManager.deleteAll();
                                mClientInfoDaoManager.deleteAll();
                                mDeleteClientInfoDaoManager.deleteAll();
                                mContactInfoDaoManager.deleteAll();
                                mDeleteContactInfoDaoManager.deleteAll();
                                mTradingOrderInfoDaoManager.deleteAll();
                                mDeleteTradingOrderInfoDaoManager.deleteAll();
                                mQuotationInfoDaoManager.deleteAll();
                                mDeleteQuotationInfoDaoManager.deleteAll();
                                mSalesOrderInfoDaoManager.deleteAll();
                                mDeleteSalesOrderInfoDaoManager.deleteAll();
                                mDeliveryOrderInfoDaoManager.deleteAll();
                                mDeleteDeliveryOrderInfoDaoManager.deleteAll();
                                mPayOrderInfoDaoManager.deleteAll();
                                mDeletePayOrderInfoDaoManager.deleteAll();
                                mServiceRecordInfoDaoManager.deleteAll();
                                mDeleteServiceRecordInfoDaoManager.deleteAll();
                                mInventoryInfoDaoManager.deleteAll();
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
                                }
                            }, new ComConsumer(this)));
                }
            }
        }

        /**
         * 企业供应商模块
         */
        for (final CompanyDeleteSupplierInfo companyDeleteInfo : mCompanyDeleteSupplierInfoList) {
            addDisposable(mDataManager.deleteCompanySupplier(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeleteSupplierInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanySupplierInfo companyInfo : mCompanySupplierLocalList) {
            final String[] mHeadCode = {""};
            if (companyInfo.isLocal()) {
                if (companyInfo.getHead().contains(".jpg")) {
                    addDisposable(mDataManager.uploadImages(companyInfo.getHead())
                            .flatMap(new Function<FileInfoBean, ObservableSource<UploadInfoBean>>() {
                                @Override
                                public ObservableSource<UploadInfoBean> apply(FileInfoBean fileInfoBean) throws Exception {
                                    if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
                                        mHeadCode[0] = fileInfoBean.getCode();
                                        return mDataManager.saveCompanySupplier(companyInfo.getId(), companyInfo.getCompanyId(), mHeadCode[0], companyInfo.getName(), companyInfo.getType(), companyInfo.getAddress(), companyInfo.getPhone(), companyInfo.getEmail(), companyInfo.getZipCode(), companyInfo.getArea(), companyInfo.getRemark());
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
                                    mCompanySupplierInfoDaoManager.correct(companyInfo);
                                }
                            }, new ComConsumer(this)));
                } else {
                    addDisposable(mDataManager.saveCompanySupplier(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getHead(), companyInfo.getName(), companyInfo.getType(), companyInfo.getAddress(), companyInfo.getPhone(), companyInfo.getEmail(), companyInfo.getZipCode(), companyInfo.getArea(), companyInfo.getRemark())
                            .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                            .subscribe(new Consumer<UploadInfoBean>() {
                                @Override
                                public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                    companyInfo.setId(uploadInfoBean.getId());
                                    companyInfo.setIsLocal(false);
                                    companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                    mCompanySupplierInfoDaoManager.correct(companyInfo);
                                }
                            }, new ComConsumer(this)));
                }
            }
        }

        /**
         * 企业产品模块
         */
        for (final CompanyDeleteProductInfo companyDeleteInfo : mDeleteProductInfoList) {
            addDisposable(mDataManager.deleteCompanyProduct(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeleteProductInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanyProductInfo companyInfo : mProductInfoLocalList) {
            if (companyInfo.isLocal()) {
                addDisposable(mDataManager.saveCompanyProduct(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getName(), companyInfo.getCode(), companyInfo.getSupplierName(), companyInfo.getMakerName(), companyInfo.getType(), companyInfo.getUnitPrice(), companyInfo.getUnit(), companyInfo.getStockNum(), companyInfo.getWarnNum(), companyInfo.getRemark())
                        .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                        .subscribe(new Consumer<UploadInfoBean>() {
                            @Override
                            public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                companyInfo.setId(uploadInfoBean.getId());
                                companyInfo.setIsLocal(false);
                                companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                mProductInfoDaoManager.correct(companyInfo);
                            }
                        }, new ComConsumer(this)));
            }
        }

        /**
         * 企业用户模块
         */
        for (final CompanyDeleteClientInfo companyDeleteInfo : mCompanyDeleteClientLocalList) {
            addDisposable(mDataManager.deleteCompanyClient(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeleteClientInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanyClientInfo companyInfo : mCompanyClientLocalList) {
            final String[] mHeadCode = {""};
            if (companyInfo.isLocal()) {
                if (companyInfo.getHead().contains(".jpg")) {
                    addDisposable(mDataManager.uploadImages(companyInfo.getHead())
                            .flatMap(new Function<FileInfoBean, ObservableSource<UploadInfoBean>>() {
                                @Override
                                public ObservableSource<UploadInfoBean> apply(FileInfoBean fileInfoBean) throws Exception {
                                    if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
                                        mHeadCode[0] = fileInfoBean.getCode();
                                        return mDataManager.saveCompanyClient(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), mHeadCode[0], companyInfo.getName(), companyInfo.getIndustry(), companyInfo.getAddress(), companyInfo.getWorkerNum(), companyInfo.getTel(), companyInfo.getYearIncome(), companyInfo.getRemark());
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
                                    mClientInfoDaoManager.correct(companyInfo);
                                }
                            }, new ComConsumer(this)));
                } else {
                    addDisposable(mDataManager.saveCompanyClient(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), companyInfo.getHead(), companyInfo.getName(), companyInfo.getIndustry(), companyInfo.getAddress(), companyInfo.getWorkerNum(), companyInfo.getTel(), companyInfo.getYearIncome(), companyInfo.getRemark())
                            .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                            .subscribe(new Consumer<UploadInfoBean>() {
                                @Override
                                public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                    companyInfo.setId(uploadInfoBean.getId());
                                    companyInfo.setIsLocal(false);
                                    companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                    mClientInfoDaoManager.correct(companyInfo);
                                }
                            }, new ComConsumer(this)));
                }
            }
        }

        /**
         * 企业联系人模块
         */
        for (final CompanyDeleteContactInfo companyDeleteInfo : mCompanyDeleteContactLocalList) {
            addDisposable(mDataManager.deleteCompanyContact(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeleteContactInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanyContactInfo companyInfo : mCompanyContactLocalList) {
            final String[] mHeadCode = {""};
            if (companyInfo.isLocal()) {
                if (companyInfo.getHead().contains(".jpg")) {
                    addDisposable(mDataManager.uploadImages(companyInfo.getHead())
                            .flatMap(new Function<FileInfoBean, ObservableSource<UploadInfoBean>>() {
                                @Override
                                public ObservableSource<UploadInfoBean> apply(FileInfoBean fileInfoBean) throws Exception {
                                    if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
                                        mHeadCode[0] = fileInfoBean.getCode();
                                        return mDataManager.saveCompanyContact(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), mHeadCode[0], companyInfo.getName(), companyInfo.getFromType(), companyInfo.getAddress(), companyInfo.getMailAddress(), companyInfo.getPhone(), companyInfo.getTel(), companyInfo.getEmail(), companyInfo.getJob(), companyInfo.getRemark());
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
                                    mContactInfoDaoManager.correct(companyInfo);
                                }
                            }, new ComConsumer(this)));
                } else {
                    addDisposable(mDataManager.saveCompanyContact(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), mHeadCode[0], companyInfo.getName(), companyInfo.getFromType(), companyInfo.getAddress(), companyInfo.getMailAddress(), companyInfo.getPhone(), companyInfo.getTel(), companyInfo.getEmail(), companyInfo.getJob(), companyInfo.getRemark())
                            .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                            .subscribe(new Consumer<UploadInfoBean>() {
                                @Override
                                public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                    companyInfo.setId(uploadInfoBean.getId());
                                    companyInfo.setIsLocal(false);
                                    companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                    mContactInfoDaoManager.correct(companyInfo);
                                }
                            }, new ComConsumer(this)));
                }
            }
        }

        /**
         * 企业交易单模块
         */
        for (final CompanyDeleteTradingOrderInfo companyDeleteInfo : mCompanyDeleteTradingOrderLocalList) {
            addDisposable(mDataManager.deleteCompanyTradingOrder(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeleteTradingOrderInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanyTradingOrderInfo companyInfo : mTradingOrderLocalList) {
            if (companyInfo.isLocal()) {
                addDisposable(mDataManager.saveCompanyTradingOrder(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getCustomerName(), companyInfo.getPrice(), companyInfo.getEstimateProfit(), companyInfo.getContactName(), companyInfo.getPossibility(), companyInfo.getClueSource(), companyInfo.getRemark())
                        .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                        .subscribe(new Consumer<UploadInfoBean>() {
                            @Override
                            public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                companyInfo.setId(uploadInfoBean.getId());
                                companyInfo.setIsLocal(false);
                                companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                mTradingOrderInfoDaoManager.correct(companyInfo);
                            }
                        }, new ComConsumer(this)));
            }
        }

        /**
         * 企业报价单模块
         */
        for (final CompanyDeleteQuotationInfo companyDeleteInfo : mCompanyDeleteQuotationLocalList) {
            addDisposable(mDataManager.deleteCompanyQuotation(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeleteQuotationInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanyQuotationInfo companyInfo : mCompanyQuotationLocalList) {
            if (companyInfo.isLocal()) {
                addDisposable(mDataManager.saveCompanyQuotation(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getCustomerName(), companyInfo.getContactName(), companyInfo.getTermOfValidity(), companyInfo.getPrice(), companyInfo.getSendAddress(), companyInfo.getSendAddressZipCode(), companyInfo.getDestinationAddress(), companyInfo.getDestinationAddressZipCode(), companyInfo.getProducts(), companyInfo.getClause(), companyInfo.getRemark())
                        .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                        .subscribe(new Consumer<UploadInfoBean>() {
                            @Override
                            public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                companyInfo.setId(uploadInfoBean.getId());
                                companyInfo.setIsLocal(false);
                                companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                mQuotationInfoDaoManager.correct(companyInfo);
                            }
                        }, new ComConsumer(this)));
            }
        }

        /**
         * 企业销售单模块
         */
        for (final CompanyDeleteSalesOrderInfo companyDeleteInfo : mCompanyDeleteSalesOrderLocalList) {
            addDisposable(mDataManager.deleteCompanySalesOrder(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeleteSalesOrderInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanySalesOrderInfo companyInfo : mCompanySalesOrderLocalList) {
            if (companyInfo.isLocal()) {
                addDisposable(mDataManager.saveCompanySalesOrder(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getCustomerName(), companyInfo.getContactName(), companyInfo.getSaleCommission(), companyInfo.getState(), companyInfo.getSendAddress(), companyInfo.getSendAddressZipCode(), companyInfo.getDestinationAddress(), companyInfo.getDestinationAddressZipCode(), companyInfo.getProducts(), companyInfo.getClause(), companyInfo.getRemark())
                        .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                        .subscribe(new Consumer<UploadInfoBean>() {
                            @Override
                            public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                companyInfo.setId(uploadInfoBean.getId());
                                companyInfo.setIsLocal(false);
                                companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                mSalesOrderInfoDaoManager.correct(companyInfo);
                            }
                        }, new ComConsumer(this)));
            }
        }

        /**
         * 企业发货单模块
         */
        for (final CompanyDeleteDeliveryOrderInfo companyDeleteInfo : mDeleteDeliveryOrderLocalList) {
            addDisposable(mDataManager.deleteCompanyDeliveryOrder(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeleteDeliveryOrderInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanyDeliveryOrderInfo companyInfo : mDeliveryOrderLocalList) {
            if (companyInfo.isLocal()) {
                addDisposable(mDataManager.saveCompanyDeliveryOrder(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getSalesOrderId(), companyInfo.getLogisticsCode(), companyInfo.getState(), companyInfo.getArriveDate(), companyInfo.getSendAddress(), companyInfo.getSendAddressZipCode(), companyInfo.getDestinationAddress(), companyInfo.getDestinationAddressZipCode(), companyInfo.getProducts(), companyInfo.getClause(), companyInfo.getRemark())
                        .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                        .subscribe(new Consumer<UploadInfoBean>() {
                            @Override
                            public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                companyInfo.setId(uploadInfoBean.getId());
                                companyInfo.setIsLocal(false);
                                companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                mDeliveryOrderInfoDaoManager.correct(companyInfo);
                            }
                        }, new ComConsumer(this)));
            }
        }

        /**
         * 企业付款单模块
         */
        for (final CompanyDeletePayOrderInfo companyDeleteInfo : mDeletePayOrderLocalList) {
            addDisposable(mDataManager.deleteCompanyPayOrder(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeletePayOrderInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanyPayOrderInfo companyInfo : mPayOrderLocalList) {
            final String[] mHeadCode = {"", ""};
            if (companyInfo.isLocal()) {
                final String[] localPath = companyInfo.getInvoiceFiles().split(",");
                if (companyInfo.getInvoiceFiles().contains(".jpg")) {
                    addDisposable(mDataManager.uploadImages(localPath[0])
                            .flatMap(new Function<FileInfoBean, ObservableSource<FileInfoBean>>() {
                                @Override
                                public ObservableSource<FileInfoBean> apply(FileInfoBean fileInfoBean) throws Exception {
                                    if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
                                        mHeadCode[0] = fileInfoBean.getCode();
                                        return mDataManager.uploadImages(localPath[1]);
                                    } else {
                                        return Observable.error(new ComException("上传失败，请重试"));
                                    }
                                }
                            })
                            .flatMap(new Function<FileInfoBean, ObservableSource<UploadInfoBean>>() {
                                @Override
                                public ObservableSource<UploadInfoBean> apply(FileInfoBean fileInfoBean) throws Exception {
                                    if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
                                        mHeadCode[1] = fileInfoBean.getCode();
                                        return mDataManager.saveCompanyPayOrder(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getSalesOrderId(), companyInfo.getTradingOrderId(), companyInfo.getCustomerName(), companyInfo.getPrice(), companyInfo.getPayTime(), companyInfo.getHasInvoice(), companyInfo.getHasPrint(), mHeadCode[0] + "," + mHeadCode[1], companyInfo.getRemark());
                                    } else {
                                        return Observable.error(new ComException("上传失败，请重试"));
                                    }
                                }
                            })
                            .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                            .subscribe(new Consumer<UploadInfoBean>() {
                                @Override
                                public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                    companyInfo.setId(uploadInfoBean.getId());
                                    companyInfo.setInvoiceFiles(mHeadCode[0] + "," + mHeadCode[1]);
                                    companyInfo.setIsLocal(false);
                                    companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                    mPayOrderInfoDaoManager.correct(companyInfo);
                                }
                            }, new ComConsumer(this)));
                } else {
                    addDisposable(mDataManager.saveCompanyPayOrder(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getSalesOrderId(), companyInfo.getTradingOrderId(), companyInfo.getCustomerName(), companyInfo.getPrice(), companyInfo.getPayTime(), companyInfo.getHasInvoice(), companyInfo.getHasPrint(), companyInfo.getInvoiceFiles(), companyInfo.getRemark())
                            .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                            .subscribe(new Consumer<UploadInfoBean>() {
                                @Override
                                public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                    companyInfo.setId(uploadInfoBean.getId());
                                    companyInfo.setIsLocal(false);
                                    companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                    mPayOrderInfoDaoManager.correct(companyInfo);
                                }
                            }, new ComConsumer(this)));
                }
            }
        }

        /**
         * 企业服务记录模块
         */
        for (final CompanyDeleteServiceRecordInfo companyDeleteInfo : mDeleteServiceRecordLocalList) {
            addDisposable(mDataManager.deleteServiceRecord(companyDeleteInfo.getId())
                    .compose(RxUtils.<BaseEntity>ioToMain(this))
                    .subscribe(new Consumer<BaseEntity>() {
                        @Override
                        public void accept(BaseEntity baseEntity) throws Exception {
                            mDeleteServiceRecordInfoDaoManager.delete(companyDeleteInfo.getLocalId());
                        }
                    }, new ComConsumer(this)));
        }
        for (final CompanyServiceRecordInfo companyInfo : mServiceRecordLocalList) {
            if (companyInfo.isLocal()) {
                addDisposable(mDataManager.saveCompanyServiceRecord(companyInfo.getId(), companyInfo.getCompanyId(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getState(), companyInfo.getType(), companyInfo.getProductId(), companyInfo.getCustomerName(), companyInfo.getLevel(), companyInfo.getPhone(), companyInfo.getEmail(), companyInfo.getReason(), companyInfo.getRemark(), companyInfo.getSolution(), companyInfo.getTrack())
                        .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                        .subscribe(new Consumer<UploadInfoBean>() {
                            @Override
                            public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                companyInfo.setId(uploadInfoBean.getId());
                                companyInfo.setIsLocal(false);
                                companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                mServiceRecordInfoDaoManager.correct(companyInfo);
                            }
                        }, new ComConsumer(this)));
            }
        }

        /**
         * 企业库存模块
         */
        for (final CompanyInventoryInfo companyInfo : mInventoryLocalList) {
            if (companyInfo.isLocal()) {
                addDisposable(mDataManager.saveInventory(companyInfo.getCompanyId(), companyInfo.getProductId(), companyInfo.getType(), companyInfo.getNum(), companyInfo.getRemark())
                        .compose(RxUtils.<UploadInfoBean>ioToMain(this))
                        .subscribe(new Consumer<UploadInfoBean>() {
                            @Override
                            public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                                companyInfo.setId(uploadInfoBean.getId());
                                companyInfo.setIsLocal(false);
                                companyInfo.setCreateTime(uploadInfoBean.getCreateTime());
                                mInventoryInfoDaoManager.correct(companyInfo);
                            }
                        }, new ComConsumer(this)));
            }
        }
        toastMsg("数据上传成功");
    }
}
