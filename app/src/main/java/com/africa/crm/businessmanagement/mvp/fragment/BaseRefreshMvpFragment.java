package com.africa.crm.businessmanagement.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ToastUtils;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseFragment;
import com.africa.crm.businessmanagement.baseutil.library.http.MyNetworkUtil;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 16:51
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseRefreshMvpFragment<P extends IBasePresenter> extends BaseFragment {
    protected P mPresenter;

    protected View mLayoutRootView;

    protected int page = 1;
    protected int rows = 20; // 每页显示多少条数据
    protected RefreshLayout mRefreshLayout;
    protected LinearLayout layout_no_data;
    protected LinearLayout layout_network_error;
    protected TextView tv_back;
    protected TextView tv_refresh;
    protected TextView tv_load_local;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.attach(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPullToRefreshListView();
        requestData();
    }

    /**
     * 初始化listview
     */
    protected void initPullToRefreshListView() {
        if (mLayoutRootView == null) {
            mLayoutRootView = getFragmentProgress();
            layout_no_data = mLayoutRootView.findViewById(R.id.layout_no_data);
            layout_network_error = mLayoutRootView.findViewById(R.id.layout_network_error);
            tv_back = mLayoutRootView.findViewById(R.id.tv_back);
            tv_back.setOnClickListener(this);
            tv_refresh = mLayoutRootView.findViewById(R.id.tv_refresh);
            tv_refresh.setOnClickListener(this);
            tv_load_local = mLayoutRootView.findViewById(R.id.tv_load_local);
            tv_load_local.setOnClickListener(this);
            mRefreshLayout = (RefreshLayout) mLayoutRootView.findViewById(R.id.smart_refresh_layout);
            //自动加载更多
            mRefreshLayout.setEnableAutoLoadmore(true);
            //触发自动刷新
//            mRefreshLayout.autoRefresh();
            //添加头布局
//            mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
            //添加尾布局
            mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(final RefreshLayout refreshlayout) {
                    refreshlayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page = 1;
                            pullDownRefresh(page);
                            refreshlayout.finishRefresh();
                            refreshlayout.resetNoMoreData();
                        }
                    }, 10);
                }
            });
            mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(final RefreshLayout refreshlayout) {
                    refreshlayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page++;
                            pullDownRefresh(page);
                        }
                    }, 10);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_back:
                getBVActivity().onBackPressed();
                break;
            case R.id.tv_refresh:
                if (MyNetworkUtil.isNetworkConnected(getActivity())) {
                    //默认请求第一页
                    page = 1;
                    pullDownRefresh(page);
                } else {
                    toastMsg("网络连接失败，请检查网络是否可用");
                }
                break;
            case R.id.tv_load_local:
                ToastUtils.show(getBVActivity(), "加载本地数据");
                break;
        }
    }

    protected abstract P initPresenter();

    /**
     * 请求初始化数据
     */
    protected abstract void requestData();

    /**
     * 请求页数数据
     *
     * @param page
     */
    protected abstract void pullDownRefresh(int page);

    protected abstract View getFragmentProgress();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
