package baselibrary.library.base.progress;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.africa.crm.businessmanagement.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import baselibrary.library.http.MyHttpRequestFinishInterface;

public abstract class BasePullToRefreshFragmentProgressViewPager extends BaseFragmentProgress implements
        MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    protected PullToRefreshListView pullToRefreshListView;
    protected ListView listView;
    protected int page = 1;
    protected int epage = 20; // 每页显示多少条数据

    protected View layoutPullListView;

    @Override
    public void initView() {
        super.initView();
        initPullToRefreshListView();
    }

    /**
     * 初始化listview
     */
    protected void initPullToRefreshListView() {
        if (layoutPullListView == null) {
            layoutPullListView = getFragmentProgress();
            initProgressBar(layoutPullListView);

            pullToRefreshListView = (PullToRefreshListView) layoutPullListView.findViewById(R.id.pull_to_refresh_listview);
            pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

                @Override
                public void onPullDownToRefresh(
                        PullToRefreshBase<ListView> refreshView) {
                    page = 1;
                    pullDownRefresh(page);
                }

                @Override
                public void onPullUpToRefresh(
                        PullToRefreshBase<ListView> refreshView) {
                    page++;
                    pullDownRefresh(page);
                }
            });
            listView = pullToRefreshListView.getRefreshableView();
            listView.setOnItemClickListener(this);
            pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            pullToRefreshListView.setPullToRefreshOverScrollEnabled(true);
       /*     pullToRefreshListView.setPullLoadEnabled(false);
            pullToRefreshListView.setScrollLoadEnabled(true);
            pullToRefreshListView.doPullRefreshing(true, 0);*/
            pullToRefreshListView.setVisibility(View.INVISIBLE);
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
        if (pullToRefreshListView.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.GONE);
            pullToRefreshListView.setVisibility(View.VISIBLE);
        }
        pullToRefreshListView.onRefreshComplete();
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
        setLastUpdateTime();
    }

    /**
     * 是否显示更多
     *
     * @param isShow
     */
    protected void showMoreData(boolean isShow) {
/*        pullToRefreshListView.getFooterLoadingLayout().show(isShow);
        pullToRefreshListView.setHasMoreData(isShow);*/
    }

    /**
     * 请求页数数据
     *
     * @param page
     */
    public abstract void pullDownRefresh(int page);

    public abstract View getFragmentProgress();
}
