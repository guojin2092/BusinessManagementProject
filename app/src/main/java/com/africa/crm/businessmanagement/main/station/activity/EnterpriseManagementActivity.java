package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
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
import com.africa.crm.businessmanagement.main.station.adapter.EnterpriseListAdapter;
import com.africa.crm.businessmanagement.main.station.bean.EnterpriseInfoBean;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import baselibrary.common.util.ListUtils;
import baselibrary.common.util.ToastUtils;
import baselibrary.library.base.BaseActivity;
import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/26 0026 16:05
 * Modification  History:
 * Why & What is modified:
 */
public class EnterpriseManagementActivity extends BaseActivity {
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

    @BindView(R.id.rv_enterprise)
    RecyclerView rv_enterprise;
    private EnterpriseListAdapter mEnterpriseListAdapter;
    private List<EnterpriseInfoBean> mEnterpriseInfoList = new ArrayList<>();

    /**
     * @param activity
     */
    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, EnterpriseManagementActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_enterprise_management);
    }

    @Override
    public void initView() {
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        titlebar_name.setText(R.string.all_enterprises);
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
                    if (mEnterpriseListAdapter != null) {
                        mEnterpriseListAdapter.setmIsDeleted(true);
                    }
                } else {
                    titlebar_right.setText(R.string.delete);
                    tv_delete.setVisibility(View.GONE);
                    if (mEnterpriseListAdapter != null) {
                        mEnterpriseListAdapter.setmIsDeleted(false);
                    }
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加企业");
                break;
            case R.id.tv_delete:
                List<EnterpriseInfoBean> list = new ArrayList<>();
                for (int i = 0; i < mEnterpriseInfoList.size(); i++) {
                    if (!mEnterpriseInfoList.get(i).isChosen()) {
                        list.add(mEnterpriseInfoList.get(i));
                    }
                }
                setCostomerData(list);
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
        enterpriseInfoBean.setChosen(false);
        mEnterpriseInfoList.add(enterpriseInfoBean);

        EnterpriseInfoBean enterpriseInfoBean2 = new EnterpriseInfoBean();
        enterpriseInfoBean2.setIcon("2");
        enterpriseInfoBean2.setCompany("西行设计");
        enterpriseInfoBean2.setType("教育");
        enterpriseInfoBean2.setLocation("沈阳");
        enterpriseInfoBean.setChosen(false);
        mEnterpriseInfoList.add(enterpriseInfoBean2);

        EnterpriseInfoBean enterpriseInfoBean3 = new EnterpriseInfoBean();
        enterpriseInfoBean3.setIcon("3");
        enterpriseInfoBean3.setCompany("兴时科技");
        enterpriseInfoBean3.setType("金融服务");
        enterpriseInfoBean3.setLocation("江西");
        enterpriseInfoBean3.setChosen(false);
        mEnterpriseInfoList.add(enterpriseInfoBean3);

        setCostomerData(mEnterpriseInfoList);
    }

    /**
     * 设置客户数据
     *
     * @param enterpriseInfoBeanList
     */
    private void setCostomerData(final List<EnterpriseInfoBean> enterpriseInfoBeanList) {
        if (!ListUtils.isEmpty(enterpriseInfoBeanList)) {
            mEnterpriseListAdapter = new EnterpriseListAdapter(enterpriseInfoBeanList);
            rv_enterprise.setAdapter(mEnterpriseListAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rv_enterprise.setLayoutManager(layoutManager);
            rv_enterprise.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
            rv_enterprise.setHasFixedSize(true);
            rv_enterprise.setNestedScrollingEnabled(false);

            mEnterpriseListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_enterprise, position, R.id.cb_choose);
                    mEnterpriseInfoList.get(position).setChosen(cb_choose.isChecked());
                    mEnterpriseListAdapter.notifyDataSetChanged();
                }
            });

            mEnterpriseListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    EnterpriseDetailActivity.startActivity(EnterpriseManagementActivity.this, mEnterpriseInfoList.get(position));
                }
            });
        }
    }

}
