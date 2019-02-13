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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPurchasingOrderEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeletePurchasingOrderInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyDeletePurchasingOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyPurchasingOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.PurchasingListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPurchasingOrderContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPurchasingOrderPresenter;
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
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PURCHASING_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_PURCHASING;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPurchasingManagementActivity extends BaseRefreshMvpActivity<CompanyPurchasingOrderPresenter> implements CompanyPurchasingOrderContract.View {
    @BindView(R.id.et_purchasing_name)
    EditText et_purchasing_name;
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
    private PurchasingListAdapter mPurchasingListAdapter;
    private List<CompanyPurchasingOrderInfo> mDeleteList = new ArrayList<>();
    private List<CompanyPurchasingOrderInfo> mPurchasingInfoBeanList = new ArrayList<>();

    private WorkStationInfo mWorkStationInfo;
    private boolean mShowCheckBox = false;
    private AlertDialog mDeleteDialog;
    private String mCompanyId = "";
    private String mRoleCode = "";

    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private GreendaoManager<CompanyPurchasingOrderInfo, CompanyPurchasingOrderInfoDao> mPurchasingOrderInfoDaoManager;
    private GreendaoManager<CompanyDeletePurchasingOrderInfo, CompanyDeletePurchasingOrderInfoDao> mDeletePurchasingOrderInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanyPurchasingOrderInfo> mPurchasingOrderLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyPurchasingManagementActivity.class);
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
        setContentView(R.layout.activity_purchasing_management);
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
            spinner_user.setVisibility(View.GONE);
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
        mPurchasingOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyPurchasingOrderInfoDao());
        //得到Dao对象管理器
        mDeletePurchasingOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeletePurchasingOrderInfoDao());
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
                        toastMsg(getString(R.string.The_end_time_cannot_be_earlier_than_the_start_time));
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
                        toastMsg(getString(R.string.The_end_time_cannot_be_earlier_than_the_start_time));
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
    protected CompanyPurchasingOrderPresenter injectPresenter() {
        return new CompanyPurchasingOrderPresenter();
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
        mPresenter.getCompanyPurchasingOrderList(page, rows, mCompanyId, mUserId, et_purchasing_name.getText().toString().trim(), et_code.getText().toString().trim(), tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
    }

    @Override
    public void initData() {
        mPurchasingListAdapter = new PurchasingListAdapter(mPurchasingInfoBeanList);
        recyclerView.setAdapter(mPurchasingListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
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
                if (titlebar_right.getText().toString().equals(getString(R.string.Delete))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_delete.setVisibility(View.VISIBLE);
                    mShowCheckBox = true;
                } else {
                    titlebar_right.setText(R.string.Delete);
                    tv_delete.setVisibility(View.GONE);
                    mShowCheckBox = false;
                }
                if (mPurchasingListAdapter != null) {
                    mPurchasingListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyPurchasingDetailActivity.startActivity(CompanyPurchasingManagementActivity.this, "",0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyPurchasingOrderInfo companyPurchasingOrderInfo : mPurchasingInfoBeanList) {
                    if (companyPurchasingOrderInfo.isChosen()) {
                        mDeleteList.add(companyPurchasingOrderInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg(getString(R.string.no_choose_delete));
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyPurchasingManagementActivity.this)
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
                                    mPresenter.deleteCompanyPurchasingOrder(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
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
    public void getCompanyPurchasingOrderList(CompanyPurchasingOrderInfoBean companyPurchasingOrderInfoBean) {
        if (companyPurchasingOrderInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyPurchasingOrderInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mPurchasingInfoBeanList.clear();
                mPurchasingOrderLocalList = mPurchasingOrderInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyPurchasingOrderInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyPurchasingOrderInfoBean.getRows())) {
                mPurchasingInfoBeanList.addAll(companyPurchasingOrderInfoBean.getRows());
                List<CompanyPurchasingOrderInfo> addList = DifferentDataUtil.addPurchasingDataToLocal(mPurchasingInfoBeanList, mPurchasingOrderLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyPurchasingOrderInfo companyInfo : addList) {
                        mPurchasingOrderInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mPurchasingOrderLocalList = new ArrayList<>();
                    mPurchasingOrderLocalList = mPurchasingOrderInfoDaoManager.queryAll();
                }
                for (CompanyPurchasingOrderInfo info : mPurchasingInfoBeanList) {
                    for (CompanyPurchasingOrderInfo localInfo : mPurchasingOrderLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mPurchasingOrderInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mPurchasingListAdapter != null) {
                    mPurchasingListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mPurchasingInfoBeanList)) {
                mPurchasingListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mPurchasingInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                            mPurchasingListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyPurchasingDetailActivity.startActivity(CompanyPurchasingManagementActivity.this, mPurchasingInfoBeanList.get(position).getId(),mPurchasingInfoBeanList.get(position).getLocalId());
                        }
                    }
                });
            }
        }
    }

    @Override
    public void deleteCompanyPurchasingOrder(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mPurchasingInfoBeanList.contains(mDeleteList.get(i))) {
                    int position = mPurchasingInfoBeanList.indexOf(mDeleteList.get(i));
                    mPurchasingInfoBeanList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyPurchasingOrderInfo companyInfo : mDeleteList) {
                            CompanyDeletePurchasingOrderInfo deletePurchasingOrderInfo = new CompanyDeletePurchasingOrderInfo(companyInfo.getCreateTime(), companyInfo.getCreateTimeDate(), companyInfo.getSupplierName(), companyInfo.getSendAddress(), companyInfo.getRemark(), companyInfo.getOrderDate(), companyInfo.getState(), companyInfo.getCode(), companyInfo.getCompanyName(), companyInfo.getUserNickName(), companyInfo.getId(), companyInfo.getArriveDate(), companyInfo.getEditAble(), companyInfo.getDestinationAddress(), companyInfo.getSendAddressZipCode(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getStateName(), companyInfo.getCompanyId(), companyInfo.getProducts(), companyInfo.getClause(), companyInfo.getDestinationAddressZipCode(), false, isLocal);
                            mDeletePurchasingOrderInfoDaoManager.insertOrReplace(deletePurchasingOrderInfo);
                        }
                    }
                    for (CompanyPurchasingOrderInfo companyInfo : mPurchasingOrderLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mPurchasingOrderInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mPurchasingOrderLocalList = new ArrayList<>();
                    mPurchasingOrderLocalList = mPurchasingOrderInfoDaoManager.queryAll();
                    if (mPurchasingListAdapter != null) {
                        mPurchasingListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mPurchasingInfoBeanList)) {
                titlebar_right.setText(R.string.Delete);
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
    public void Event(AddOrSaveCompanyPurchasingOrderEvent addOrSaveCompanyPurchasingOrderEvent) {
        toastMsg(addOrSaveCompanyPurchasingOrderEvent.getMsg());
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
            case REQUEST_COMPANY_PURCHASING_LIST:
                List<CompanyPurchasingOrderInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(StringUtil.getText(et_purchasing_name)) || !TextUtils.isEmpty(StringUtil.getText(et_code)) || !TextUtils.isEmpty(mFromUserId) || !TextUtils.isEmpty(StringUtil.getText(tv_start_time)) || !TextUtils.isEmpty(StringUtil.getText(tv_end_time))) {
                    if (!TextUtils.isEmpty(mFromUserId)) {
                        rows = mPurchasingOrderInfoDaoManager.queryBuilder().where(CompanyPurchasingOrderInfoDao.Properties.Name.like("%" + StringUtil.getText(et_purchasing_name) + "%"), CompanyPurchasingOrderInfoDao.Properties.Code.like("%" + StringUtil.getText(et_code) + "%"), CompanyPurchasingOrderInfoDao.Properties.UserId.eq(mFromUserId), CompanyPurchasingOrderInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyPurchasingOrderInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    } else {
                        rows = mPurchasingOrderInfoDaoManager.queryBuilder().where(CompanyPurchasingOrderInfoDao.Properties.Name.like("%" + StringUtil.getText(et_purchasing_name) + "%"), CompanyPurchasingOrderInfoDao.Properties.Code.like("%" + StringUtil.getText(et_code) + "%"), CompanyPurchasingOrderInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyPurchasingOrderInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    }
                } else {
                    rows = mPurchasingOrderInfoDaoManager.queryAll();
                }
                CompanyPurchasingOrderInfoBean companyPurchasingOrderInfoBean = new CompanyPurchasingOrderInfoBean();
                companyPurchasingOrderInfoBean.setRows(rows);
                getCompanyPurchasingOrderList(companyPurchasingOrderInfoBean);
                break;
            case REQUEST_DELETE_COMPANY_PURCHASING:
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setSuccess(true);
                deleteCompanyPurchasingOrder(baseEntity, true);
                break;
        }
    }
}
