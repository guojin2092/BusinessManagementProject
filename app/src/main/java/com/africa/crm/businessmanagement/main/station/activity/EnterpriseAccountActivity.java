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
import com.africa.crm.businessmanagement.main.station.adapter.EnterpriseAccountListAdapter;
import com.africa.crm.businessmanagement.main.station.bean.EnterpriseInfoBean;
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
 * Date：2018/11/27 0027 9:04
 * Modification  History:
 * Why & What is modified:
 */
public class EnterpriseAccountActivity extends BaseActivity {
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

    @BindView(R.id.rv_enterprise_account)
    RecyclerView rv_enterprise_account;
    private EnterpriseAccountListAdapter mEnterpriseAccountListAdapter;
    private List<EnterpriseInfoBean> mDeleteList = new ArrayList<>();
    private List<EnterpriseInfoBean> mEnterpriseInfoList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, EnterpriseAccountActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_enterprise_account);
    }

    @Override
    public void initView() {
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        titlebar_name.setText(R.string.all_enterprises_account);
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
                if (mEnterpriseAccountListAdapter != null) {
                    mEnterpriseAccountListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加企业账号");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(EnterpriseAccountActivity.this)
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
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mEnterpriseInfoList.contains(mDeleteList.get(i))) {
                                        int position = mEnterpriseInfoList.indexOf(mDeleteList.get(i));
                                        mEnterpriseInfoList.remove(mDeleteList.get(i));
                                        if (mEnterpriseAccountListAdapter != null) {
                                            mEnterpriseAccountListAdapter.notifyItemRemoved(position);
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
        mEnterpriseInfoList.add(enterpriseInfoBean);

        EnterpriseInfoBean enterpriseInfoBean2 = new EnterpriseInfoBean();
        enterpriseInfoBean2.setIcon("2");
        enterpriseInfoBean2.setCompany("西行设计");
        enterpriseInfoBean2.setType("教育");
        enterpriseInfoBean2.setLocation("沈阳");
        enterpriseInfoBean2.setAccount("企业账号：23536464");
        enterpriseInfoBean.setChosen(false);
        mEnterpriseInfoList.add(enterpriseInfoBean2);

        EnterpriseInfoBean enterpriseInfoBean3 = new EnterpriseInfoBean();
        enterpriseInfoBean3.setIcon("3");
        enterpriseInfoBean3.setCompany("兴时科技");
        enterpriseInfoBean3.setType("金融服务");
        enterpriseInfoBean3.setLocation("江西");
        enterpriseInfoBean3.setAccount("企业账号：32624626");
        enterpriseInfoBean3.setChosen(false);
        mEnterpriseInfoList.add(enterpriseInfoBean3);

        setEnterpriseAccountDatas(mEnterpriseInfoList);
    }

    /**
     * 设置企业账号数据
     *
     * @param enterpriseInfoBeanList
     */
    private void setEnterpriseAccountDatas(final List<EnterpriseInfoBean> enterpriseInfoBeanList) {
        mEnterpriseAccountListAdapter = new EnterpriseAccountListAdapter(enterpriseInfoBeanList);
        rv_enterprise_account.setAdapter(mEnterpriseAccountListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_enterprise_account.setLayoutManager(layoutManager);
        rv_enterprise_account.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_enterprise_account.setHasFixedSize(true);
        rv_enterprise_account.setNestedScrollingEnabled(false);

        mEnterpriseAccountListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_enterprise_account, position, R.id.cb_choose);
                    mEnterpriseInfoList.get(position).setChosen(!cb_choose.isChecked());
                    if (mEnterpriseInfoList.get(position).isChosen()) {
                        mDeleteList.add(mEnterpriseInfoList.get(position));
                    }
                    mEnterpriseAccountListAdapter.notifyDataSetChanged();
                } else {
                    EnterpriseAccountDetailActivity.startActivity(EnterpriseAccountActivity.this, mEnterpriseInfoList.get(position));
                }
            }
        });
    }
}
