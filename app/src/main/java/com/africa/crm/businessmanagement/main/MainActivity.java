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
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.main.adapter.WorkStationListAdapter;
import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.contract.MainContract;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.presenter.MainPresenter;
import com.africa.crm.businessmanagement.main.station.activity.CompanyAccountActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyClientManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyContactManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyInfoManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyPaymentManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanySupplierManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyTradingOrderManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyDeliveryOrderManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.InventoryManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.PackagingDataManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.PdfReportManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyProductManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyPurchasingManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyQuotationManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanySalesOrderManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyServiceRecordManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.SettingActivity;
import com.africa.crm.businessmanagement.main.station.activity.SystemManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.TaskManagementActivity;
import com.africa.crm.businessmanagement.mvp.activity.BaseEasyMvpActivity;
import com.africa.crm.businessmanagement.widget.GridItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseEasyMvpActivity<MainPresenter> implements MainContract.View {
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
    protected MainPresenter injectPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void requestData() {
        long userId = UserInfoManager.getUserLoginInfo(this).getId();
        if (userId != 0) {
            mPresenter.getMainStationInfo(String.valueOf(userId));
        }
    }

    @Override
    public void initView() {
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
        workStationInfo11.setWork_name(getString(R.string.payment_management));
        mWorkStationInfoList.add(workStationInfo11);
        WorkStationInfo workStationInfo12 = new WorkStationInfo();
        workStationInfo12.setWork_type("12");
        workStationInfo12.setWork_name(getString(R.string.service_record_management));
        mWorkStationInfoList.add(workStationInfo12);
        WorkStationInfo workStationInfo13 = new WorkStationInfo();
        workStationInfo13.setWork_type("13");
        workStationInfo13.setWork_name(getString(R.string.inventory_management));
        mWorkStationInfoList.add(workStationInfo13);
        WorkStationInfo workStationInfo14 = new WorkStationInfo();
        workStationInfo14.setWork_type("14");
        workStationInfo14.setWork_name(getString(R.string.purchasing_management));
        mWorkStationInfoList.add(workStationInfo14);
        WorkStationInfo workStationInfo15 = new WorkStationInfo();
        workStationInfo15.setWork_type("15");
        workStationInfo15.setWork_name(getString(R.string.task_management));
        mWorkStationInfoList.add(workStationInfo15);
        WorkStationInfo workStationInfo16 = new WorkStationInfo();
        workStationInfo16.setWork_type("16");
        workStationInfo16.setWork_name(getString(R.string.pdf_report_management));
        mWorkStationInfoList.add(workStationInfo16);
        WorkStationInfo workStationInfo17 = new WorkStationInfo();
        workStationInfo17.setWork_type("17");
        workStationInfo17.setWork_name(getString(R.string.packaging_data_management));
        mWorkStationInfoList.add(workStationInfo17);
        WorkStationInfo workStationInfo18 = new WorkStationInfo();
        workStationInfo18.setWork_type("18");
        workStationInfo18.setWork_name(getString(R.string.statistical_report_forms));
        mWorkStationInfoList.add(workStationInfo18);
        WorkStationInfo workStationInfo19 = new WorkStationInfo();
        workStationInfo19.setWork_type("19");
        workStationInfo19.setWork_name(getString(R.string.enterprise_expenditure_management));
        mWorkStationInfoList.add(workStationInfo19);
        WorkStationInfo workStationInfo20 = new WorkStationInfo();
        workStationInfo20.setWork_type("20");
        workStationInfo20.setWork_name(getString(R.string.system_management));
        mWorkStationInfoList.add(workStationInfo20);
        WorkStationInfo workStationInfo21 = new WorkStationInfo();
        workStationInfo21.setWork_type("21");
        workStationInfo21.setWork_name(getString(R.string.setting));
        mWorkStationInfoList.add(workStationInfo21);
        setWorkStationDatas(mWorkStationInfoList);
    }


    @Override
    public void getMainStationInfo(List<MainStationInfoBean> mainStationInfoBeanList) {

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
                            CompanyInfoManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "2":
                            CompanyAccountActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "3":
                            CompanySupplierManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "4":
                            CompanyProductManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "5":
                            CompanyClientManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "6":
                            CompanyContactManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "7":
                            CompanyTradingOrderManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "8":
                            CompanyQuotationManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "9":
                            CompanySalesOrderManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "10":
                            CompanyDeliveryOrderManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "11":
                            CompanyPaymentManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "12":
                            CompanyServiceRecordManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "13":
                            InventoryManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "14":
                            CompanyPurchasingManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "15":
                            TaskManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "16":
                            PdfReportManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "17":
                            PackagingDataManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "18":
                            showShortToast(getString(R.string.statistical_report_forms));
                            break;
                        case "19":
                            showShortToast(getString(R.string.enterprise_expenditure_management));
                            break;
                        case "20":
                            SystemManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "21":
                            SettingActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                    }
                }
            });
        }
    }

}
