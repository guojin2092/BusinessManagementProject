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
import com.africa.crm.businessmanagement.main.station.adapter.DeliveryOrderListAdapter;
import com.africa.crm.businessmanagement.main.bean.TradingOrderInfoBean;
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
public class DeliveryOrderManagementActivity extends BaseActivity {
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

    @BindView(R.id.rv_delivery_orders)
    RecyclerView rv_delivery_orders;
    private DeliveryOrderListAdapter mDeliveryOrderListAdapter;
    private List<TradingOrderInfoBean> mDeleteList = new ArrayList<>();
    private List<TradingOrderInfoBean> mTradingOrderInfoBeanList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, DeliveryOrderManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_delivery_order_management);
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
                if (mDeliveryOrderListAdapter != null) {
                    mDeliveryOrderListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加发货单");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(DeliveryOrderManagementActivity.this)
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
                                for (TradingOrderInfoBean tradingOrderInfoBean : mTradingOrderInfoBeanList) {
                                    if (tradingOrderInfoBean.isChosen()) {
                                        mDeleteList.add(tradingOrderInfoBean);
                                    }
                                }
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mTradingOrderInfoBeanList.contains(mDeleteList.get(i))) {
                                        int position = mTradingOrderInfoBeanList.indexOf(mDeleteList.get(i));
                                        mTradingOrderInfoBeanList.remove(mDeleteList.get(i));
                                        if (mDeliveryOrderListAdapter != null) {
                                            mDeliveryOrderListAdapter.notifyItemRemoved(position);
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
        TradingOrderInfoBean tradingOrderInfoBean = new TradingOrderInfoBean();
        tradingOrderInfoBean.setCompany("云茂地产");
        tradingOrderInfoBean.setMoney("187，000.00");
        tradingOrderInfoBean.setSender("张三");
        tradingOrderInfoBean.setChosen(false);
        mTradingOrderInfoBeanList.add(tradingOrderInfoBean);

        TradingOrderInfoBean tradingOrderInfoBean2 = new TradingOrderInfoBean();
        tradingOrderInfoBean2.setCompany("西行设计");
        tradingOrderInfoBean2.setMoney("196，000.00");
        tradingOrderInfoBean2.setSender("李四");
        tradingOrderInfoBean2.setChosen(false);
        mTradingOrderInfoBeanList.add(tradingOrderInfoBean2);

        TradingOrderInfoBean tradingOrderInfoBean3 = new TradingOrderInfoBean();
        tradingOrderInfoBean3.setCompany("西行设计");
        tradingOrderInfoBean3.setMoney("32，000.00");
        tradingOrderInfoBean3.setSender("赵六");
        tradingOrderInfoBean3.setChosen(false);
        mTradingOrderInfoBeanList.add(tradingOrderInfoBean3);

        setDeliveryOrderDatas(mTradingOrderInfoBeanList);
    }

    /**
     * 设置发货单数据
     *
     * @param tradingOrderDatas
     */
    private void setDeliveryOrderDatas(final List<TradingOrderInfoBean> tradingOrderDatas) {
        mDeliveryOrderListAdapter = new DeliveryOrderListAdapter(tradingOrderDatas);
        rv_delivery_orders.setAdapter(mDeliveryOrderListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_delivery_orders.setLayoutManager(layoutManager);
        rv_delivery_orders.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_delivery_orders.setHasFixedSize(true);
        rv_delivery_orders.setNestedScrollingEnabled(false);

        mDeliveryOrderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_delivery_orders, position, R.id.cb_choose);
                    mTradingOrderInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                    mDeliveryOrderListAdapter.notifyDataSetChanged();
                } else {
                    DeliveryOrderDetailActivity.startActivity(DeliveryOrderManagementActivity.this, mTradingOrderInfoBeanList.get(position));
                }
            }
        });
    }
}
