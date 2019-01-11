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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveTaskEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyTaskInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTaskDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyTaskDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_CONTACTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_CUSTOMERS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.TASK_LEVEL;
import static com.africa.crm.businessmanagement.network.api.DicUtil.TASK_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CONTACT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CUSTOMER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_TASK_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_TASK;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_TASK_LEVEL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_TASK_STATE;

public class CompanyTaskDetailActivity extends BaseMvpActivity<CompanyTaskDetailPresenter> implements CompanyTaskDetailContract.View {
    @BindView(R.id.et_task_name)
    EditText et_task_name;
    @BindView(R.id.tv_remind_time)
    TextView tv_remind_time;
    @BindView(R.id.spinner_customer_name)
    MySpinner spinner_customer_name;
    @BindView(R.id.spinner_contact_name)
    MySpinner spinner_contact_name;

    @BindView(R.id.spinner_level)
    MySpinner spinner_level;
    private List<DicInfo> mOrderLevelList = new ArrayList<>();
    private String mLevelCode = "";
    private String mLevelName = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private List<DicInfo> mOrderStateList = new ArrayList<>();
    private String mStateCode = "";
    private String mStateName = "";
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private TimePickerView pvTime;

    private String mTaskId = "";
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mCompanyName = "";
    private String mFromName = "";
    private String mUserId = "";
    private String mCustomerName = "";
    private String mContactName = "";
    private String mHasRemind = "2";

    private GreendaoManager<CompanyTaskInfo, CompanyTaskInfoDao> mTaskInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanyTaskInfo> mTaskInfoLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanyTaskDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_detail);
    }

    @Override
    protected CompanyTaskDetailPresenter injectPresenter() {
        return new CompanyTaskDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mTaskId = getIntent().getStringExtra("id");
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        mFromName = UserInfoManager.getUserLoginInfo(this).getName();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("任务详情");
        tv_remind_time.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        if (TextUtils.isEmpty(mTaskId) && mLocalId == 0l) {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mTaskId) || mLocalId != 0l) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        }
        initTimePicker();
        //得到Dao对象管理器
        mTaskInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyTaskInfoDao());
        //得到本地数据
        mTaskInfoLocalList = mTaskInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
    }

    private void initTimePicker() {
        pvTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv_remind_time.setText(TimeUtils.getTimeByMinute(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true));
    }


    @Override
    protected void requestData() {
        mPresenter.getLevels(TASK_LEVEL);
        mPresenter.getStates(TASK_STATE);
        mPresenter.getAllContact(mCompanyId);
        mPresenter.getAllCustomers(mCompanyId);
        if (!TextUtils.isEmpty(mTaskId) || mLocalId != 0l) {
            mPresenter.getCompanyTaskDetail(mTaskId);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_remind_time:
                pvTime.show();
                break;
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
                if (TextUtils.isEmpty(et_task_name.getText().toString().trim())) {
                    toastMsg("尚未填写任务名称");
                    return;
                }
                mPresenter.saveCompanyTask(mTaskId, mCompanyId, mUserId, et_task_name.getText().toString().trim(), tv_remind_time.getText().toString().trim(), spinner_customer_name.getText().trim().trim(), spinner_contact_name.getText().trim().trim(), mLevelCode, mStateCode, et_remark.getText().toString().trim());
                break;

        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_task_name.setEnabled(canInput);
        tv_remind_time.setEnabled(canInput);
        spinner_customer_name.getTextView().setEnabled(canInput);
        spinner_contact_name.getTextView().setEnabled(canInput);
        spinner_level.getTextView().setEnabled(canInput);
        spinner_state.getTextView().setEnabled(canInput);
        et_remark.setEnabled(canInput);
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
    public void getLevels(List<DicInfo> dicInfoList) {
        mOrderLevelList.clear();
        mOrderLevelList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mOrderLevelList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(TASK_LEVEL);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_level.setListDatas(this, mOrderLevelList);
        spinner_level.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mLevelCode = dicInfo.getCode();
                mLevelName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getStates(List<DicInfo> dicInfoList) {
        mOrderStateList.clear();
        mOrderStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mOrderStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(TASK_STATE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_state.setListDatas(this, mOrderStateList);
        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
                mStateName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getCompanyTaskDetail(CompanyTaskInfo companyTaskInfo) {
        if (companyTaskInfo != null) {
            et_task_name.setText(companyTaskInfo.getName());
            tv_remind_time.setText(companyTaskInfo.getRemindTime());
            spinner_customer_name.setText(companyTaskInfo.getCustomerName());
            spinner_contact_name.setText(companyTaskInfo.getContactName());
            spinner_level.setText(companyTaskInfo.getLevelName());
            mLevelCode = companyTaskInfo.getLevel();
            spinner_state.setText(companyTaskInfo.getStateName());
            mStateCode = companyTaskInfo.getState();
            et_remark.setText(companyTaskInfo.getRemark());
            mCustomerName = companyTaskInfo.getCustomerName();
            mCompanyName = companyTaskInfo.getCompanyName();
            mFromName = companyTaskInfo.getName();
            mContactName = companyTaskInfo.getContactName();
            mStateName = companyTaskInfo.getStateName();
            mLevelName = companyTaskInfo.getLevelName();
            mHasRemind = companyTaskInfo.getHasRemind();
            for (CompanyTaskInfo localInfo : mTaskInfoLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyTaskInfo.getId())) {
                    if (localInfo.getId().equals(companyTaskInfo.getId())) {
                        companyTaskInfo.setLocalId(localInfo.getLocalId());
                        mTaskInfoDaoManager.correct(companyTaskInfo);
                    }
                }
            }
        }

    }

    @Override
    public void saveCompanyTask(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mTaskId) && mLocalId == 0l) {
            toastString = "任务创建成功";
        } else {
            toastString = "任务修改成功";
        }
        if (isLocal) {
            CompanyTaskInfo companyTaskInfo = null;
            if (mLocalId == 0l) {
                companyTaskInfo = new CompanyTaskInfo(mCustomerName, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_remark), mStateCode, mCompanyName, mFromName, mTaskId, mLevelCode, StringUtil.getText(tv_remind_time), mContactName, mUserId, StringUtil.getText(et_task_name), mStateName, mLevelName, mCompanyId, mHasRemind, false, isLocal);
                mTaskInfoDaoManager.insertOrReplace(companyTaskInfo);
            } else {
                for (CompanyTaskInfo info : mTaskInfoLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyTaskInfo = new CompanyTaskInfo(info.getLocalId(), mCustomerName, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_remark), mStateCode, mCompanyName, mFromName, mTaskId, mLevelCode, StringUtil.getText(tv_remind_time), mContactName, mUserId, StringUtil.getText(et_task_name), mStateName, mLevelName, mCompanyId, mHasRemind, false, isLocal);
                        mTaskInfoDaoManager.correct(companyTaskInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveTaskEvent(toastString));
        finish();
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_ALL_CUSTOMER_LIST:
                List<DicInfo2> allCustomers = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_CUSTOMERS)) {
                        allCustomers.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllCustomers(allCustomers);
                break;
            case REQUEST_ALL_CONTACT_LIST:
                List<DicInfo2> allContact = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_CONTACTS)) {
                        allContact.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllContact(allContact);
                break;
            case REQUEST_TASK_LEVEL:
                List<DicInfo> taskList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(TASK_LEVEL)) {
                        taskList.add(dicInfo);
                    }
                }
                getLevels(taskList);
                break;
            case REQUEST_TASK_STATE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(TASK_STATE)) {
                        stateList.add(dicInfo);
                    }
                }
                getStates(stateList);
                break;
            case REQUEST_COMPANY_TASK_DETAIL:
                CompanyTaskInfo companyTaskInfo = null;
                for (CompanyTaskInfo info : mTaskInfoLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyTaskInfo = info;
                    }
                }
                getCompanyTaskDetail(companyTaskInfo);
                break;
            case REQUEST_SAVE_COMPANY_TASK:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mTaskId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyTask(uploadInfoBean, true);
                break;
        }
    }
}
