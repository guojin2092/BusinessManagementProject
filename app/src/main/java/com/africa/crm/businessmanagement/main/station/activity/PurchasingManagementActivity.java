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
import com.africa.crm.businessmanagement.main.station.adapter.PurchasingListAdapter;
import com.africa.crm.businessmanagement.main.bean.PurchasingInfoBean;
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
public class PurchasingManagementActivity extends BaseActivity {
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

    @BindView(R.id.rv_purchasing)
    RecyclerView rv_purchasing;
    private PurchasingListAdapter mPurchasingListAdapter;
    private List<PurchasingInfoBean> mDeleteList = new ArrayList<>();
    private List<PurchasingInfoBean> mPurchasingInfoBeanList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, PurchasingManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_purchasing_management);
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
                if (mPurchasingListAdapter != null) {
                    mPurchasingListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加采购订单");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(PurchasingManagementActivity.this)
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
                                for (PurchasingInfoBean purchasingInfoBean : mPurchasingInfoBeanList) {
                                    if (purchasingInfoBean.isChosen()) {
                                        mDeleteList.add(purchasingInfoBean);
                                    }
                                }
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mPurchasingInfoBeanList.contains(mDeleteList.get(i))) {
                                        int position = mPurchasingInfoBeanList.indexOf(mDeleteList.get(i));
                                        mPurchasingInfoBeanList.remove(mDeleteList.get(i));
                                        if (mPurchasingListAdapter != null) {
                                            mPurchasingListAdapter.notifyItemRemoved(position);
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
        purchasingInfoBean.setType("2018采购订单111");
        purchasingInfoBean.setNumber("00011");
        purchasingInfoBean.setChosen(false);
        mPurchasingInfoBeanList.add(purchasingInfoBean);

        PurchasingInfoBean purchasingInfoBean2 = new PurchasingInfoBean();
        purchasingInfoBean2.setName("李四");
        purchasingInfoBean2.setType("采购名单");
        purchasingInfoBean2.setNumber("23411");
        purchasingInfoBean2.setChosen(false);
        mPurchasingInfoBeanList.add(purchasingInfoBean2);

        PurchasingInfoBean purchasingInfoBean3 = new PurchasingInfoBean();
        purchasingInfoBean3.setName("赵六");
        purchasingInfoBean3.setType("采购订单111");
        purchasingInfoBean3.setNumber("354011");
        purchasingInfoBean3.setChosen(false);
        mPurchasingInfoBeanList.add(purchasingInfoBean3);

        setPurchasingDatas(mPurchasingInfoBeanList);
    }

    /**
     * 设置采购订单数据
     *
     * @param purchasingInfoBeans
     */
    private void setPurchasingDatas(final List<PurchasingInfoBean> purchasingInfoBeans) {
        mPurchasingListAdapter = new PurchasingListAdapter(purchasingInfoBeans);
        rv_purchasing.setAdapter(mPurchasingListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_purchasing.setLayoutManager(layoutManager);
        rv_purchasing.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_purchasing.setHasFixedSize(true);
        rv_purchasing.setNestedScrollingEnabled(false);

        mPurchasingListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_purchasing, position, R.id.cb_choose);
                    mPurchasingInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                    mPurchasingListAdapter.notifyDataSetChanged();
                } else {
                    PurchasingDetailActivity.startActivity(PurchasingManagementActivity.this, mPurchasingInfoBeanList.get(position));
                }
            }
        });
    }
}
