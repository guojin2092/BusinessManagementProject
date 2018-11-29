package com.africa.crm.businessmanagement.main.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.station.activity.CostumerDetailActivity;
import com.africa.crm.businessmanagement.main.station.activity.CostumerManagementActivity;
import com.africa.crm.businessmanagement.main.station.adapter.CostumerListAdapter;
import com.africa.crm.businessmanagement.main.station.bean.CostumerInfoBean;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import baselibrary.common.util.ListUtils;
import baselibrary.common.util.ToastUtils;
import baselibrary.library.base.BaseFragment;
import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/25 0025 15:00
 * Modification  History:
 * Why & What is modified:
 */
public class UserManagementFragment extends BaseFragment {
    @BindView(R.id.rv_costumer)
    RecyclerView rv_costumer;
    private CostumerListAdapter mCostumerListAdapter;
    private List<CostumerInfoBean> mCostumerInfoBeanList = new ArrayList<>();


    public static UserManagementFragment newInstance() {
        UserManagementFragment userManagementFragment = new UserManagementFragment();
        return userManagementFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_management, container, false);
    }

    @Override
    public void initView() {
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
            mCostumerListAdapter = new CostumerListAdapter(mCostumerInfoBeanList);
            rv_costumer.setAdapter(mCostumerListAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rv_costumer.setLayoutManager(layoutManager);
            rv_costumer.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(getActivity(), R.color.F2F2F2)));
            rv_costumer.setHasFixedSize(true);
            rv_costumer.setNestedScrollingEnabled(false);

            mCostumerListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ToastUtils.show(getActivity(), mCostumerInfoBeanList.get(position).getCompany());
                }
            });
        }
    }

    @Override
    public void release() {

    }
}
