package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveTaskEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTaskDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyTaskDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

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
    private static final String LEVEL_CODE = "TASKLEVEL";
    private List<DicInfo> mOrderLevelList = new ArrayList<>();
    private String mLevelCode = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private static final String STATE_CODE = "TASKSTATE";
    private List<DicInfo> mOrderStateList = new ArrayList<>();
    private String mStateCode = "";
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private TimePickerView pvTime;

    private String mTaskId = "";
    private String mCompanyId = "";
    private String mUserId = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyTaskDetailActivity.class);
        intent.putExtra("id", id);
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
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("任务详情");
        tv_remind_time.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        if (!TextUtils.isEmpty(mTaskId)) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        }
        initTimePicker();
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
        mPresenter.getLevels(LEVEL_CODE);
        mPresenter.getStates(STATE_CODE);
        mPresenter.getAllContact(mCompanyId);
        mPresenter.getAllCustomers(mCompanyId);
        if (!TextUtils.isEmpty(mTaskId)) {
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
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        spinner_customer_name.setListDatas(this, list);
    }

    @Override
    public void getAllContact(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        spinner_contact_name.setListDatas(this, list);
    }

    @Override
    public void getLevels(List<DicInfo> dicInfoList) {
        mOrderLevelList.clear();
        mOrderLevelList.addAll(dicInfoList);
        spinner_level.setListDatas(this, mOrderLevelList);

        spinner_level.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mLevelCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getStates(List<DicInfo> dicInfoList) {
        mOrderStateList.clear();
        mOrderStateList.addAll(dicInfoList);
        spinner_state.setListDatas(this, mOrderStateList);

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyTaskDetail(CompanyTaskInfo companyTaskInfo) {
        et_task_name.setText(companyTaskInfo.getName());
        tv_remind_time.setText(companyTaskInfo.getRemindTime());
        spinner_customer_name.setText(companyTaskInfo.getCustomerName());
        spinner_contact_name.setText(companyTaskInfo.getContactName());
        spinner_level.setText(companyTaskInfo.getLevelName());
        mLevelCode = companyTaskInfo.getLevel();
        spinner_state.setText(companyTaskInfo.getStateName());
        mStateCode = companyTaskInfo.getState();
        et_remark.setText(companyTaskInfo.getRemark());
    }

    @Override
    public void saveCompanyTask(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mTaskId)) {
                toastString = "任务创建成功";
            } else {
                toastString = "任务修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveTaskEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
