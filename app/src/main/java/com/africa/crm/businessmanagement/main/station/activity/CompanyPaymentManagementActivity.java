package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPayOrderEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeletePayOrderInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyDeletePayOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyPayOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.PaymentListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPayOrderContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPayOrderPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseRefreshMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_USERS;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_PAY_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_PAY_ORDER_LIST;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPaymentManagementActivity extends BaseRefreshMvpActivity<CompanyPayOrderPresenter> implements CompanyPayOrderContract.View {
    @BindView(R.id.et_pay_order_name)
    EditText et_pay_order_name;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_delete)
    TextView tv_delete;

    @BindView(R.id.spinner_user)
    MySpinner spinner_user;
    private List<DicInfo> mSpinnerCompanyUserList = new ArrayList<>();
    private String mFromUserId = "";
    private String mUserId = "";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PaymentListAdapter mPaymentListAdapter;
    private List<CompanyPayOrderInfo> mDeleteList = new ArrayList<>();
    private List<CompanyPayOrderInfo> mPaymentInfoBeanList = new ArrayList<>();

    private WorkStationInfo mWorkStationInfo;
    private boolean mShowCheckBox = false;
    private AlertDialog mDeleteDialog;
    private String mCompanyId = "";
    private String mRoleCode = "";

    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private GreendaoManager<CompanyPayOrderInfo, CompanyPayOrderInfoDao> mPayOrderInfoDaoManager;
    private GreendaoManager<CompanyDeletePayOrderInfo, CompanyDeletePayOrderInfoDao> mDeletePayOrderInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanyPayOrderInfo> mPayOrderLocalist = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyPaymentManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_payment_management);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        if (mRoleCode.equals("companyRoot")) {
            titlebar_right.setVisibility(View.GONE);
            spinner_user.setVisibility(View.VISIBLE);
        } else {
            titlebar_right.setVisibility(View.VISIBLE);
            spinner_user.setVisibility(View.INVISIBLE);
        }
        if (mRoleCode.equals("companySales")) {
            ll_add.setVisibility(View.VISIBLE);
        } else {
            ll_add.setVisibility(View.GONE);
        }
        ll_add.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        initTimePicker();
        //得到Dao对象管理器
        mPayOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyPayOrderInfoDao());
        //得到Dao对象管理器
        mDeletePayOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeletePayOrderInfoDao());
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
    }

    private void initTimePicker() {
        pvStartTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mEndDate != null) {
                    if (mEndDate.getTime() < date.getTime()) {
                        toastMsg("起止时间不得小于起始时间");
                        return;
                    }
                }
                mStartDate = date;
                tv_start_time.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));

        pvEndTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mStartDate != null) {
                    if (date.getTime() < mStartDate.getTime()) {
                        toastMsg("起止时间不得小于起始时间");
                        return;
                    }
                }
                mEndDate = date;
                tv_end_time.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
    }


    @Override
    protected CompanyPayOrderPresenter injectPresenter() {
        return new CompanyPayOrderPresenter();
    }

    @Override
    protected void requestData() {
        if ("companyRoot".equals(mRoleCode)) {
            mPresenter.getAllCompanyUsers(mCompanyId);
        }
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        if (mRoleCode.equals("companySales")) {
            mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        } else {
            mUserId = mFromUserId;
        }
        mPresenter.getCompanyPayOrderList(page, rows, mCompanyId, mUserId, et_pay_order_name.getText().toString().trim(), et_code.getText().toString().trim(), tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_start_time:
                if (pvStartTime != null)
                    pvStartTime.show();
                break;
            case R.id.tv_end_time:
                if (pvEndTime != null)
                    pvEndTime.show();
                break;
            case R.id.tv_search:
                page = 1;
                pullDownRefresh(page);
                break;
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.delete))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_delete.setVisibility(View.VISIBLE);
                    mShowCheckBox = true;
                } else {
                    titlebar_right.setText(R.string.delete);
                    tv_delete.setVisibility(View.GONE);
                    mShowCheckBox = false;
                }
                if (mPaymentListAdapter != null) {
                    mPaymentListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyPaymentDetailActivity.startActivity(CompanyPaymentManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyPayOrderInfo companyPayOrderInfo : mPaymentInfoBeanList) {
                    if (companyPayOrderInfo.isChosen()) {
                        mDeleteList.add(companyPayOrderInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyPaymentManagementActivity.this)
                        .setTitle(R.string.tips)
                        .setMessage(R.string.confirm_delete)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    mPresenter.deleteCompanyPayOrder(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public void initData() {
        mPaymentListAdapter = new PaymentListAdapter(mPaymentInfoBeanList);
        recyclerView.setAdapter(mPaymentListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
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
            spinner_user.setListDatas(getBVActivity(), mSpinnerCompanyUserList);
            spinner_user.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mFromUserId = dicInfo.getCode();
                }
            });
        }
    }

    @Override
    public void getCompanyPayOrderList(CompanyPayOrderInfoBean companyPayOrderInfoBean) {
        if (companyPayOrderInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyPayOrderInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mPaymentInfoBeanList.clear();
                mPayOrderLocalist = mPayOrderInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyPayOrderInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyPayOrderInfoBean.getRows())) {
                mPaymentInfoBeanList.addAll(companyPayOrderInfoBean.getRows());
                List<CompanyPayOrderInfo> addList = DifferentDataUtil.addPayOrderDataToLocal(mPaymentInfoBeanList, mPayOrderLocalist);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyPayOrderInfo companyInfo : addList) {
                        mPayOrderInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mPayOrderLocalist = new ArrayList<>();
                    mPayOrderLocalist = mPayOrderInfoDaoManager.queryAll();
                }
                for (CompanyPayOrderInfo info : mPaymentInfoBeanList) {
                    for (CompanyPayOrderInfo localInfo : mPayOrderLocalist) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mPayOrderInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mPaymentListAdapter != null) {
                    mPaymentListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mPaymentInfoBeanList)) {
                mPaymentListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mPaymentInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                            mPaymentListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyPaymentDetailActivity.startActivity(CompanyPaymentManagementActivity.this, mPaymentInfoBeanList.get(position).getId(), mPaymentInfoBeanList.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteCompanyPayOrder(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mPaymentInfoBeanList.contains(mDeleteList.get(i))) {
                    int position = mPaymentInfoBeanList.indexOf(mDeleteList.get(i));
                    mPaymentInfoBeanList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyPayOrderInfo companyInfo : mDeleteList) {
                            CompanyDeletePayOrderInfo deletePayOrderInfo = new CompanyDeletePayOrderInfo(companyInfo.getHasPrint(), companyInfo.getCustomerName(), companyInfo.getCreateTime(), companyInfo.getCreateTimeDate(), companyInfo.getHasInvoice(), companyInfo.getRemark(), companyInfo.getHasInvoiceName(), companyInfo.getHasPrintName(), companyInfo.getSalesOrderId(), companyInfo.getCode(), companyInfo.getCompanyName(), companyInfo.getPayTime(), companyInfo.getUserNickName(), companyInfo.getId(), companyInfo.getPrice(), companyInfo.getEditAble(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getSalesOrderName(), companyInfo.getInvoiceFiles(), companyInfo.getCompanyId(), companyInfo.getTradingOrderId(), companyInfo.getTradingOrderName(), false, isLocal);
                            mDeletePayOrderInfoDaoManager.insertOrReplace(deletePayOrderInfo);
                        }
                    }
                    for (CompanyPayOrderInfo companyInfo : mPayOrderLocalist) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mPayOrderInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mPayOrderLocalist = new ArrayList<>();
                    mPayOrderLocalist = mPayOrderInfoDaoManager.queryAll();
                    if (mPaymentListAdapter != null) {
                        mPaymentListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mPaymentInfoBeanList)) {
                titlebar_right.setText(R.string.delete);
                tv_delete.setVisibility(View.GONE);
                mShowCheckBox = false;
                layout_network_error.setVisibility(View.GONE);
                mRefreshLayout.getLayout().setVisibility(View.GONE);
                layout_no_data.setVisibility(View.VISIBLE);
            }
            mDeleteDialog.dismiss();
        } else {
            ErrorMsg.showErrorMsg(baseEntity.getReturnMsg());
        }
    }

    @Subscribe
    public void Event(AddOrSaveCompanyPayOrderEvent addOrSaveCompanyPayOrderEvent) {
        toastMsg(addOrSaveCompanyPayOrderEvent.getMsg());
        page = 1;
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        mRefreshLayout.setEnableLoadmore(false);
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
            case REQUEST_PAY_ORDER_LIST:
                List<CompanyPayOrderInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(StringUtil.getText(et_pay_order_name)) || !TextUtils.isEmpty(StringUtil.getText(et_code)) || !TextUtils.isEmpty(mFromUserId) || !TextUtils.isEmpty(StringUtil.getText(tv_start_time)) || !TextUtils.isEmpty(StringUtil.getText(tv_end_time))) {
                    if (!TextUtils.isEmpty(mFromUserId)) {
                        rows = mPayOrderInfoDaoManager.queryBuilder().where(CompanyPayOrderInfoDao.Properties.Name.like("%" + StringUtil.getText(et_pay_order_name) + "%"), CompanyPayOrderInfoDao.Properties.Code.like("%" + StringUtil.getText(et_code) + "%"), CompanyPayOrderInfoDao.Properties.UserId.eq(mFromUserId), CompanyPayOrderInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyPayOrderInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    } else {
                        rows = mPayOrderInfoDaoManager.queryBuilder().where(CompanyPayOrderInfoDao.Properties.Name.like("%" + StringUtil.getText(et_pay_order_name) + "%"), CompanyPayOrderInfoDao.Properties.Code.like("%" + StringUtil.getText(et_code) + "%"), CompanyPayOrderInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyPayOrderInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    }
                } else {
                    rows = mPayOrderInfoDaoManager.queryAll();
                }
                CompanyPayOrderInfoBean companyPayOrderInfoBean = new CompanyPayOrderInfoBean();
                companyPayOrderInfoBean.setRows(rows);
                getCompanyPayOrderList(companyPayOrderInfoBean);
                break;
            case REQUEST_DELETE_PAY_ORDER:
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setSuccess(true);
                deleteCompanyPayOrder(baseEntity, true);
                break;
        }
    }
}
