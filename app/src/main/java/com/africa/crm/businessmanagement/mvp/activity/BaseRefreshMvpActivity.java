package com.africa.crm.businessmanagement.mvp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ToastUtils;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseActivity;
import com.africa.crm.businessmanagement.baseutil.library.http.MyNetworkUtil;
import com.africa.crm.businessmanagement.mvp.presenter.BasePresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 16:43
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseRefreshMvpActivity<P extends BasePresenter> extends BaseActivity {
    @BindView(R.id.smart_refresh_layout)
    protected RefreshLayout mRefreshLayout;
    @BindView(R.id.titlebar_back)
    protected ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    protected TextView titlebar_name;
    @BindView(R.id.titlebar_right)
    protected TextView titlebar_right;
    @BindView(R.id.tv_back)
    protected TextView tv_back;
    @BindView(R.id.tv_refresh)
    protected TextView tv_refresh;
    @BindView(R.id.layout_no_data)
    protected LinearLayout layout_no_data;
    @BindView(R.id.layout_network_error)
    protected LinearLayout layout_network_error;
    @BindView(R.id.tv_load_local)
    protected TextView tv_load_local;

    protected P mPresenter;
    protected int page = 1;
    protected int rows = 20; // 每页显示多少条数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = injectPresenter();
        mPresenter.attach(this);
        initSmartRefreshListView();
        requestData();
    }

    @Override
    public void initView() {
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_refresh.setOnClickListener(this);
        tv_load_local.setOnClickListener(this);
        titlebar_right.setText(R.string.Delete);
    }

    /**
     * 初始化listview和初始化进度条
     */
    protected void initSmartRefreshListView() {
        //自动加载更多
        mRefreshLayout.setEnableAutoLoadmore(true);
        //触发自动刷新
//        mRefreshLayout.autoRefresh();
        //添加头布局
//            mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        //添加尾布局
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));

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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.titlebar_back:
            case R.id.tv_back:
                getBVActivity().onBackPressed();
                break;
            case R.id.tv_refresh:
                if (MyNetworkUtil.isNetworkConnected(getBVActivity())) {
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

    protected abstract P injectPresenter();

    /**
     * 请求初始化数据
     */
    protected abstract void requestData();

    /**
     * 请求页数数据
     *
     * @param page
     */
    public abstract void pullDownRefresh(int page);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}
