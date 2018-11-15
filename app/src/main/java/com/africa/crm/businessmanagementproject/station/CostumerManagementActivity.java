package com.africa.crm.businessmanagementproject.station;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagementproject.R;
import com.africa.crm.businessmanagementproject.station.adapter.CostumerListAdapter;
import com.africa.crm.businessmanagementproject.station.bean.CostumerInfoBean;
import com.africa.crm.businessmanagementproject.widget.LineItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import baselibrary.common.util.ListUtils;
import baselibrary.common.util.ToastUtils;
import baselibrary.library.base.progress.BaseActivityProgress;
import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/14 0014 16:39
 * Modification  History:
 * Why & What is modified:
 */
public class CostumerManagementActivity extends BaseActivityProgress {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;

    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.rv_costumer)
    RecyclerView rv_costumer;
    private CostumerListAdapter mCostumerListAdapter;
    private List<CostumerInfoBean> mCostumerInfoBeanList = new ArrayList<>();

    /**
     * @param activity
     */
    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, CostumerManagementActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.right_push_in, R.anim.hold);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_customer_management_list);
    }

    @Override
    public void initView() {
        super.initView();
        titlebar_back.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        titlebar_name.setText(getString(R.string.all_customers));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加客户信息");
                break;
        }
    }

    @Override
    public void initData() {
        CostumerInfoBean costumerInfoBean = new CostumerInfoBean();
        costumerInfoBean.setIcon("1");
        costumerInfoBean.setCompany("云茂地产");
        costumerInfoBean.setType("科技");
        costumerInfoBean.setLocation("上海");
        mCostumerInfoBeanList.add(costumerInfoBean);

        CostumerInfoBean costumerInfoBean2 = new CostumerInfoBean();
        costumerInfoBean2.setIcon("2");
        costumerInfoBean2.setCompany("西行设计");
        costumerInfoBean2.setType("教育");
        costumerInfoBean2.setLocation("沈阳");
        mCostumerInfoBeanList.add(costumerInfoBean2);

        CostumerInfoBean costumerInfoBean3 = new CostumerInfoBean();
        costumerInfoBean3.setIcon("3");
        costumerInfoBean3.setCompany("兴时科技");
        costumerInfoBean3.setType("金融服务");
        costumerInfoBean3.setLocation("江西");
        mCostumerInfoBeanList.add(costumerInfoBean3);

        setCostomerData(mCostumerInfoBeanList);
    }

    /**
     * 设置客户数据
     *
     * @param mCostumerInfoBeanList
     */
    private void setCostomerData(final List<CostumerInfoBean> mCostumerInfoBeanList) {
        if (!ListUtils.isEmpty(mCostumerInfoBeanList)) {
            mCostumerListAdapter = new CostumerListAdapter(R.layout.item_customer_list, mCostumerInfoBeanList);
            rv_costumer.setAdapter(mCostumerListAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rv_costumer.setLayoutManager(layoutManager);
            rv_costumer.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
            rv_costumer.setHasFixedSize(true);
            rv_costumer.setNestedScrollingEnabled(false);

            mCostumerListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ToastUtils.show(CostumerManagementActivity.this, mCostumerInfoBeanList.get(position).getCompany());
                    CostumerDetailActivity.startActivity(CostumerManagementActivity.this, mCostumerInfoBeanList.get(position));
                }
            });
        }
    }

}
