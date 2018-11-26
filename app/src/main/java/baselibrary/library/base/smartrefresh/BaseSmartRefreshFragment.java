package baselibrary.library.base.smartrefresh;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.africa.crm.businessmanagement.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.simplesoft.basesimplelibrary.bean.BaseSimpleBean;
import com.simplesoft.basesimplelibrary.http.MyHttpRequestFinishInterface;

import baselibrary.library.base.progress.BaseFragmentProgress;

/**
 * Project：zhangshangjisheng
 * Author:  guojin
 * Version: 1.0.0
 * Description：下拉刷新适用于Fragment
 * Date：2018/1/18 12:16
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseSmartRefreshFragment extends BaseFragmentProgress implements
        MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    protected ListView listView;
    protected int page = 1;
    protected int epage = 20; // 每页显示多少条数据

    protected View mLayoutRootView;

    protected RefreshLayout mRefreshLayout;

    @Override
    public void initView() {
        super.initView();
        initPullToRefreshListView();
    }

    /**
     * 初始化listview
     */
    protected void initPullToRefreshListView() {
        if (mLayoutRootView == null) {
            mLayoutRootView = getFragmentProgress();
            initProgressBar(mLayoutRootView);
            listView = (ListView) mLayoutRootView.findViewById(R.id.list_view);
            listView.setOnItemClickListener(this);
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
    public void onRequestSuccess(String methodName, int statusCode, String msg,
                                 BaseSimpleBean baseSimpleBean) {
        super.onRequestSuccess(methodName, statusCode, msg, baseSimpleBean);
        showListView();
        if (baseSimpleBean == null)
            return;
    }

    /**
     * 显示listview列表
     */
    protected void showListView() {
        if (listView.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 请求页数数据
     *
     * @param page
     */
    public abstract void pullDownRefresh(int page);

    public abstract View getFragmentProgress();
}
