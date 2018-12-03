package com.africa.crm.businessmanagement.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.adapter.WorkStationListAdapter;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.station.activity.ContactManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CostumerManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.DeliveryOrderManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.EnterpriseAccountActivity;
import com.africa.crm.businessmanagement.main.station.activity.EnterpriseManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.ProductManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.PurchasingManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.QuotationManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.SalesOrderManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.ServiceRecordManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.SettingActivity;
import com.africa.crm.businessmanagement.main.station.activity.SupplierManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.SystemManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.TaskManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.TradingOrderManagementActivity;
import com.africa.crm.businessmanagement.widget.GridItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.baseutil.common.util.ToastUtils;
import com.africa.crm.businessmanagement.baseutil.library.base.progress.BaseActivityProgress;
import butterknife.BindView;

public class MainActivity extends BaseActivityProgress {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.rv_work_station)
    RecyclerView rv_work_station;

    @BindView(R.id.ll_message)
    LinearLayout ll_message;
    @BindView(R.id.iv_cancel)
    ImageView iv_cancel;

    private List<WorkStationInfo> mWorkStationInfoList = new ArrayList<>();
    private WorkStationListAdapter mWorkStationListAdapter;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        super.initView();
        titlebar_back.setVisibility(View.GONE);
        titlebar_name.setText(getString(R.string.work_station));

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                hideAnim.setDuration(300);
                ll_message.startAnimation(hideAnim);
                ll_message.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void initData() {
        WorkStationInfo workStationInfo = new WorkStationInfo();
        workStationInfo.setWork_type("1");
        workStationInfo.setWork_name(getString(R.string.enterprise_information_management));
        mWorkStationInfoList.add(workStationInfo);
        WorkStationInfo workStationInfo2 = new WorkStationInfo();
        workStationInfo2.setWork_type("2");
        workStationInfo2.setWork_name(getString(R.string.enterprise_account_management));
        mWorkStationInfoList.add(workStationInfo2);
        WorkStationInfo workStationInfo3 = new WorkStationInfo();
        workStationInfo3.setWork_type("3");
        workStationInfo3.setWork_name(getString(R.string.supplier_management));
        mWorkStationInfoList.add(workStationInfo3);
        WorkStationInfo workStationInfo4 = new WorkStationInfo();
        workStationInfo4.setWork_type("4");
        workStationInfo4.setWork_name(getString(R.string.product_management));
        mWorkStationInfoList.add(workStationInfo4);
        WorkStationInfo workStationInfo5 = new WorkStationInfo();
        workStationInfo5.setWork_type("5");
        workStationInfo5.setWork_name(getString(R.string.customer_management));
        mWorkStationInfoList.add(workStationInfo5);
        WorkStationInfo workStationInfo6 = new WorkStationInfo();
        workStationInfo6.setWork_type("6");
        workStationInfo6.setWork_name(getString(R.string.contact_management));
        mWorkStationInfoList.add(workStationInfo6);
        WorkStationInfo workStationInfo7 = new WorkStationInfo();
        workStationInfo7.setWork_type("7");
        workStationInfo7.setWork_name(getString(R.string.trading_order_management));
        mWorkStationInfoList.add(workStationInfo7);
        WorkStationInfo workStationInfo8 = new WorkStationInfo();
        workStationInfo8.setWork_type("8");
        workStationInfo8.setWork_name(getString(R.string.quotation_management));
        mWorkStationInfoList.add(workStationInfo8);
        WorkStationInfo workStationInfo9 = new WorkStationInfo();
        workStationInfo9.setWork_type("9");
        workStationInfo9.setWork_name(getString(R.string.sales_order_management));
        mWorkStationInfoList.add(workStationInfo9);
        WorkStationInfo workStationInfo10 = new WorkStationInfo();
        workStationInfo10.setWork_type("10");
        workStationInfo10.setWork_name(getString(R.string.delivery_order_management));
        mWorkStationInfoList.add(workStationInfo10);
        WorkStationInfo workStationInfo11 = new WorkStationInfo();
        workStationInfo11.setWork_type("11");
        workStationInfo11.setWork_name(getString(R.string.service_record_management));
        mWorkStationInfoList.add(workStationInfo11);
        WorkStationInfo workStationInfo12 = new WorkStationInfo();
        workStationInfo12.setWork_type("12");
        workStationInfo12.setWork_name(getString(R.string.purchasing_management));
        mWorkStationInfoList.add(workStationInfo12);
        WorkStationInfo workStationInfo13 = new WorkStationInfo();
        workStationInfo13.setWork_type("13");
        workStationInfo13.setWork_name(getString(R.string.task_management));
        mWorkStationInfoList.add(workStationInfo13);
        WorkStationInfo workStationInfo14 = new WorkStationInfo();
        workStationInfo14.setWork_type("14");
        workStationInfo14.setWork_name(getString(R.string.system_management));
        mWorkStationInfoList.add(workStationInfo14);
        WorkStationInfo workStationInfo15 = new WorkStationInfo();
        workStationInfo15.setWork_type("15");
        workStationInfo15.setWork_name(getString(R.string.setting));
        mWorkStationInfoList.add(workStationInfo15);
        setWorkStationDatas(mWorkStationInfoList);
    }

    /**
     * 设置工作台数据
     *
     * @param workStationInfoList
     */
    private void setWorkStationDatas(final List<WorkStationInfo> workStationInfoList) {
        if (!ListUtils.isEmpty(workStationInfoList)) {
            mWorkStationListAdapter = new WorkStationListAdapter(workStationInfoList);
            rv_work_station.setAdapter(mWorkStationListAdapter);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            rv_work_station.setLayoutManager(layoutManager);
            rv_work_station.addItemDecoration(new GridItemDecoration(0, 3));
            rv_work_station.setHasFixedSize(true);
            rv_work_station.setNestedScrollingEnabled(false);

            mWorkStationListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    WorkStationInfo workStationInfo = workStationInfoList.get(position);
                    String type = workStationInfoList.get(position).getWork_type();
                    switch (type) {
                        case "1":
                            EnterpriseManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "2":
                            EnterpriseAccountActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "3":
                            SupplierManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "4":
                            ProductManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "5":
                            CostumerManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "6":
                            ContactManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "7":
                            TradingOrderManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "8":
                            QuotationManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "9":
                            SalesOrderManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "10":
                            DeliveryOrderManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "11":
                            ServiceRecordManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "12":
                            PurchasingManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "13":
                            TaskManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "14":
                            SystemManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "15":
                            SettingActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        default:
                            ToastUtils.show(MainActivity.this, "暂无该分类");
                            break;
                    }
                }
            });
        }
    }
}
