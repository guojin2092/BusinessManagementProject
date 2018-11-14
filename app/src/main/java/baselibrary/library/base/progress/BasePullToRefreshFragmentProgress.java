package baselibrary.library.base.progress;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.africa.crm.businessmanagementproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.simplesoft.basesimplelibrary.bean.BaseSimpleBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import baselibrary.library.http.MyHttpRequestFinishInterface;

/**
 * 下拉刷新
 */
public abstract class BasePullToRefreshFragmentProgress extends BaseFragmentProgress implements
        MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    protected PullToRefreshListView pullToRefreshListView;
    protected ListView listView;
    protected int page = 1;
    protected int epage = 10; // 每页显示多少条数据

    protected View layoutPullListView;

    @Override
    public void initView() {
        super.initView();
        initPullToRefreshListView(getView());
    }

    protected void initPullToRefreshListView(int layoutPullId, int layoutListId, int layoutProgressId) {
    /*    if (layoutPullListView == null) {*/
        layoutPullListView = getActivity().findViewById(layoutPullId);
        initViewListView(layoutPullListView, layoutListId);
        initProgressBar(layoutPullListView, layoutProgressId);
      /*  }*/
    }

    protected void initPullToRefreshListView(View container, int layoutPullId, int layoutListId, int layoutProgressId) {
   /*     if (layoutPullListView == null) {*/
        layoutPullListView = container.findViewById(layoutPullId);
        initViewListView(layoutPullListView, layoutListId);
        initProgressBar(layoutPullListView, layoutProgressId);
      /*  }*/
    }

    /**
     * 初始化listview
     */
    protected void initPullToRefreshListView() {
/*        if (layoutPullListView == null) {*/
        layoutPullListView = getActivity().findViewById(R.id.layout_listview_pulltorefresh_progressbar);
        initViewListView(layoutPullListView, R.id.pull_to_refresh_listview);
        initProgressBar(layoutPullListView, R.id.progressbar_wait);
        /*}*/
    }

    protected void initPullToRefreshListView(View container) {
     /*   if (layoutPullListView == null) {*/
        layoutPullListView = container.findViewById(R.id.layout_listview_pulltorefresh_progressbar);
        initViewListView(layoutPullListView, R.id.pull_to_refresh_listview);
        initProgressBar(layoutPullListView, R.id.progressbar_wait);
        /*}*/
    }

    private void initViewListView(View view, int listViewId) {
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(listViewId);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                page = 1;
                pullDownRefresh(page);
            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                page++;
                pullDownRefresh(page);
            }
        });
        listView = pullToRefreshListView.getRefreshableView();
        listView.setOnItemClickListener(this);
        /*pullToRefreshListView.setLoadingDrawable(getResources().getDrawable(R.drawable.progressbar_wait_white));*/
        /*pullToRefreshListView.getLoadingLayoutProxy().setLoadingDrawable(getResources().getDrawable(R.drawable.progressbar_wait_white));*/
        /*pullToRefreshListView.setShowViewWhileRefreshing(true);*/
        pullToRefreshListView.setRefreshing();
/*        pullToRefreshListView.setScrollingWhileRefreshingEnabled(false);
        pullToRefreshListView.setPullToRefreshOverScrollEnabled(false);*/
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    /*    pullToRefreshListView。*/
     /*   pullToRefreshListView.setPullLoadEnabled(true);
        pullToRefreshListView.setScrollLoadEnabled(true);*/
/*        pullToRefreshListView.doPullRefreshing(true, 0);*/
    }

    @Override
    public void onRequestSuccess(String methodName, int statusCode, String msg,
                                 BaseSimpleBean baseSimpleBean) {
        super.onRequestSuccess(methodName, statusCode, msg, baseSimpleBean);
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            pullToRefreshListView.setVisibility(View.VISIBLE);
        }
        pullToRefreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshListView.onRefreshComplete();
            }
        }, 100);
    }

    /**
     * 设置上次刷新时间
     */
    @SuppressLint("SimpleDateFormat")
    protected void setLastUpdateTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        long time = System.currentTimeMillis();
        String text = mDateFormat.format(new Date(time));
        pullToRefreshListView.setLastUpdatedLabel(text);
    }

    /**
     * 将listview移动到顶部
     */
    protected void stackFromBottom() {
        if (!listView.isStackFromBottom()) {
            listView.setStackFromBottom(true);
        }
        listView.setStackFromBottom(false);
        //设置最后刷新时间
//        setLastUpdateTime();
    }

    /**
     * 是否显示更多
     *
     * @param isShow
     */
    protected void showMoreData(boolean isShow) {
  /*      pullToRefreshListView.getFooterLoadingLayout().show(isShow);
        pullToRefreshListView.setHasMoreData(isShow);*/
    }

    /**
     * 请求页数数据
     *
     * @param page
     */
    public abstract void pullDownRefresh(int page);
}
