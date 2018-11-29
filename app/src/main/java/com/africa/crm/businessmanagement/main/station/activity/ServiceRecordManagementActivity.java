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
import com.africa.crm.businessmanagement.main.station.adapter.ServiceRecordListAdapter;
import com.africa.crm.businessmanagement.main.station.bean.ServiceRecordInfoBean;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import baselibrary.common.util.ToastUtils;
import baselibrary.library.base.BaseActivity;
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
public class ServiceRecordManagementActivity extends BaseActivity {
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

    @BindView(R.id.rv_service_record)
    RecyclerView rv_service_record;
    private ServiceRecordListAdapter mServiceRecordListAdapter;
    private List<ServiceRecordInfoBean> mDeleteList = new ArrayList<>();
    private List<ServiceRecordInfoBean> mServiceRecordInfoBeanList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, ServiceRecordManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_service_record_management);
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
                if (mServiceRecordListAdapter != null) {
                    mServiceRecordListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加发货单");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(ServiceRecordManagementActivity.this)
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
                                for (ServiceRecordInfoBean serviceRecordInfoBean : mServiceRecordInfoBeanList) {
                                    if (serviceRecordInfoBean.isChosen()) {
                                        mDeleteList.add(serviceRecordInfoBean);
                                    }
                                }
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mServiceRecordInfoBeanList.contains(mDeleteList.get(i))) {
                                        int position = mServiceRecordInfoBeanList.indexOf(mDeleteList.get(i));
                                        mServiceRecordInfoBeanList.remove(mDeleteList.get(i));
                                        if (mServiceRecordListAdapter != null) {
                                            mServiceRecordListAdapter.notifyItemRemoved(position);
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
        ServiceRecordInfoBean serviceRecordInfoBean = new ServiceRecordInfoBean();
        serviceRecordInfoBean.setClientName("张三");
        serviceRecordInfoBean.setServiceType("家政服务");
        serviceRecordInfoBean.setServiceState("已服务");
        serviceRecordInfoBean.setChosen(false);
        mServiceRecordInfoBeanList.add(serviceRecordInfoBean);

        ServiceRecordInfoBean serviceRecordInfoBean2 = new ServiceRecordInfoBean();
        serviceRecordInfoBean2.setClientName("李四");
        serviceRecordInfoBean2.setServiceType("钟点工");
        serviceRecordInfoBean2.setServiceState("请求服务");
        serviceRecordInfoBean2.setChosen(false);
        mServiceRecordInfoBeanList.add(serviceRecordInfoBean2);

        ServiceRecordInfoBean serviceRecordInfoBean3 = new ServiceRecordInfoBean();
        serviceRecordInfoBean3.setClientName("赵六");
        serviceRecordInfoBean3.setServiceType("保姆");
        serviceRecordInfoBean3.setServiceState("服务中");
        serviceRecordInfoBean3.setChosen(false);
        mServiceRecordInfoBeanList.add(serviceRecordInfoBean3);

        setServiceRecordDatas(mServiceRecordInfoBeanList);
    }

    /**
     * 设置服务记录数据
     *
     * @param serviceRecordInfoBeans
     */
    private void setServiceRecordDatas(final List<ServiceRecordInfoBean> serviceRecordInfoBeans) {
        mServiceRecordListAdapter = new ServiceRecordListAdapter(serviceRecordInfoBeans);
        rv_service_record.setAdapter(mServiceRecordListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_service_record.setLayoutManager(layoutManager);
        rv_service_record.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_service_record.setHasFixedSize(true);
        rv_service_record.setNestedScrollingEnabled(false);

        mServiceRecordListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_service_record, position, R.id.cb_choose);
                    mServiceRecordInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                    mServiceRecordListAdapter.notifyDataSetChanged();
                } else {
                    ServiceRecordDetailActivity.startActivity(ServiceRecordManagementActivity.this, mServiceRecordInfoBeanList.get(position));
                }
            }
        });
    }
}
