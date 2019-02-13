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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveTaskEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteTaskInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteTaskInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyTaskInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.TaskListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTaskManagementContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyTaskManagementPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.TASK_LEVEL;
import static com.africa.crm.businessmanagement.network.api.DicUtil.TASK_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_TASK_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_TASK;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_TASK_LEVEL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_TASK_STATE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyTaskManagementActivity extends BaseRefreshMvpActivity<CompanyTaskManagementPresenter> implements CompanyTaskManagementContract.View {
    @BindView(R.id.et_task_name)
    EditText et_task_name;
    @BindView(R.id.et_customer_name)
    EditText et_customer_name;

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private List<DicInfo> mSpinnerStateList = new ArrayList<>();
    private String mStateCode = "";

    @BindView(R.id.spinner_level)
    MySpinner spinner_level;
    private List<DicInfo> mSpinnerLevelList = new ArrayList<>();
    private String mLevelCode = "";

    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;

    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_delete)
    TextView tv_delete;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private TaskListAdapter mTaskListAdapter;
    private List<CompanyTaskInfo> mDeleteList = new ArrayList<>();
    private List<CompanyTaskInfo> mTaskInfoBeanList = new ArrayList<>();

    private boolean mShowCheckBox = false;
    private WorkStationInfo mWorkStationInfo;
    private AlertDialog mDeleteDialog;
    private String mUserId = "";
    private String mCompanyId = "";

    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private GreendaoManager<CompanyTaskInfo, CompanyTaskInfoDao> mTaskInfoDaoManager;
    private GreendaoManager<CompanyDeleteTaskInfo, CompanyDeleteTaskInfoDao> mDeleteTaskInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanyTaskInfo> mTaskInfoLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyTaskManagementActivity.class);
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
        setContentView(R.layout.activity_company_task_management);
    }

    @Override
    public void initView() {
        super.initView();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        ll_add.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        initTimePicker();
        //得到Dao对象管理器
        mTaskInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyTaskInfoDao());
        //得到Dao对象管理器
        mDeleteTaskInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteTaskInfoDao());
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
    protected CompanyTaskManagementPresenter injectPresenter() {
        return new CompanyTaskManagementPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getStates(TASK_STATE);
        mPresenter.getLevels(TASK_LEVEL);
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        mPresenter.getCompanyTaskList(page, rows, mCompanyId, mUserId, et_task_name.getText().toString().trim(), et_customer_name.getText().toString().trim(), mStateCode, mLevelCode, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
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
                if (mTaskListAdapter != null) {
                    mTaskListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyTaskDetailActivity.startActivity(CompanyTaskManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyTaskInfo companyTaskInfo : mTaskInfoBeanList) {
                    if (companyTaskInfo.isChosen()) {
                        mDeleteList.add(companyTaskInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg(getString(R.string.no_choose_delete));
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyTaskManagementActivity.this)
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
                                    mPresenter.deleteCompanyTask(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public void initData() {
        mTaskListAdapter = new TaskListAdapter(mTaskInfoBeanList);
        recyclerView.setAdapter(mTaskListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void getStates(List<DicInfo> dicInfoList) {
        mSpinnerStateList.clear();
        mSpinnerStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(TASK_STATE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_state.setListDatas(this, mSpinnerStateList);
        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getLevels(List<DicInfo> dicInfoList) {
        mSpinnerLevelList.clear();
        mSpinnerLevelList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerLevelList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(TASK_LEVEL);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_level.setListDatas(this, mSpinnerLevelList);
        spinner_level.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mLevelCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyTaskList(CompanyTaskInfoBean companyTaskInfoBean) {
        if (companyTaskInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyTaskInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mTaskInfoBeanList.clear();
                mTaskInfoLocalList = mTaskInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyTaskInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyTaskInfoBean.getRows())) {
                mTaskInfoBeanList.addAll(companyTaskInfoBean.getRows());
                List<CompanyTaskInfo> addList = DifferentDataUtil.addTaskDataToLocal(mTaskInfoBeanList, mTaskInfoLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyTaskInfo companyInfo : addList) {
                        mTaskInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mTaskInfoLocalList = new ArrayList<>();
                    mTaskInfoLocalList = mTaskInfoDaoManager.queryAll();
                }
                for (CompanyTaskInfo info : mTaskInfoBeanList) {
                    for (CompanyTaskInfo localInfo : mTaskInfoLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mTaskInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mTaskListAdapter != null) {
                    mTaskListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mTaskInfoBeanList)) {
                mTaskListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mTaskInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                            mTaskListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyTaskDetailActivity.startActivity(CompanyTaskManagementActivity.this, mTaskInfoBeanList.get(position).getId(), mTaskInfoBeanList.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteCompanyTask(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mTaskInfoBeanList.contains(mDeleteList.get(i))) {
                    int position = mTaskInfoBeanList.indexOf(mDeleteList.get(i));
                    mTaskInfoBeanList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyTaskInfo companyInfo : mDeleteList) {
                            CompanyDeleteTaskInfo deleteTaskInfo = new CompanyDeleteTaskInfo(companyInfo.getCustomerName(), companyInfo.getCreateTime(), companyInfo.getCreateTimeDate(), companyInfo.getRemark(), companyInfo.getState(), companyInfo.getCompanyName(), companyInfo.getUserNickName(), companyInfo.getId(), companyInfo.getLevel(), companyInfo.getRemindTime(), companyInfo.getContactName(), companyInfo.getUserId(), companyInfo.getName(), companyInfo.getStateName(), companyInfo.getLevelName(), companyInfo.getCompanyId(), companyInfo.getHasRemind(), false, isLocal);
                            mDeleteTaskInfoDaoManager.insertOrReplace(deleteTaskInfo);
                        }
                    }
                    for (CompanyTaskInfo companyInfo : mTaskInfoLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mTaskInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mTaskInfoLocalList = new ArrayList<>();
                    mTaskInfoLocalList = mTaskInfoDaoManager.queryAll();
                    if (mTaskListAdapter != null) {
                        mTaskListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mTaskInfoBeanList)) {
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
    public void Event(AddOrSaveTaskEvent addOrSaveTaskEvent) {
        toastMsg(addOrSaveTaskEvent.getMsg());
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
            case REQUEST_TASK_STATE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(TASK_STATE)) {
                        stateList.add(dicInfo);
                    }
                }
                getStates(stateList);
                break;
            case REQUEST_TASK_LEVEL:
                List<DicInfo> levelList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(TASK_LEVEL)) {
                        levelList.add(dicInfo);
                    }
                }
                getLevels(levelList);
                break;
            case REQUEST_COMPANY_TASK_LIST:
                List<CompanyTaskInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(StringUtil.getText(et_task_name)) || !TextUtils.isEmpty(StringUtil.getText(et_customer_name)) || !TextUtils.isEmpty(mStateCode) || !TextUtils.isEmpty(mLevelCode) || !TextUtils.isEmpty(StringUtil.getText(tv_start_time)) || !TextUtils.isEmpty(StringUtil.getText(tv_end_time))) {
                    if (!TextUtils.isEmpty(mStateCode) && !TextUtils.isEmpty(mLevelCode)) {
                        rows = mTaskInfoDaoManager.queryBuilder().where(CompanyTaskInfoDao.Properties.Name.like("%" + StringUtil.getText(et_task_name) + "%"), CompanyTaskInfoDao.Properties.CustomerName.like("%" + StringUtil.getText(et_customer_name) + "%"), CompanyTaskInfoDao.Properties.State.eq(mStateCode), CompanyTaskInfoDao.Properties.Level.eq(mLevelCode), CompanyTaskInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyTaskInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    } else if (!TextUtils.isEmpty(mStateCode) && TextUtils.isEmpty(mLevelCode)) {
                        rows = mTaskInfoDaoManager.queryBuilder().where(CompanyTaskInfoDao.Properties.Name.like("%" + StringUtil.getText(et_task_name) + "%"), CompanyTaskInfoDao.Properties.CustomerName.like("%" + StringUtil.getText(et_customer_name) + "%"), CompanyTaskInfoDao.Properties.State.eq(mStateCode), CompanyTaskInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyTaskInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    } else if (TextUtils.isEmpty(mStateCode) && !TextUtils.isEmpty(mLevelCode)) {
                        rows = mTaskInfoDaoManager.queryBuilder().where(CompanyTaskInfoDao.Properties.Name.like("%" + StringUtil.getText(et_task_name) + "%"), CompanyTaskInfoDao.Properties.CustomerName.like("%" + StringUtil.getText(et_customer_name) + "%"), CompanyTaskInfoDao.Properties.Level.eq(mLevelCode), CompanyTaskInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyTaskInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    } else if (TextUtils.isEmpty(mStateCode) && TextUtils.isEmpty(mLevelCode)) {
                        rows = mTaskInfoDaoManager.queryBuilder().where(CompanyTaskInfoDao.Properties.Name.like("%" + StringUtil.getText(et_task_name) + "%"), CompanyTaskInfoDao.Properties.CustomerName.like("%" + StringUtil.getText(et_customer_name) + "%"), CompanyTaskInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyTaskInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    }
                } else {
                    rows = mTaskInfoDaoManager.queryAll();
                }
                CompanyTaskInfoBean companyTaskInfoBean = new CompanyTaskInfoBean();
                companyTaskInfoBean.setRows(rows);
                getCompanyTaskList(companyTaskInfoBean);
                break;
            case REQUEST_DELETE_COMPANY_TASK:
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setSuccess(true);
                deleteCompanyTask(baseEntity, true);
                break;
        }
    }
}
