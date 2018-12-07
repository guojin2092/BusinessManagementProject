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
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.baseutil.common.util.ToastUtils;
import com.africa.crm.businessmanagement.main.bean.UserInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.adapter.UserListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.UserManagementContract;
import com.africa.crm.businessmanagement.main.station.presenter.UserManagementPresenter;
import com.africa.crm.businessmanagement.mvp.fragment.BaseRefreshMvpFragment;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

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
public class UserManagementFragment extends BaseRefreshMvpFragment<UserManagementPresenter> implements UserManagementContract.View {
    private View mRootView;
    @BindView(R.id.rv_costumer)
    RecyclerView rv_costumer;
    private UserListAdapter mUserListAdapter;
    private List<UserInfoBean> mUserInfoBeanList = new ArrayList<>();


    public static UserManagementFragment newInstance() {
        UserManagementFragment userManagementFragment = new UserManagementFragment();
        return userManagementFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_user_management, container, false);
        return mRootView;
    }

    @Override
    protected View getFragmentProgress() {
        return mRootView;
    }

    @Override
    public void initView() {
        mUserListAdapter = new UserListAdapter(mUserInfoBeanList);
        rv_costumer.setAdapter(mUserListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_costumer.setLayoutManager(layoutManager);
        rv_costumer.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(getActivity(), R.color.F2F2F2)));
        rv_costumer.setHasFixedSize(true);
        rv_costumer.setNestedScrollingEnabled(false);

        if (!ListUtils.isEmpty(mUserInfoBeanList)) {
            mUserListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ToastUtils.show(getActivity(), mUserInfoBeanList.get(position).getRoleName());
                }
            });
        }
    }

    @Override
    protected UserManagementPresenter initPresenter() {
        return new UserManagementPresenter();
    }

    @Override
    protected void pullDownRefresh(int page) {
        mPresenter.getUserList(page, rows, "", "", "", "");
    }

    @Override
    public void getUserList(UserManagementInfoBean userManagementInfoBean) {
        if (page == 1) {
            if (ListUtils.isEmpty(userManagementInfoBean.getRows())) {
                layout_no_data.setVisibility(View.VISIBLE);
                rv_costumer.setVisibility(View.GONE);
                return;
            } else {
                layout_no_data.setVisibility(View.GONE);
                rv_costumer.setVisibility(View.VISIBLE);
            }
            mUserInfoBeanList.clear();
            rv_costumer.smoothScrollToPosition(0);
        }
        if (mRefreshLayout != null) {
            if (ListUtils.isEmpty(userManagementInfoBean.getRows()) && page > 1) {
                mRefreshLayout.finishLoadmoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadmore();
            }
        }
        if (!ListUtils.isEmpty(userManagementInfoBean.getRows())) {
            mUserInfoBeanList.addAll(userManagementInfoBean.getRows());
            if (mUserListAdapter != null) {
                mUserListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void initData() {

    }


    @Override
    public void release() {

    }

}
