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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveServiceRecordEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteServiceRecordInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteServiceRecordInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyServiceRecordInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.ServiceRecordListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyServiceRecordOrderContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyServiceRecordPresenter;
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
import static com.africa.crm.businessmanagement.network.api.DicUtil.SERVICE_STATE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.SERVICE_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_SERVICE_RECORD;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_RECORD_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyServiceRecordManagementActivity extends BaseRefreshMvpActivity<CompanyServiceRecordPresenter> implements CompanyServiceRecordOrderContract.View {
    @BindView(R.id.et_service_record_name)
    EditText et_service_record_name;

    @BindView(R.id.spinner_user)
    MySpinner spinner_user;
    private List<DicInfo> mSpinnerCompanyUserList = new ArrayList<>();
    private String mFromUserId = "";
    private String mUserId = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private List<DicInfo> mServiceRecordStateList = new ArrayList<>();
    private String mStateCode = "";

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private List<DicInfo> mServiceRecordTypeList = new ArrayList<>();
    private String mTypeCode = "";

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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ServiceRecordListAdapter mServiceRecordListAdapter;
    private List<CompanyServiceRecordInfo> mDeleteList = new ArrayList<>();
    private List<CompanyServiceRecordInfo> mServiceRecordInfoBeanList = new ArrayList<>();

    private WorkStationInfo mWorkStationInfo;
    private boolean mShowCheckBox = false;
    private AlertDialog mDeleteDialog;
    private String mCompanyId = "";
    private String mRoleCode = "";

    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private GreendaoManager<CompanyServiceRecordInfo, CompanyServiceRecordInfoDao> mServiceRecordInfoDaoManager;
    private GreendaoManager<CompanyDeleteServiceRecordInfo, CompanyDeleteServiceRecordInfoDao> mDeleteServiceRecordInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanyServiceRecordInfo> mServiceRecordLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyServiceRecordManagementActivity.class);
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
        setContentView(R.layout.activity_service_record_management);
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
        mServiceRecordInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyServiceRecordInfoDao());
        //得到Dao对象管理器
        mDeleteServiceRecordInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteServiceRecordInfoDao());
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
                    if (mEndDate.getTime() < mStartDate.getTime()) {
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
    protected CompanyServiceRecordPresenter injectPresenter() {
        return new CompanyServiceRecordPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getState(SERVICE_STATE);
        mPresenter.getType(SERVICE_TYPE);
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
        mPresenter.getServiceRecordList(page, rows, mCompanyId, mUserId, et_service_record_name.getText().toString().trim(), mStateCode, mTypeCode, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
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
                if (mServiceRecordListAdapter != null) {
                    mServiceRecordListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyServiceRecordDetailActivity.startActivity(CompanyServiceRecordManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyServiceRecordInfo serviceRecordInfo : mServiceRecordInfoBeanList) {
                    if (serviceRecordInfo.isChosen()) {
                        mDeleteList.add(serviceRecordInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyServiceRecordManagementActivity.this)
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
                                    mPresenter.deleteServiceRecord(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public void initData() {
        mServiceRecordListAdapter = new ServiceRecordListAdapter(mServiceRecordInfoBeanList);
        recyclerView.setAdapter(mServiceRecordListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void getState(List<DicInfo> dicInfoList) {
        mServiceRecordStateList.clear();
        mServiceRecordStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mServiceRecordStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(SERVICE_STATE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_state.setListDatas(this, mServiceRecordStateList);
        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getType(List<DicInfo> dicInfoList) {
        mServiceRecordTypeList.clear();
        mServiceRecordTypeList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mServiceRecordTypeList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(SERVICE_TYPE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_type.setListDatas(this, mServiceRecordTypeList);
        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mTypeCode = dicInfo.getCode();
            }
        });
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
    public void getServiceRecordList(CompanyServiceRecordInfoBean companyServiceRecordInfoBean) {
        if (companyServiceRecordInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyServiceRecordInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mServiceRecordInfoBeanList.clear();
                mServiceRecordLocalList = mServiceRecordInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyServiceRecordInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyServiceRecordInfoBean.getRows())) {
                mServiceRecordInfoBeanList.addAll(companyServiceRecordInfoBean.getRows());
                List<CompanyServiceRecordInfo> addList = DifferentDataUtil.addServiceRecordDataToLocal(mServiceRecordInfoBeanList, mServiceRecordLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyServiceRecordInfo companyInfo : addList) {
                        mServiceRecordInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mServiceRecordLocalList = new ArrayList<>();
                    mServiceRecordLocalList = mServiceRecordInfoDaoManager.queryAll();
                }
                for (CompanyServiceRecordInfo info : mServiceRecordInfoBeanList) {
                    for (CompanyServiceRecordInfo localInfo : mServiceRecordLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mServiceRecordInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mServiceRecordListAdapter != null) {
                    mServiceRecordListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mServiceRecordInfoBeanList)) {
                mServiceRecordListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mServiceRecordInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                            mServiceRecordListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyServiceRecordDetailActivity.startActivity(CompanyServiceRecordManagementActivity.this, mServiceRecordInfoBeanList.get(position).getId(), mServiceRecordInfoBeanList.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteServiceRecord(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mServiceRecordInfoBeanList.contains(mDeleteList.get(i))) {
                    int position = mServiceRecordInfoBeanList.indexOf(mDeleteList.get(i));
                    mServiceRecordInfoBeanList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyServiceRecordInfo companyInfo : mDeleteList) {
                            CompanyDeleteServiceRecordInfo deleteServiceRecordInfo = new CompanyDeleteServiceRecordInfo(companyInfo.getCustomerName(), companyInfo.getCreateTime(), companyInfo.getCreateTimeDate(), companyInfo.getPhone(), companyInfo.getRemark(), companyInfo.getReason(), companyInfo.getTrack(), companyInfo.getState(), companyInfo.getType(), companyInfo.getCompanyName(), companyInfo.getSolution(), companyInfo.getUserNickName(), companyInfo.getProductId(), companyInfo.getTypeName(), companyInfo.getId(), companyInfo.getLevel(), companyInfo.getEmail(), companyInfo.getEditAble(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getStateName(), companyInfo.getLevelName(), companyInfo.getCompanyId(), companyInfo.getProductName(), false, isLocal);
                            mDeleteServiceRecordInfoDaoManager.insertOrReplace(deleteServiceRecordInfo);
                        }
                    }
                    for (CompanyServiceRecordInfo companyInfo : mServiceRecordLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mServiceRecordInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mServiceRecordLocalList = new ArrayList<>();
                    mServiceRecordLocalList = mServiceRecordInfoDaoManager.queryAll();
                    if (mServiceRecordListAdapter != null) {
                        mServiceRecordListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mServiceRecordInfoBeanList)) {
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
    public void Event(AddOrSaveServiceRecordEvent addOrSaveServiceRecordEvent) {
        toastMsg(addOrSaveServiceRecordEvent.getMsg());
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
            case REQUEST_SERVICE_STATE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(SERVICE_STATE)) {
                        stateList.add(dicInfo);
                    }
                }
                getState(stateList);
                break;
            case REQUEST_SERVICE_TYPE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(SERVICE_TYPE)) {
                        typeList.add(dicInfo);
                    }
                }
                getType(typeList);
                break;
            case REQUEST_ALL_USERS_LIST:
                List<DicInfo2> allUserList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_USERS)) {
                        allUserList.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllCompanyUsers(allUserList);
                break;
            case REQUEST_SERVICE_RECORD_LIST:
                List<CompanyServiceRecordInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(StringUtil.getText(et_service_record_name)) || !TextUtils.isEmpty(mFromUserId) || !TextUtils.isEmpty(mStateCode) || !TextUtils.isEmpty(mTypeCode) || !TextUtils.isEmpty(StringUtil.getText(tv_start_time)) || !TextUtils.isEmpty(StringUtil.getText(tv_end_time))) {
                    if (!TextUtils.isEmpty(mFromUserId)) {
                        if (!TextUtils.isEmpty(mStateCode) && !TextUtils.isEmpty(mTypeCode)) {
                            rows = mServiceRecordInfoDaoManager.queryBuilder().where(CompanyServiceRecordInfoDao.Properties.Name.like("%" + StringUtil.getText(et_service_record_name) + "%"), CompanyServiceRecordInfoDao.Properties.State.eq(mStateCode), CompanyServiceRecordInfoDao.Properties.Type.eq(mTypeCode), CompanyServiceRecordInfoDao.Properties.UserId.eq(mFromUserId), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                        } else if (!TextUtils.isEmpty(mStateCode) && TextUtils.isEmpty(mTypeCode)) {
                            rows = mServiceRecordInfoDaoManager.queryBuilder().where(CompanyServiceRecordInfoDao.Properties.Name.like("%" + StringUtil.getText(et_service_record_name) + "%"), CompanyServiceRecordInfoDao.Properties.State.eq(mStateCode), CompanyServiceRecordInfoDao.Properties.UserId.eq(mFromUserId), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                        } else if (TextUtils.isEmpty(mStateCode) && !TextUtils.isEmpty(mTypeCode)) {
                            rows = mServiceRecordInfoDaoManager.queryBuilder().where(CompanyServiceRecordInfoDao.Properties.Name.like("%" + StringUtil.getText(et_service_record_name) + "%"), CompanyServiceRecordInfoDao.Properties.Type.eq(mTypeCode), CompanyServiceRecordInfoDao.Properties.UserId.eq(mFromUserId), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                        } else if (TextUtils.isEmpty(mStateCode) && TextUtils.isEmpty(mTypeCode)) {
                            rows = mServiceRecordInfoDaoManager.queryBuilder().where(CompanyServiceRecordInfoDao.Properties.Name.like("%" + StringUtil.getText(et_service_record_name) + "%"), CompanyServiceRecordInfoDao.Properties.UserId.eq(mFromUserId), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                        }
                    } else {
                        if (!TextUtils.isEmpty(mStateCode) && !TextUtils.isEmpty(mTypeCode)) {
                            rows = mServiceRecordInfoDaoManager.queryBuilder().where(CompanyServiceRecordInfoDao.Properties.Name.like("%" + StringUtil.getText(et_service_record_name) + "%"), CompanyServiceRecordInfoDao.Properties.State.eq(mStateCode), CompanyServiceRecordInfoDao.Properties.Type.eq(mTypeCode), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                        } else if (!TextUtils.isEmpty(mStateCode) && TextUtils.isEmpty(mTypeCode)) {
                            rows = mServiceRecordInfoDaoManager.queryBuilder().where(CompanyServiceRecordInfoDao.Properties.Name.like("%" + StringUtil.getText(et_service_record_name) + "%"), CompanyServiceRecordInfoDao.Properties.State.eq(mStateCode), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                        } else if (TextUtils.isEmpty(mStateCode) && !TextUtils.isEmpty(mTypeCode)) {
                            rows = mServiceRecordInfoDaoManager.queryBuilder().where(CompanyServiceRecordInfoDao.Properties.Name.like("%" + StringUtil.getText(et_service_record_name) + "%"), CompanyServiceRecordInfoDao.Properties.Type.eq(mTypeCode), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                        } else if (TextUtils.isEmpty(mStateCode) && TextUtils.isEmpty(mTypeCode)) {
                            rows = mServiceRecordInfoDaoManager.queryBuilder().where(CompanyServiceRecordInfoDao.Properties.Name.like("%" + StringUtil.getText(et_service_record_name) + "%"), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyServiceRecordInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                        }
                    }
                } else {
                    rows = mServiceRecordInfoDaoManager.queryAll();
                }
                CompanyServiceRecordInfoBean companyServiceRecordInfoBean = new CompanyServiceRecordInfoBean();
                companyServiceRecordInfoBean.setRows(rows);
                getServiceRecordList(companyServiceRecordInfoBean);
                break;
            case REQUEST_DELETE_SERVICE_RECORD:
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setSuccess(true);
                deleteServiceRecord(baseEntity, true);
                break;
        }
    }
}
