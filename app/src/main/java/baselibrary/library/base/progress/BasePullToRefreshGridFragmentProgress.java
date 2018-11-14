package baselibrary.library.base.progress;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.africa.crm.businessmanagementproject.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.simplesoft.basesimplelibrary.bean.BaseSimpleBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import baselibrary.library.http.MyHttpRequestFinishInterface;

public abstract class BasePullToRefreshGridFragmentProgress extends BaseFragmentProgress implements
        MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    protected PullToRefreshGridView pullToRefreshGridView;
    protected GridView gridView;
    protected int page = 1;
    protected int epage = 10; // 每页显示多少条数据

    protected View layoutPullGridView;

    @Override
    public void initView(View layout) {
        initPullToRefreshListView();
    }


    protected void initPullToRefreshListView(int layoutPullId, int layoutListId, int layoutProgressId) {
/*        if (layoutPullGridView == null) {*/
        layoutPullGridView = getActivity().findViewById(layoutPullId);
        initViewListView(layoutPullGridView, layoutListId);
        initProgressBar(layoutPullGridView, layoutProgressId);
     /*   }*/
    }

    protected void initPullToRefreshListView(View container, int layoutPullId, int layoutListId, int layoutProgressId) {
      /*  if (layoutPullGridView == null) {*/
        layoutPullGridView = container.findViewById(layoutPullId);
        initViewListView(layoutPullGridView, layoutListId);
        initProgressBar(layoutPullGridView, layoutProgressId);
      /*  }*/
    }

    /**
     * 初始化listview
     */
    protected void initPullToRefreshListView() {
     /*   if (layoutPullGridView == null) {*/
        layoutPullGridView = getActivity().findViewById(R.id.layout_listview_pulltorefresh_progressbar);
        initViewListView(layoutPullGridView, R.id.pull_to_refresh_listview);
        initProgressBar(layoutPullGridView, R.id.progressbar_wait);
       /* }*/
    }

    private void initViewListView(View view, int listViewId) {
        pullToRefreshGridView = (PullToRefreshGridView) view.findViewById(listViewId);

        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<GridView> refreshView) {
                page = 1;
                pullDownRefresh(page);
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<GridView> refreshView) {
                page++;
                pullDownRefresh(page);
            }
        });
        gridView = pullToRefreshGridView.getRefreshableView();
        gridView.setOnItemClickListener(this);

        /*pullToRefreshListView.setLoadingDrawable(getResources().getDrawable(R.drawable.progressbar_wait_white));*/
        /*pullToRefreshListView.getLoadingLayoutProxy().setLoadingDrawable(getResources().getDrawable(R.drawable.progressbar_wait_white));*/
        /*pullToRefreshListView.setShowViewWhileRefreshing(true);*/

        pullToRefreshGridView.setRefreshing();
        pullToRefreshGridView.setScrollingWhileRefreshingEnabled(true);

        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
    /*    pullToRefreshListView。*/
     /*   pullToRefreshListView.setPullLoadEnabled(true);
        pullToRefreshListView.setScrollLoadEnabled(true);*/
        pullToRefreshGridView.doPullRefreshing(true, 0);
  /*      pullToRefreshGridView.setVisibility(View.INVISIBLE);*/
    }

    @Override
    public void onRequestSuccess(String methodName, int statusCode, String msg,
                                 BaseSimpleBean baseSimpleBean) {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            pullToRefreshGridView.setVisibility(View.VISIBLE);
        }
        pullToRefreshGridView.onRefreshComplete();
    }

    /**
     * 设置上次刷新时间
     */
    @SuppressLint("SimpleDateFormat")
    protected void setLastUpdateTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        long time = System.currentTimeMillis();
        String text = mDateFormat.format(new Date(time));
        pullToRefreshGridView.setLastUpdatedLabel(text);
    }

    /**
     * 将listview移动到顶部
     */
    protected void stackFromBottom() {
        if (!gridView.isStackFromBottom()) {
            gridView.setStackFromBottom(true);
        }
        gridView.setStackFromBottom(false);
        setLastUpdateTime();
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
