package com.africa.crm.businessmanagement.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.station.adapter.CostumerInfoListAdapter;
import com.africa.crm.businessmanagement.station.adapter.SimplePaddingDecoration;
import com.africa.crm.businessmanagement.station.bean.CostumerInfoBean;
import com.africa.crm.businessmanagement.station.bean.TableInfo;
import com.africa.crm.businessmanagement.station.bean.TableInfoBean;

import java.util.ArrayList;
import java.util.List;

import baselibrary.library.base.progress.BaseActivityProgress;
import butterknife.BindView;
import drawthink.expandablerecyclerview.bean.RecyclerViewData;
import drawthink.expandablerecyclerview.listener.OnRecyclerViewListener;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/15 0015 10:34
 * Modification  History:
 * Why & What is modified:
 */
public class CostumerDetailActivity extends BaseActivityProgress implements OnRecyclerViewListener.OnItemClickListener {
    public final static String COSTUMER_INFO = "costumer_info";
    private CostumerInfoBean mCostumerInfoBean;
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;

    @BindView(R.id.rv_costumer_info)
    RecyclerView mRv_costumer_info;
    private List<RecyclerViewData> mDatas;
    private CostumerInfoListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, CostumerInfoBean costumerInfoBean) {
        Intent intent = new Intent(activity, CostumerDetailActivity.class);
        intent.putExtra(COSTUMER_INFO, costumerInfoBean);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_right_in,R.anim.anim_left_out);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_customer_detail);
    }

    @Override
    public void initView() {
        super.initView();
        titlebar_back.setOnClickListener(this);
        mCostumerInfoBean = (CostumerInfoBean) getIntent().getSerializableExtra(COSTUMER_INFO);
        if (mCostumerInfoBean != null) {
            titlebar_name.setText(mCostumerInfoBean.getCompany());
            if (mCostumerInfoBean.getIcon().equals("1")) {
                iv_icon.setImageResource(R.drawable.iv_head_icon1);
            } else if (mCostumerInfoBean.getIcon().equals("2")) {
                iv_icon.setImageResource(R.drawable.iv_head_icon2);
            } else if (mCostumerInfoBean.getIcon().equals("3")) {
                iv_icon.setImageResource(R.drawable.iv_head_icon3);
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void initData() {
        mDatas = new ArrayList<>();
        addCostumerInfoDatas();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRv_costumer_info.setLayoutManager(linearLayoutManager);
        mRv_costumer_info.addItemDecoration(new SimplePaddingDecoration(this));

        adapter = new CostumerInfoListAdapter(this, mDatas);
        adapter.setOnItemClickListener(this);

        mRv_costumer_info.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void addCostumerInfoDatas() {
        List<TableInfoBean> mTableInfoBeanList1 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList2 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList3 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList4 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList5 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList6 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList7 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList8 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList9 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList10 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList11 = new ArrayList<>();
        List<TableInfoBean> mTableInfoBeanList12 = new ArrayList<>();

        List<TableInfo> tableInfoList1 = new ArrayList<>();
        tableInfoList1.add(new TableInfo("交易名称", "云茂地产"));
        tableInfoList1.add(new TableInfo("金额（¥）", "45，000.00"));
        tableInfoList1.add(new TableInfo("阶段", "需求分析"));
        mTableInfoBeanList1.add(new TableInfoBean(tableInfoList1));

        List<TableInfo> tableInfoList2 = new ArrayList<>();
        tableInfoList2.add(new TableInfo("主题", "中非友好活动"));
        tableInfoList2.add(new TableInfo("活动类型", "任务"));
        tableInfoList2.add(new TableInfo("状态", "进行中"));
        tableInfoList2.add(new TableInfo("结束日期", "2018-12-10"));
        mTableInfoBeanList2.add(new TableInfoBean(tableInfoList2));

        List<TableInfo> tableInfoList3 = new ArrayList<>();
        tableInfoList3.add(new TableInfo("阶段", "需求分析"));
        tableInfoList3.add(new TableInfo("可能性", "20"));
        tableInfoList3.add(new TableInfo("类型", "新业务"));
        mTableInfoBeanList3.add(new TableInfoBean(tableInfoList3));

        List<TableInfo> tableInfoList4 = new ArrayList<>();
        tableInfoList4.add(new TableInfo("通话开始时间", "2018-10-11"));
        tableInfoList4.add(new TableInfo("活动所有者", "姚明"));
        tableInfoList4.add(new TableInfo("修改时间", "2018-12-12"));
        tableInfoList4.add(new TableInfo("类型", "新业务"));
        mTableInfoBeanList4.add(new TableInfoBean(tableInfoList4));

        List<TableInfo> tableInfoList5 = new ArrayList<>();
        tableInfoList5.add(new TableInfo("通话开始时间", "2018-10-11"));
        tableInfoList5.add(new TableInfo("活动所有者", "姚明"));
        tableInfoList5.add(new TableInfo("修改时间", "2018-12-12"));
        tableInfoList5.add(new TableInfo("类型", "新业务"));
        mTableInfoBeanList5.add(new TableInfoBean(tableInfoList5));

        List<TableInfo> tableInfoList6 = new ArrayList<>();
        tableInfoList6.add(new TableInfo("主题", "中非友好活动"));
        tableInfoList6.add(new TableInfo("活动类型", "任务"));
        tableInfoList6.add(new TableInfo("状态", "进行中"));
        tableInfoList6.add(new TableInfo("到期日期", "2018-12-21"));
        tableInfoList6.add(new TableInfo("起始日期", "2018-10-11"));
        tableInfoList6.add(new TableInfo("结束日期", "2018-12-10"));
        mTableInfoBeanList6.add(new TableInfoBean(tableInfoList6));

        List<TableInfo> tableInfoList7 = new ArrayList<>();
        tableInfoList7.add(new TableInfo("通话开始时间", "2018-10-11"));
        tableInfoList7.add(new TableInfo("活动所有者", "姚明"));
        tableInfoList7.add(new TableInfo("修改时间", "2018-12-12"));
        tableInfoList7.add(new TableInfo("类型", "新业务"));
        mTableInfoBeanList7.add(new TableInfoBean(tableInfoList7));

        mDatas.add(new RecyclerViewData("备注：", mTableInfoBeanList1, true));
        mDatas.add(new RecyclerViewData("附件：", mTableInfoBeanList2, true));
        mDatas.add(new RecyclerViewData("交易：", mTableInfoBeanList3, true));
        mDatas.add(new RecyclerViewData("联系人：", mTableInfoBeanList4, true));
        mDatas.add(new RecyclerViewData("电子邮件：", mTableInfoBeanList5, true));
        mDatas.add(new RecyclerViewData("未关闭的活动：", mTableInfoBeanList6, true));
        mDatas.add(new RecyclerViewData("产品：", mTableInfoBeanList7, true));
        mDatas.add(new RecyclerViewData("报价：", mTableInfoBeanList8, true));
        mDatas.add(new RecyclerViewData("销售单：", mTableInfoBeanList9, true));
        mDatas.add(new RecyclerViewData("发货单：", mTableInfoBeanList10, true));
        mDatas.add(new RecyclerViewData("成员客户：", mTableInfoBeanList11, true));
        mDatas.add(new RecyclerViewData("服务支持：", mTableInfoBeanList12, true));

    }

    @Override
    public void onGroupItemClick(int position, int groupPosition, View view) {

    }

    @Override
    public void onChildItemClick(int position, int groupPosition, int childPosition, View view) {

    }


}
