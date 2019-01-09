package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyTradingOrderEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyTradingOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTradingOrderDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyTradingOrderDetailPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_CONTACTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_CUSTOMERS;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CONTACT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CUSTOMER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_TRADING_ORDER_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_TRADING_ORDER;

public class CompanyTradingOrderDetailActivity extends BaseMvpActivity<CompanyTradingOrderDetailPresenter> implements CompanyTradingOrderDetailContract.View {
    @BindView(R.id.et_order_name)
    EditText et_order_name;
    @BindView(R.id.et_money)
    EditText et_money;
    @BindView(R.id.et_income)
    EditText et_income;
    @BindView(R.id.et_thread_from)
    EditText et_thread_from;
    @BindView(R.id.et_possibility)
    EditText et_possibility;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private String mTradingOrderId = "";
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mCompanyName = "";
    private String mUserId = "";

    @BindView(R.id.spinner_customer_name)
    MySpinner spinner_customer_name;
    private String mFromName = "";

    @BindView(R.id.spinner_contact_name)
    MySpinner spinner_contact_name;
    private String mContactName = "";

    private String mEditAble = "1";

    private GreendaoManager<CompanyTradingOrderInfo, CompanyTradingOrderInfoDao> mTradingOrderInfoDaoManager;
    private List<CompanyTradingOrderInfo> mTradingOrderLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanyTradingOrderDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_trading_order_detail);
    }

    @Override
    public void initView() {
        super.initView();
        mTradingOrderId = getIntent().getStringExtra("id");
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("交易单详情");
        tv_save.setOnClickListener(this);

        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (TextUtils.isEmpty(mTradingOrderId) && mLocalId == 0l) {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setText(R.string.add);
                tv_save.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(mTradingOrderId) || mLocalId != 0l) {
                titlebar_right.setText(R.string.edit);
                tv_save.setText(R.string.save);
                setEditTextInput(false);
            }
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        }
        //得到Dao对象管理器
        mTradingOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyTradingOrderInfoDao());
        //得到本地数据
        mTradingOrderLocalList = mTradingOrderInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
    }

    @Override
    protected CompanyTradingOrderDetailPresenter injectPresenter() {
        return new CompanyTradingOrderDetailPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getAllContact(mCompanyId);
        mPresenter.getAllCustomers(mCompanyId);
        if (!TextUtils.isEmpty(mTradingOrderId) || mLocalId != 0l) {
            mPresenter.getCompanyTradingOrderDetail(mTradingOrderId);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
                if (TextUtils.isEmpty(et_order_name.getText().toString().trim())) {
                    toastMsg("尚未填写交易单名称");
                    return;
                }
                mPresenter.saveCompanyTradingOrder(mTradingOrderId, mCompanyId, mUserId, et_order_name.getText().toString().trim(), spinner_customer_name.getText(), et_money.getText().toString().trim(), et_income.getText().toString().trim(), spinner_contact_name.getText(), et_possibility.getText().toString().trim(), et_thread_from.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_order_name.setEnabled(canInput);
        spinner_customer_name.getTextView().setEnabled(canInput);
        spinner_contact_name.getTextView().setEnabled(canInput);
        et_money.setEnabled(canInput);
        et_income.setEnabled(canInput);
        et_thread_from.setEnabled(canInput);
        et_possibility.setEnabled(canInput);
        et_remark.setEnabled(canInput);

    }

    @Override
    public void getAllContact(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_CONTACTS, dicInfo2.getName(), dicInfo2.getId()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(QUERY_ALL_CONTACTS);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_contact_name.setListDatas(this, list);
        spinner_contact_name.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mContactName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getAllCustomers(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_CUSTOMERS, dicInfo2.getName(), dicInfo2.getId()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(QUERY_ALL_CUSTOMERS);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_customer_name.setListDatas(this, list);
        spinner_customer_name.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mFromName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getCompanyTradingOrderDetail(CompanyTradingOrderInfo companyTradingOrderInfo) {
        if (companyTradingOrderInfo != null) {
            et_order_name.setText(companyTradingOrderInfo.getName());
            spinner_customer_name.getTextView().setText(companyTradingOrderInfo.getCustomerName());
            spinner_contact_name.getTextView().setText(companyTradingOrderInfo.getContactName());
            et_money.setText(companyTradingOrderInfo.getPrice());
            et_income.setText(companyTradingOrderInfo.getEstimateProfit());
            et_thread_from.setText(companyTradingOrderInfo.getClueSource());
            et_possibility.setText(companyTradingOrderInfo.getPossibility());
            et_remark.setText(companyTradingOrderInfo.getRemark());
            mCompanyName = companyTradingOrderInfo.getCompanyName();
            mFromName = companyTradingOrderInfo.getUserNickName();
            mEditAble = companyTradingOrderInfo.getEditAble();
            mContactName = companyTradingOrderInfo.getContactName();
            for (CompanyTradingOrderInfo localInfo : mTradingOrderLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyTradingOrderInfo.getId())) {
                    if (localInfo.getId().equals(companyTradingOrderInfo.getId())) {
                        companyTradingOrderInfo.setLocalId(localInfo.getLocalId());
                        mTradingOrderInfoDaoManager.correct(companyTradingOrderInfo);
                    }
                }
            }
        }

    }

    @Override
    public void saveCompanyTradingOrder(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mTradingOrderId) && mLocalId == 0l) {
            toastString = "交易单创建成功";
        } else {
            toastString = "交易单修改成功";
        }
        if (isLocal) {
            CompanyTradingOrderInfo companyTradingOrderInfo = null;
            if (mLocalId == 0l) {
                companyTradingOrderInfo = new CompanyTradingOrderInfo(spinner_customer_name.getText(), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), TimeUtils.getCurrentTime(new Date()), StringUtil.getText(et_remark), StringUtil.getText(et_thread_from), StringUtil.getText(et_possibility), mCompanyName, mFromName, mTradingOrderId, StringUtil.getText(et_income), StringUtil.getText(et_money), mEditAble, mContactName, mUserId, StringUtil.getText(et_order_name), mCompanyId, false, isLocal);
                mTradingOrderInfoDaoManager.insertOrReplace(companyTradingOrderInfo);
            } else {
                for (CompanyTradingOrderInfo info : mTradingOrderLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyTradingOrderInfo = new CompanyTradingOrderInfo(info.getLocalId(), spinner_customer_name.getText(), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), TimeUtils.getCurrentTime(new Date()), StringUtil.getText(et_remark), StringUtil.getText(et_thread_from), StringUtil.getText(et_possibility), mCompanyName, mFromName, mTradingOrderId, StringUtil.getText(et_income), StringUtil.getText(et_money), mEditAble, mContactName, mUserId, StringUtil.getText(et_order_name), mCompanyId, false, isLocal);
                        mTradingOrderInfoDaoManager.correct(companyTradingOrderInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyTradingOrderEvent(toastString));
        finish();
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_ALL_CONTACT_LIST:
                List<DicInfo2> allContactList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_CONTACTS)) {
                        allContactList.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllContact(allContactList);
                break;
            case REQUEST_ALL_CUSTOMER_LIST:
                List<DicInfo2> allCustomers = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_CUSTOMERS)) {
                        allCustomers.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllCustomers(allCustomers);
                break;
            case REQUEST_COMPANY_TRADING_ORDER_DETAIL:
                CompanyTradingOrderInfo companyTradingOrderInfo = null;
                for (CompanyTradingOrderInfo info : mTradingOrderLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyTradingOrderInfo = info;
                    }
                }
                getCompanyTradingOrderDetail(companyTradingOrderInfo);
                break;
            case REQUEST_SAVE_COMPANY_TRADING_ORDER:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mTradingOrderId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyTradingOrder(uploadInfoBean, true);
                break;
        }
    }
}
