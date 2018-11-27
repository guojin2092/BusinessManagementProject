package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.station.adapter.CostumerInfoListAdapter;
import com.africa.crm.businessmanagement.main.station.adapter.SimplePaddingDecoration;
import com.africa.crm.businessmanagement.main.station.bean.EnterpriseInfoBean;
import com.africa.crm.businessmanagement.main.station.bean.TableInfo;
import com.africa.crm.businessmanagement.main.station.bean.TableInfoBean;

import java.util.ArrayList;
import java.util.List;

import baselibrary.library.base.BaseActivity;
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
public class SupplierDetailActivity extends BaseActivity implements OnRecyclerViewListener.OnItemClickListener {
    public final static String COSTUMER_INFO = "costumer_info";
    private EnterpriseInfoBean mCostumerInfoBean;
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
    public static void startActivity(Activity activity, EnterpriseInfoBean costumerInfoBean) {
        Intent intent = new Intent(activity, SupplierDetailActivity.class);
        intent.putExtra(COSTUMER_INFO, costumerInfoBean);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_supplier_detail);
    }

    @Override
    public void initView() {
        titlebar_back.setOnClickListener(this);
        mCostumerInfoBean = (EnterpriseInfoBean) getIntent().getSerializableExtra(COSTUMER_INFO);
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

        List<TableInfo> tableInfoList = new ArrayList<>();
        tableInfoList.add(new TableInfo("主题", "xxx"));
        tableInfoList.add(new TableInfo("活动类型", "任务"));
        tableInfoList.add(new TableInfo("状态", "进心中"));
        tableInfoList.add(new TableInfo("到期日期", "2018-11-11"));
        tableInfoList.add(new TableInfo("起始日期", "2018-10-11"));
        tableInfoList.add(new TableInfo("结束日期", "2018-12-11"));
        mTableInfoBeanList2.add(new TableInfoBean(tableInfoList));

        mDatas.add(new RecyclerViewData("联系人：", mTableInfoBeanList1, true));
        mDatas.add(new RecyclerViewData("任务及活动：", mTableInfoBeanList2, true));
        mDatas.add(new RecyclerViewData("采购订单：", mTableInfoBeanList3, true));
    }

    @Override
    public void onGroupItemClick(int position, int groupPosition, View view) {

    }

    @Override
    public void onChildItemClick(int position, int groupPosition, int childPosition, View view) {

    }


}
