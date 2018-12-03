package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.station.adapter.TaskListAdapter;
import com.africa.crm.businessmanagement.main.bean.PurchasingInfoBean;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import com.africa.crm.businessmanagement.baseutil.common.util.ToastUtils;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseActivity;
import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class TaskManagementActivity extends BaseActivity {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.titlebar_right)
    TextView titlebar_right;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_delete)
    TextView tv_delete;

    private WorkStationInfo mWorkStationInfo;

    @BindView(R.id.rv_task)
    RecyclerView rv_task;
    private TaskListAdapter mTaskListAdapter;
    private List<PurchasingInfoBean> mDeleteList = new ArrayList<>();
    private List<PurchasingInfoBean> mTaskInfoBeanList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, TaskManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_management);
    }

    @Override
    public void initView() {
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        titlebar_right.setText(R.string.delete);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
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
                if (mTaskListAdapter != null) {
                    mTaskListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加任务订单");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(TaskManagementActivity.this)
                        .setTitle("温馨提示")
                        .setMessage("是否确认删除？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                for (PurchasingInfoBean purchasingInfoBean : mTaskInfoBeanList) {
                                    if (purchasingInfoBean.isChosen()) {
                                        mDeleteList.add(purchasingInfoBean);
                                    }
                                }
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mTaskInfoBeanList.contains(mDeleteList.get(i))) {
                                        int position = mTaskInfoBeanList.indexOf(mDeleteList.get(i));
                                        mTaskInfoBeanList.remove(mDeleteList.get(i));
                                        if (mTaskListAdapter != null) {
                                            mTaskListAdapter.notifyItemRemoved(position);
                                        }
                                    }
                                }
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void initData() {
        PurchasingInfoBean purchasingInfoBean = new PurchasingInfoBean();
        purchasingInfoBean.setName("张三");
        purchasingInfoBean.setType("2018任务订单111");
        purchasingInfoBean.setNumber("00011");
        purchasingInfoBean.setChosen(false);
        mTaskInfoBeanList.add(purchasingInfoBean);

        PurchasingInfoBean purchasingInfoBean2 = new PurchasingInfoBean();
        purchasingInfoBean2.setName("李四");
        purchasingInfoBean2.setType("任务订单");
        purchasingInfoBean2.setNumber("23411");
        purchasingInfoBean2.setChosen(false);
        mTaskInfoBeanList.add(purchasingInfoBean2);

        PurchasingInfoBean purchasingInfoBean3 = new PurchasingInfoBean();
        purchasingInfoBean3.setName("赵六");
        purchasingInfoBean3.setType("任务订单111");
        purchasingInfoBean3.setNumber("354011");
        purchasingInfoBean3.setChosen(false);
        mTaskInfoBeanList.add(purchasingInfoBean3);

        setTaskDatas(mTaskInfoBeanList);
    }

    /**
     * 设置任务列表数据
     *
     * @param purchasingInfoBeans
     */
    private void setTaskDatas(final List<PurchasingInfoBean> purchasingInfoBeans) {
        mTaskListAdapter = new TaskListAdapter(purchasingInfoBeans);
        rv_task.setAdapter(mTaskListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_task.setLayoutManager(layoutManager);
        rv_task.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_task.setHasFixedSize(true);
        rv_task.setNestedScrollingEnabled(false);

        mTaskListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_task, position, R.id.cb_choose);
                    mTaskInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                    mTaskListAdapter.notifyDataSetChanged();
                } else {
                    TaskDetailActivity.startActivity(TaskManagementActivity.this, mTaskInfoBeanList.get(position));
                }
            }
        });
    }
}
