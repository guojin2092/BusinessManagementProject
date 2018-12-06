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
import com.africa.crm.businessmanagement.main.bean.EnterpriseInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.station.adapter.InventoryListAdapter;
import com.africa.crm.businessmanagement.main.station.dialog.InventoryDialog;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;

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
public class InventoryManagementActivity extends BaseActivity {
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

    @BindView(R.id.rv_inventory)
    RecyclerView rv_inventory;
    private InventoryListAdapter mInventoryListAdapter;
    private List<EnterpriseInfoBean> mDeleteList = new ArrayList<>();
    private List<EnterpriseInfoBean> mInventoryInfoList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    private InventoryDialog mInventoryDialog;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, InventoryManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_inventory_management);
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
        mInventoryDialog = InventoryDialog.getInstance(this);
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
                if (mInventoryListAdapter != null) {
                    mInventoryListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加发货单");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(InventoryManagementActivity.this)
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
                                for (EnterpriseInfoBean serviceRecordInfoBean : mInventoryInfoList) {
                                    if (serviceRecordInfoBean.isChosen()) {
                                        mDeleteList.add(serviceRecordInfoBean);
                                    }
                                }
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mInventoryInfoList.contains(mDeleteList.get(i))) {
                                        int position = mInventoryInfoList.indexOf(mDeleteList.get(i));
                                        mInventoryInfoList.remove(mDeleteList.get(i));
                                        if (mInventoryListAdapter != null) {
                                            mInventoryListAdapter.notifyItemRemoved(position);
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
        EnterpriseInfoBean enterpriseInfoBean = new EnterpriseInfoBean();
        enterpriseInfoBean.setCompany("云茂地产");
        enterpriseInfoBean.setAccount("数量：13");
        enterpriseInfoBean.setChosen(false);
        mInventoryInfoList.add(enterpriseInfoBean);

        EnterpriseInfoBean enterpriseInfoBean2 = new EnterpriseInfoBean();
        enterpriseInfoBean2.setCompany("西行设计");
        enterpriseInfoBean2.setAccount("数量：3");
        enterpriseInfoBean2.setChosen(false);
        mInventoryInfoList.add(enterpriseInfoBean2);

        EnterpriseInfoBean enterpriseInfoBean3 = new EnterpriseInfoBean();
        enterpriseInfoBean3.setCompany("兴时科技");
        enterpriseInfoBean3.setAccount("数量：56");
        enterpriseInfoBean3.setChosen(false);
        mInventoryInfoList.add(enterpriseInfoBean3);

        setInventoryDatas(mInventoryInfoList);
    }

    /**
     * 设置服务记录数据
     *
     * @param enterpriseInfoBeans
     */
    private void setInventoryDatas(final List<EnterpriseInfoBean> enterpriseInfoBeans) {
        mInventoryListAdapter = new InventoryListAdapter(enterpriseInfoBeans);
        rv_inventory.setAdapter(mInventoryListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_inventory.setLayoutManager(layoutManager);
        rv_inventory.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_inventory.setHasFixedSize(true);
        rv_inventory.setNestedScrollingEnabled(false);

        mInventoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_inventory, position, R.id.cb_choose);
                    mInventoryInfoList.get(position).setChosen(!cb_choose.isChecked());
                    mInventoryListAdapter.notifyDataSetChanged();
                } else {
                    InventoryDetailActivity.startActivity(InventoryManagementActivity.this, mInventoryInfoList.get(position));
                }
            }
        });
        mInventoryListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mInventoryDialog.isCancelableOnTouchOutside(false)
                        .withDuration(300)
                        .withEffect(Effectstype.Fadein)
                        .setCancelClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mInventoryDialog.dismiss();
                            }
                        })
                        .setSaveClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mInventoryDialog.dismiss();
                            }
                        })
                        .show();

            }
        });
    }
}
