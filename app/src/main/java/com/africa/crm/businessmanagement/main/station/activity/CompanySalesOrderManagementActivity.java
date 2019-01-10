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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanySalesOrderEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteSalesOrderInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteSalesOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanySalesOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.SalesListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanySalesOrderContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanySalesOrderPresenter;
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
import static com.africa.crm.businessmanagement.network.api.DicUtil.SALE_ORDER_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_SALE_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SALE_ORDER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SALE_ORDER_STATE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanySalesOrderManagementActivity extends BaseRefreshMvpActivity<CompanySalesOrderPresenter> implements CompanySalesOrderContract.View {
    @BindView(R.id.et_sales_name)
    EditText et_sales_name;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_search)
    TextView tv_search;

    @BindView(R.id.spinner_user)
    MySpinner spinner_user;
    private List<DicInfo> mSpinnerCompanyUserList = new ArrayList<>();
    private String mFromUserId = "";
    private String mUserId = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private static final String STATE_CODE = "SALEORDERSTATE";
    private List<DicInfo> mSalesOrderStateList = new ArrayList<>();
    private String mStateCode = "";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SalesListAdapter mSalesListAdapter;
    private List<CompanySalesOrderInfo> mDeleteList = new ArrayList<>();
    private List<CompanySalesOrderInfo> mCompanySalesOrderInfoList = new ArrayList<>();

    private AlertDialog mDeleteDialog;
    private boolean mShowCheckBox = false;
    private WorkStationInfo mWorkStationInfo;
    private String mRoleCode = "";
    private String mCompanyId = "";

    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private GreendaoManager<CompanySalesOrderInfo, CompanySalesOrderInfoDao> mSalesOrderInfoDaoManager;
    private GreendaoManager<CompanyDeleteSalesOrderInfo, CompanyDeleteSalesOrderInfoDao> mDeleteSalesOrderInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanySalesOrderInfo> mCompanySalesOrderLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanySalesOrderManagementActivity.class);
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
        setContentView(R.layout.activity_company_sales_order_management);
    }

    @Override
    public void initView() {
        super.initView();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);

        if (mRoleCode.equals("companyRoot")) {
            spinner_user.setVisibility(View.VISIBLE);
            titlebar_right.setVisibility(View.GONE);
        } else {
            spinner_user.setVisibility(View.GONE);
            titlebar_right.setVisibility(View.VISIBLE);
        }
        if (mRoleCode.equals("companySales")) {
            ll_add.setVisibility(View.VISIBLE);
        } else {
            ll_add.setVisibility(View.GONE);
        }

        //得到Dao对象管理器
        mSalesOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanySalesOrderInfoDao());
        //得到Dao对象管理器
        mDeleteSalesOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteSalesOrderInfoDao());
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
/*
        et_sales_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    spinner_user.setText("");
                    mUserId = "";
                    spinner_state.setText("");
                    mStateCode = "";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
        initTimePicker();
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
    protected CompanySalesOrderPresenter injectPresenter() {
        return new CompanySalesOrderPresenter();
    }

    @Override
    protected void requestData() {
        if ("companyRoot".equals(mRoleCode)) {
            mPresenter.getAllCompanyUsers(mCompanyId);
        }
        mPresenter.getStateType(STATE_CODE);
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        if (mRoleCode.equals("companySales")) {
            mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        } else {
            mUserId = mFromUserId;
        }
        mPresenter.getCompanySalesOrderList(page, rows, mCompanyId, mUserId, et_sales_name.getText().toString().trim(), mStateCode, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
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
                if (mSalesListAdapter != null) {
                    mSalesListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanySaleOrdersDetailActivity.startActivity(CompanySalesOrderManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanySalesOrderInfo companySalesOrderInfo : mCompanySalesOrderInfoList) {
                    if (companySalesOrderInfo.isChosen()) {
                        mDeleteList.add(companySalesOrderInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanySalesOrderManagementActivity.this)
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
                                    mPresenter.deleteCompanySalesOrder(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public void initData() {
        mSalesListAdapter = new SalesListAdapter(mCompanySalesOrderInfoList);
        recyclerView.setAdapter(mSalesListAdapter);
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
    public void getStateType(List<DicInfo> dicInfoList) {
        mSalesOrderStateList.clear();
        mSalesOrderStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSalesOrderStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(SALE_ORDER_STATE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_state.setListDatas(this, mSalesOrderStateList);
        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
/*
                if (!TextUtils.isEmpty(mStateCode)) {
                    et_sales_name.setText("");
                    spinner_user.setText("");
                    mUserId = "";
                }
*/
            }
        });
    }

    @Override
    public void getCompanySalesOrderList(CompanySalesOrderInfoBean companySalesOrderInfoBean) {
        if (companySalesOrderInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companySalesOrderInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                  /*  KeyboardUtil.clearInputBox(et_sales_name);
                    KeyboardUtil.clearInputBox(tv_start_time);
                    KeyboardUtil.clearInputBox(tv_end_time);
                    mStartDate = null;
                    mEndDate = null;
                    spinner_user.setText("");
                    mUserId = "";
                    spinner_state.setText("");
                    mStateCode = "";*/
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mCompanySalesOrderInfoList.clear();
                mCompanySalesOrderLocalList = mSalesOrderInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companySalesOrderInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companySalesOrderInfoBean.getRows())) {
                mCompanySalesOrderInfoList.addAll(companySalesOrderInfoBean.getRows());
                List<CompanySalesOrderInfo> addList = DifferentDataUtil.addSaleOrderDataToLocal(mCompanySalesOrderInfoList, mCompanySalesOrderLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanySalesOrderInfo companyInfo : addList) {
                        mSalesOrderInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mCompanySalesOrderLocalList = new ArrayList<>();
                    mCompanySalesOrderLocalList = mSalesOrderInfoDaoManager.queryAll();
                }
                for (CompanySalesOrderInfo info : mCompanySalesOrderInfoList) {
                    for (CompanySalesOrderInfo localInfo : mCompanySalesOrderLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mSalesOrderInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mSalesListAdapter != null) {
                    mSalesListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mCompanySalesOrderInfoList)) {
                mSalesListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mCompanySalesOrderInfoList.get(position).setChosen(!cb_choose.isChecked());
                            mSalesListAdapter.notifyDataSetChanged();
                        } else {
                            CompanySaleOrdersDetailActivity.startActivity(CompanySalesOrderManagementActivity.this, mCompanySalesOrderInfoList.get(position).getId(), mCompanySalesOrderInfoList.get(position).getLocalId());
                        }
                    }
                });
            }
        }
    }

    @Override
    public void deleteCompanySalesOrder(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mCompanySalesOrderInfoList.contains(mDeleteList.get(i))) {
                    int position = mCompanySalesOrderInfoList.indexOf(mDeleteList.get(i));
                    mCompanySalesOrderInfoList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanySalesOrderInfo companyInfo : mDeleteList) {
                            CompanyDeleteSalesOrderInfo deleteSalesOrderInfo = new CompanyDeleteSalesOrderInfo(companyInfo.getCustomerName(), companyInfo.getCreateTime(), companyInfo.getCreateTimeDate(), companyInfo.getSendAddress(), companyInfo.getRemark(), companyInfo.getState(), companyInfo.getCompanyName(), companyInfo.getUserNickName(), companyInfo.getId(), companyInfo.getEditAble(), companyInfo.getDestinationAddress(), companyInfo.getContactName(), companyInfo.getSendAddressZipCode(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getStateName(), companyInfo.getSaleCommission(), companyInfo.getCompanyId(), companyInfo.getProducts(), companyInfo.getClause(), companyInfo.getDestinationAddressZipCode(), false, isLocal);
                            mDeleteSalesOrderInfoDaoManager.insertOrReplace(deleteSalesOrderInfo);
                        }
                    }
                    for (CompanySalesOrderInfo companyInfo : mCompanySalesOrderLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mSalesOrderInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mCompanySalesOrderLocalList = new ArrayList<>();
                    mCompanySalesOrderLocalList = mSalesOrderInfoDaoManager.queryAll();
                    if (mSalesListAdapter != null) {
                        mSalesListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mCompanySalesOrderInfoList)) {
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
    public void Event(AddOrSaveCompanySalesOrderEvent addOrSaveCompanySalesOrderEvent) {
        toastMsg(addOrSaveCompanySalesOrderEvent.getMsg());
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
            case REQUEST_SALE_ORDER_STATE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(SALE_ORDER_STATE)) {
                        stateList.add(dicInfo);
                    }
                }
                getStateType(stateList);
                break;
            case REQUEST_SALE_ORDER_LIST:
                List<CompanySalesOrderInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(StringUtil.getText(et_sales_name)) || !TextUtils.isEmpty(mFromUserId) || !TextUtils.isEmpty(mStateCode) || !TextUtils.isEmpty(StringUtil.getText(tv_start_time)) || !TextUtils.isEmpty(StringUtil.getText(tv_end_time))) {
                    if (!TextUtils.isEmpty(mFromUserId) && TextUtils.isEmpty(mStateCode)) {
                        rows = mSalesOrderInfoDaoManager.queryBuilder().where(CompanySalesOrderInfoDao.Properties.Name.like("%" + StringUtil.getText(et_sales_name) + "%"), CompanySalesOrderInfoDao.Properties.UserId.eq(mFromUserId), CompanySalesOrderInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanySalesOrderInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    } else if (TextUtils.isEmpty(mFromUserId) && !TextUtils.isEmpty(mStateCode)) {
                        rows = mSalesOrderInfoDaoManager.queryBuilder().where(CompanySalesOrderInfoDao.Properties.Name.like("%" + StringUtil.getText(et_sales_name) + "%"), CompanySalesOrderInfoDao.Properties.State.eq(mStateCode), CompanySalesOrderInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanySalesOrderInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    } else if (!TextUtils.isEmpty(mFromUserId) && !TextUtils.isEmpty(mStateCode)) {
                        rows = mSalesOrderInfoDaoManager.queryBuilder().where(CompanySalesOrderInfoDao.Properties.Name.like("%" + StringUtil.getText(et_sales_name) + "%"), CompanySalesOrderInfoDao.Properties.UserId.eq(mFromUserId), CompanySalesOrderInfoDao.Properties.State.eq(mStateCode), CompanySalesOrderInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanySalesOrderInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    } else if (TextUtils.isEmpty(mFromUserId) && TextUtils.isEmpty(mStateCode)) {
                        rows = mSalesOrderInfoDaoManager.queryBuilder().where(CompanySalesOrderInfoDao.Properties.Name.like("%" + StringUtil.getText(et_sales_name) + "%"), CompanySalesOrderInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanySalesOrderInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    }
                } else {
                    rows = mSalesOrderInfoDaoManager.queryAll();
                }
                CompanySalesOrderInfoBean companySalesOrderInfoBean = new CompanySalesOrderInfoBean();
                companySalesOrderInfoBean.setRows(rows);
                getCompanySalesOrderList(companySalesOrderInfoBean);
                break;
            case REQUEST_DELETE_SALE_ORDER:
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setSuccess(true);
                deleteCompanySalesOrder(baseEntity, true);
                break;
        }
    }
}
