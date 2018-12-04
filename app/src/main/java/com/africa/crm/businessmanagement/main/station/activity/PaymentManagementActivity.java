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
import com.africa.crm.businessmanagement.baseutil.common.util.ToastUtils;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseActivity;
import com.africa.crm.businessmanagement.main.bean.PaymentInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.station.adapter.PaymentListAdapter;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

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
public class PaymentManagementActivity extends BaseActivity {
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

    @BindView(R.id.rv_payment)
    RecyclerView rv_payment;
    private PaymentListAdapter mPaymentListAdapter;
    private List<PaymentInfoBean> mDeleteList = new ArrayList<>();
    private List<PaymentInfoBean> mPaymentInfoBeanList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, PaymentManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_payment_management);
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
                if (mPaymentListAdapter != null) {
                    mPaymentListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加任务订单");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(PaymentManagementActivity.this)
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
                                for (PaymentInfoBean purchasingInfoBean : mPaymentInfoBeanList) {
                                    if (purchasingInfoBean.isChosen()) {
                                        mDeleteList.add(purchasingInfoBean);
                                    }
                                }
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mPaymentInfoBeanList.contains(mDeleteList.get(i))) {
                                        int position = mPaymentInfoBeanList.indexOf(mDeleteList.get(i));
                                        mPaymentInfoBeanList.remove(mDeleteList.get(i));
                                        if (mPaymentListAdapter != null) {
                                            mPaymentListAdapter.notifyItemRemoved(position);
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
        PaymentInfoBean paymentInfoBean = new PaymentInfoBean();
        paymentInfoBean.setUserName("张三");
        paymentInfoBean.setCompanyName("所属企业1");
        paymentInfoBean.setDate("2018-1-12");
        paymentInfoBean.setChosen(false);
        mPaymentInfoBeanList.add(paymentInfoBean);

        PaymentInfoBean paymentInfoBean2 = new PaymentInfoBean();
        paymentInfoBean2.setUserName("李四");
        paymentInfoBean2.setCompanyName("所属企业2");
        paymentInfoBean2.setDate("2018-2-12");
        paymentInfoBean2.setChosen(false);
        mPaymentInfoBeanList.add(paymentInfoBean2);

        PaymentInfoBean paymentInfoBean3 = new PaymentInfoBean();
        paymentInfoBean3.setUserName("赵六");
        paymentInfoBean3.setCompanyName("所属企业3");
        paymentInfoBean3.setDate("2018-3-12");
        paymentInfoBean3.setChosen(false);
        mPaymentInfoBeanList.add(paymentInfoBean3);

        setPaymentDatas(mPaymentInfoBeanList);
    }

    /**
     * 设置付款单列表数据
     *
     * @param paymentInfoBeans
     */
    private void setPaymentDatas(final List<PaymentInfoBean> paymentInfoBeans) {
        mPaymentListAdapter = new PaymentListAdapter(paymentInfoBeans);
        rv_payment.setAdapter(mPaymentListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_payment.setLayoutManager(layoutManager);
        rv_payment.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_payment.setHasFixedSize(true);
        rv_payment.setNestedScrollingEnabled(false);

        mPaymentListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_payment, position, R.id.cb_choose);
                    mPaymentInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                    mPaymentListAdapter.notifyDataSetChanged();
                } else {
                    PaymentDetailActivity.startActivity(PaymentManagementActivity.this, mPaymentInfoBeanList.get(position));
                }
            }
        });
    }
}
