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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyTradingOrderEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteTradingOrderInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteTradingOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyTradingOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.TradingOrderListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTradingOrderContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyTradingOrderPresenter;
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
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_TRADING_ORDER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_TRADING_ORDER;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyTradingOrderManagementActivity extends BaseRefreshMvpActivity<CompanyTradingOrderPresenter> implements CompanyTradingOrderContract.View {
    @BindView(R.id.ll_manager)
    LinearLayout ll_manager;
    @BindView(R.id.et_quotation_name_a)
    EditText et_quotation_name_a;
    @BindView(R.id.et_quotation_name_b)
    EditText et_quotation_name_b;
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
    private TradingOrderListAdapter mTradingOrderListAdapter;
    private List<CompanyTradingOrderInfo> mDeleteList = new ArrayList<>();
    private List<CompanyTradingOrderInfo> mTradingOrderInfoBeanList = new ArrayList<>();

    private WorkStationInfo mWorkStationInfo;
    private boolean mShowCheckBox = false;
    private AlertDialog mDeleteDialog;
    private String mCompanyId = "";
    private String mRoleCode = "";
    private String quotataion_name = "";
    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private GreendaoManager<CompanyTradingOrderInfo, CompanyTradingOrderInfoDao> mTradingOrderInfoDaoManager;
    private GreendaoManager<CompanyDeleteTradingOrderInfo, CompanyDeleteTradingOrderInfoDao> mDeleteTradingOrderInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanyTradingOrderInfo> mTradingOrderLocalBeanList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyTradingOrderManagementActivity.class);
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
        setContentView(R.layout.activity_company_trading_order_management);
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
            ll_manager.setVisibility(View.VISIBLE);
            et_quotation_name_b.setVisibility(View.GONE);
            titlebar_right.setVisibility(View.GONE);
        } else {
            ll_manager.setVisibility(View.GONE);
            et_quotation_name_b.setVisibility(View.VISIBLE);
            titlebar_right.setVisibility(View.VISIBLE);
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

        //得到Dao对象管理器
        mTradingOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyTradingOrderInfoDao());
        //得到Dao对象管理器
        mDeleteTradingOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteTradingOrderInfoDao());
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
/*
        et_quotation_name_a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    spinner_user.setText("");
                    mUserId = "";
                    tv_start_time.setText("");
                    tv_end_time.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
/*
        et_quotation_name_b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    tv_start_time.setText("");
                    tv_end_time.setText("");
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
    protected CompanyTradingOrderPresenter injectPresenter() {
        return new CompanyTradingOrderPresenter();
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
            quotataion_name = StringUtil.getText(et_quotation_name_b);
            mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        } else {
            quotataion_name = StringUtil.getText(et_quotation_name_a);
            mUserId = mFromUserId;
        }
        mPresenter.getCompanyTradingOrderList(page, rows, mCompanyId, mUserId, quotataion_name, StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time));
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
                if (mTradingOrderListAdapter != null) {
                    mTradingOrderListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyTradingOrderDetailActivity.startActivity(CompanyTradingOrderManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyTradingOrderInfo companyTradingOrderInfo : mTradingOrderInfoBeanList) {
                    if (companyTradingOrderInfo.isChosen()) {
                        mDeleteList.add(companyTradingOrderInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyTradingOrderManagementActivity.this)
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
                                    mPresenter.deleteCompanyTradingOrder(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public void initData() {
        mTradingOrderListAdapter = new TradingOrderListAdapter(mTradingOrderInfoBeanList);
        recyclerView.setAdapter(mTradingOrderListAdapter);
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
    public void getCompanyTradingOrderList(CompanyTradingOrderInfoBean companyTradingOrderInfoBean) {
        if (companyTradingOrderInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyTradingOrderInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                  /*  KeyboardUtil.clearInputBox(et_quotation_name_a);
                    KeyboardUtil.clearInputBox(et_quotation_name_b);
                    KeyboardUtil.clearInputBox(tv_start_time);
                    KeyboardUtil.clearInputBox(tv_end_time);
                    spinner_user.setText("");
                    mUserId = "";*/
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mTradingOrderInfoBeanList.clear();
                mTradingOrderLocalBeanList = mTradingOrderInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyTradingOrderInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyTradingOrderInfoBean.getRows())) {
                mTradingOrderInfoBeanList.addAll(companyTradingOrderInfoBean.getRows());
                List<CompanyTradingOrderInfo> addList = DifferentDataUtil.addTradindOrderDataToLocal(mTradingOrderInfoBeanList, mTradingOrderLocalBeanList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyTradingOrderInfo companyInfo : addList) {
                        mTradingOrderInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mTradingOrderLocalBeanList = new ArrayList<>();
                    mTradingOrderLocalBeanList = mTradingOrderInfoDaoManager.queryAll();
                }
                for (CompanyTradingOrderInfo info : mTradingOrderInfoBeanList) {
                    for (CompanyTradingOrderInfo localInfo : mTradingOrderLocalBeanList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mTradingOrderInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mTradingOrderListAdapter != null) {
                    mTradingOrderListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mTradingOrderInfoBeanList)) {
                mTradingOrderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mTradingOrderInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                            mTradingOrderListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyTradingOrderDetailActivity.startActivity(CompanyTradingOrderManagementActivity.this, mTradingOrderInfoBeanList.get(position).getId(), mTradingOrderInfoBeanList.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteCompanyTradingOrder(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mTradingOrderInfoBeanList.contains(mDeleteList.get(i))) {
                    int position = mTradingOrderInfoBeanList.indexOf(mDeleteList.get(i));
                    mTradingOrderInfoBeanList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyTradingOrderInfo companyInfo : mDeleteList) {
                            CompanyDeleteTradingOrderInfo deleteTradingOrderInfo = new CompanyDeleteTradingOrderInfo(companyInfo.getCustomerName(), companyInfo.getCreateTimeDate(), companyInfo.getCreateTime(), companyInfo.getRemark(), companyInfo.getClueSource(), companyInfo.getPossibility(), companyInfo.getCompanyName(), companyInfo.getUserNickName(), companyInfo.getId(), companyInfo.getEstimateProfit(), companyInfo.getPrice(), companyInfo.getEditAble(), companyInfo.getContactName(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getCompanyId(), false, true);
                            mDeleteTradingOrderInfoDaoManager.insertOrReplace(deleteTradingOrderInfo);
                        }
                    }
                    for (CompanyTradingOrderInfo companyInfo : mTradingOrderLocalBeanList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mTradingOrderInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mTradingOrderLocalBeanList = new ArrayList<>();
                    mTradingOrderLocalBeanList = mTradingOrderInfoDaoManager.queryAll();
                    if (mTradingOrderListAdapter != null) {
                        mTradingOrderListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mTradingOrderInfoBeanList)) {
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
    public void Event(AddOrSaveCompanyTradingOrderEvent addOrSaveCompanyTradingOrderEvent) {
        toastMsg(addOrSaveCompanyTradingOrderEvent.getMsg());
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
            case REQUEST_COMPANY_TRADING_ORDER_LIST:
                List<CompanyTradingOrderInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(quotataion_name) || !TextUtils.isEmpty(mFromUserId) || !TextUtils.isEmpty(StringUtil.getText(tv_start_time)) || !TextUtils.isEmpty(StringUtil.getText(tv_end_time))) {
                    if (!TextUtils.isEmpty(mFromUserId)) {
                        rows = mTradingOrderInfoDaoManager.queryBuilder().where(CompanyTradingOrderInfoDao.Properties.Name.like("%" + quotataion_name + "%"), CompanyTradingOrderInfoDao.Properties.UserId.eq(mFromUserId), CompanyTradingOrderInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyTradingOrderInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    } else {
                        rows = mTradingOrderInfoDaoManager.queryBuilder().where(CompanyTradingOrderInfoDao.Properties.Name.like("%" + quotataion_name + "%"), CompanyTradingOrderInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyTradingOrderInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    }
                } else {
                    rows = mTradingOrderInfoDaoManager.queryAll();
                }
                CompanyTradingOrderInfoBean companyTradingOrderInfoBean = new CompanyTradingOrderInfoBean();
                companyTradingOrderInfoBean.setRows(rows);
                getCompanyTradingOrderList(companyTradingOrderInfoBean);
                break;
            case REQUEST_DELETE_COMPANY_TRADING_ORDER:
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setSuccess(true);
                deleteCompanyTradingOrder(baseEntity, true);
                break;
        }
    }
}
