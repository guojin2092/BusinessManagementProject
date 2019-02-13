package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.africa.crm.businessmanagement.widget.EditTextUtil;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private String mFromName = "";

    @BindView(R.id.spinner_customer_name)
    MySpinner spinner_customer_name;
    private String mCustomerName = "";

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
        mFromName = UserInfoManager.getUserLoginInfo(this).getName();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText(R.string.Trading_Order_Details);
        tv_save.setOnClickListener(this);
        EditTextUtil.setPricePoint(et_money);
        EditTextUtil.setPricePoint(et_income);
        et_possibility.addTextChangedListener(inputWatch(et_possibility));

        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (TextUtils.isEmpty(mTradingOrderId) && mLocalId == 0l) {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setText(R.string.Add);
                tv_save.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(mTradingOrderId) || mLocalId != 0l) {
                titlebar_right.setText(R.string.Edit);
                tv_save.setText(R.string.Save);
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
                if (TextUtils.isEmpty(et_order_name.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_fill_in_the_name));
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
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_CONTACTS, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
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
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_CUSTOMERS, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_customer_name.setListDatas(this, list);
        spinner_customer_name.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mCustomerName = dicInfo.getText();
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
            mCustomerName = companyTradingOrderInfo.getCustomerName();
            mContactName = companyTradingOrderInfo.getContactName();
            if (mEditAble.equals("2")) {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setVisibility(View.GONE);
                setEditTextInput(false);
            }
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
            toastString = getString(R.string.Added_Successfully);
        } else {
            toastString = getString(R.string.Successfully_Modified);
        }
        if (isLocal) {
            CompanyTradingOrderInfo companyTradingOrderInfo = null;
            if (mLocalId == 0l) {
                companyTradingOrderInfo = new CompanyTradingOrderInfo(mCustomerName, TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), TimeUtils.getCurrentTime(new Date()), StringUtil.getText(et_remark), StringUtil.getText(et_thread_from), StringUtil.getText(et_possibility), mCompanyName, mFromName, mTradingOrderId, StringUtil.getText(et_income), StringUtil.getText(et_money), mEditAble, mContactName, mUserId, StringUtil.getText(et_order_name), mCompanyId, false, isLocal);
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

    /**
     * 百分比校验
     *
     * @param input
     * @return
     */
    public TextWatcher inputWatch(final EditText input) {
        return new TextWatcher() {

            private String outStr = ""; //这个值存储输入超过两位数时候显示的内容

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String edit = s.toString();
                if (edit.length() == 2 && Integer.parseInt(edit) >= 10) {
                    outStr = edit;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String words = s.toString();
                //首先内容进行非空判断，空内容（""和null）不处理
                if (!TextUtils.isEmpty(words)) {
                    //1-100的正则验证
                    Pattern p = Pattern.compile("^(100|[1-9]\\d|\\d)$");
                    Matcher m = p.matcher(words);
                    if (m.find() || ("").equals(words)) {
                        //这个时候输入的是合法范围内的值
                    } else {
                        if (words.length() > 2) {
                            //若输入不合规，且长度超过2位，继续输入只显示之前存储的outStr
                            input.setText(outStr);
                            //重置输入框内容后默认光标位置会回到索引0的地方，要改变光标位置
                            input.setSelection(2);
                        }
                        toastMsg(getString(R.string.Please_enter_an_integer_ranging_from_1_100));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //这里的处理是不让输入0开头的值
                String words = s.toString();
                //首先内容进行非空判断，空内容（""和null）不处理
                if (!TextUtils.isEmpty(words)) {
                    if (Integer.parseInt(s.toString()) <= 0) {
                        input.setText("");
                        toastMsg(getString(R.string.Please_enter_an_integer_ranging_from_1_100));
                    }
                }
            }
        };
    }

}
