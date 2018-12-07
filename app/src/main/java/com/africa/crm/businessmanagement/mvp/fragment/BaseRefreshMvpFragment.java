package com.africa.crm.businessmanagement.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseFragment;
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
    protected int rows = 10; // 每页显示多少条数据
    protected RefreshLayout mRefreshLayout;
    protected LinearLayout layout_no_data;
    protected LinearLayout layout_network_error;


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
    }

    /**
     * 初始化listview
     */
    protected void initPullToRefreshListView() {
        if (mLayoutRootView == null) {
            mLayoutRootView = getFragmentProgress();
            layout_no_data = mLayoutRootView.findViewById(R.id.layout_no_data);
            layout_network_error = mLayoutRootView.findViewById(R.id.layout_network_error);
            mRefreshLayout = (RefreshLayout) mLayoutRootView.findViewById(R.id.smart_refresh_layout);
            //自动加载更多
            mRefreshLayout.setEnableAutoLoadmore(true);
            //触发自动刷新
            mRefreshLayout.autoRefresh();
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

    protected abstract P initPresenter();

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
