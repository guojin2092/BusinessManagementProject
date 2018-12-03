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
import com.africa.crm.businessmanagement.main.station.adapter.SupplierListAdapter;
import com.africa.crm.businessmanagement.main.bean.EnterpriseInfoBean;
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
 * Date：2018/11/27 0027 16:10
 * Modification  History:
 * Why & What is modified:
 */
public class SupplierManagementActivity extends BaseActivity {
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

    @BindView(R.id.rv_supplier)
    RecyclerView rv_supplier;
    private SupplierListAdapter mSupplierListAdapter;
    private List<EnterpriseInfoBean> mDeleteList = new ArrayList<>();
    private List<EnterpriseInfoBean> mSupplierList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, SupplierManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_supplier_management);
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
                if (mSupplierListAdapter != null) {
                    mSupplierListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加供应商");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(SupplierManagementActivity.this)
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
                                for (EnterpriseInfoBean enterpriseInfoBean : mSupplierList) {
                                    if (enterpriseInfoBean.isChosen()) {
                                        mDeleteList.add(enterpriseInfoBean);
                                    }
                                }
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mSupplierList.contains(mDeleteList.get(i))) {
                                        int position = mSupplierList.indexOf(mDeleteList.get(i));
                                        mSupplierList.remove(mDeleteList.get(i));
                                        if (mSupplierListAdapter != null) {
                                            mSupplierListAdapter.notifyItemRemoved(position);
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
        enterpriseInfoBean.setIcon("1");
        enterpriseInfoBean.setCompany("云茂地产");
        enterpriseInfoBean.setType("科技");
        enterpriseInfoBean.setLocation("上海");
        enterpriseInfoBean.setAccount("企业账号：17861863");
        enterpriseInfoBean.setChosen(false);
        mSupplierList.add(enterpriseInfoBean);

        EnterpriseInfoBean enterpriseInfoBean2 = new EnterpriseInfoBean();
        enterpriseInfoBean2.setIcon("2");
        enterpriseInfoBean2.setCompany("西行设计");
        enterpriseInfoBean2.setType("教育");
        enterpriseInfoBean2.setLocation("沈阳");
        enterpriseInfoBean2.setAccount("企业账号：23536464");
        enterpriseInfoBean.setChosen(false);
        mSupplierList.add(enterpriseInfoBean2);

        EnterpriseInfoBean enterpriseInfoBean3 = new EnterpriseInfoBean();
        enterpriseInfoBean3.setIcon("3");
        enterpriseInfoBean3.setCompany("兴时科技");
        enterpriseInfoBean3.setType("金融服务");
        enterpriseInfoBean3.setLocation("江西");
        enterpriseInfoBean3.setAccount("企业账号：32624626");
        enterpriseInfoBean3.setChosen(false);
        mSupplierList.add(enterpriseInfoBean3);

        setEnterpriseAccountDatas(mSupplierList);
    }

    /**
     * 设置企业账号数据
     *
     * @param enterpriseInfoBeanList
     */
    private void setEnterpriseAccountDatas(final List<EnterpriseInfoBean> enterpriseInfoBeanList) {
        mSupplierListAdapter = new SupplierListAdapter(enterpriseInfoBeanList);
        rv_supplier.setAdapter(mSupplierListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_supplier.setLayoutManager(layoutManager);
        rv_supplier.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_supplier.setHasFixedSize(true);
        rv_supplier.setNestedScrollingEnabled(false);

        mSupplierListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_supplier, position, R.id.cb_choose);
                    mSupplierList.get(position).setChosen(!cb_choose.isChecked());
                    mSupplierListAdapter.notifyDataSetChanged();
                } else {
                    SupplierDetailActivity.startActivity(SupplierManagementActivity.this, mSupplierList.get(position));
                }
            }
        });
    }
}
