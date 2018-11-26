package com.africa.crm.businessmanagement.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.station.adapter.RoleListAdapter;
import com.africa.crm.businessmanagement.station.bean.AuthInfoBean;
import com.africa.crm.businessmanagement.station.bean.CostumerInfoBean;
import com.africa.crm.businessmanagement.station.dialog.RoleAuthLimitDialog;
import com.africa.crm.businessmanagement.station.dialog.RoleDetailDialog;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;

import java.util.ArrayList;
import java.util.List;

import baselibrary.common.util.ListUtils;
import baselibrary.library.base.BaseFragment;
import butterknife.BindView;


/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/25 0025 14:53
 * Modification  History:
 * Why & What is modified:
 */
public class RoleManagementFragment extends BaseFragment {
    @BindView(R.id.rv_role)
    RecyclerView rv_role;
    private RoleListAdapter mRoleListAdapter;
    private List<CostumerInfoBean> mRoleList = new ArrayList<>();

    private RoleDetailDialog mRoleDetailDialog;
    private RoleAuthLimitDialog mRoleAuthLimitDialog;

    public static RoleManagementFragment newInstance() {
        RoleManagementFragment roleManagementFragment = new RoleManagementFragment();
        return roleManagementFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_role_management, container, false);
    }

    @Override
    public void initView() {
        mRoleDetailDialog = RoleDetailDialog.getInstance(getActivity());
    }

    @Override
    public void initData() {
        CostumerInfoBean costumerInfoBean = new CostumerInfoBean();
        costumerInfoBean.setIcon("1");
        costumerInfoBean.setCompany("云茂地产");
        costumerInfoBean.setType("科技");
        mRoleList.add(costumerInfoBean);

        CostumerInfoBean costumerInfoBean2 = new CostumerInfoBean();
        costumerInfoBean2.setIcon("2");
        costumerInfoBean2.setCompany("西行设计");
        costumerInfoBean2.setType("教育");
        mRoleList.add(costumerInfoBean2);

        CostumerInfoBean costumerInfoBean3 = new CostumerInfoBean();
        costumerInfoBean3.setIcon("3");
        costumerInfoBean3.setCompany("兴时科技");
        costumerInfoBean3.setType("金融服务");
        mRoleList.add(costumerInfoBean3);

        setCostomerData(mRoleList);

        List<AuthInfoBean> authInfoBeanList = new ArrayList<>();
        AuthInfoBean authInfoBean = new AuthInfoBean();
        authInfoBean.setAuthType("企业信息管理");
        authInfoBeanList.add(authInfoBean);

        AuthInfoBean authInfoBean2 = new AuthInfoBean();
        authInfoBean2.setAuthType("企业账号管理");
        authInfoBeanList.add(authInfoBean2);

        AuthInfoBean authInfoBean3 = new AuthInfoBean();
        authInfoBean3.setAuthType("供应商管理");
        authInfoBeanList.add(authInfoBean3);

        AuthInfoBean authInfoBean4 = new AuthInfoBean();
        authInfoBean4.setAuthType("产品管理");
        authInfoBeanList.add(authInfoBean4);

        AuthInfoBean authInfoBean5 = new AuthInfoBean();
        authInfoBean5.setAuthType("客户管理");
        authInfoBeanList.add(authInfoBean5);

        mRoleAuthLimitDialog = RoleAuthLimitDialog.getInstance(getActivity(), authInfoBeanList);

    }

    /**
     * 设置客户数据
     *
     * @param mCostumerInfoBeanList
     */
    private void setCostomerData(final List<CostumerInfoBean> mCostumerInfoBeanList) {
        if (!ListUtils.isEmpty(mCostumerInfoBeanList)) {
            mRoleListAdapter = new RoleListAdapter(mCostumerInfoBeanList);
            rv_role.setAdapter(mRoleListAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rv_role.setLayoutManager(layoutManager);
            rv_role.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(getActivity(), R.color.F2F2F2)));
            rv_role.setHasFixedSize(true);
            rv_role.setNestedScrollingEnabled(false);

            mRoleListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.tv_see_detail:
                            mRoleDetailDialog.isCancelableOnTouchOutside(false)
                                    .withDuration(300)
                                    .withEffect(Effectstype.Fadein)
                                    .setCancelClick(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            mRoleDetailDialog.dismiss();
                                        }
                                    })
                                    .setSaveClick(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            mRoleDetailDialog.dismiss();
                                        }
                                    })
                                    .show();
                            break;
                        case R.id.tv_auth_allocation:
                            mRoleAuthLimitDialog.isCancelableOnTouchOutside(false)
                                    .withDuration(300)
                                    .withEffect(Effectstype.Fadein)
                                    .setCancelClick(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            mRoleAuthLimitDialog.dismiss();
                                        }
                                    })
                                    .setSaveClick(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            mRoleAuthLimitDialog.dismiss();
                                        }
                                    })
                                    .show();

                            break;
                    }
                }
            });
        }
    }

    @Override
    public void release() {

    }
}
