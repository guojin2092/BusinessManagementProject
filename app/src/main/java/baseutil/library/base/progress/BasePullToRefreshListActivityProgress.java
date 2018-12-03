package baseutil.library.base.progress;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.africa.crm.businessmanagement.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import baseutil.library.http.MyHttpRequestFinishInterface;
import baseutil.library.util.NetUtil;
import baseutil.common.util.ToastUtils;

/**
 * 下拉列表刷新
 *
 * @author Administrator
 */
@SuppressLint("SimpleDateFormat")
public abstract class BasePullToRefreshListActivityProgress extends BaseActivityProgress implements
        MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    protected FrameLayout progressBar;
    protected View LayoutPullListView;
    public PullToRefreshListView pullToRefreshListView;
    protected ListView listView;
    protected int page = 1;
    protected int epage = 10; // 每页显示多少条数据

    @Override
    public void initView() {
        super.initView();
        initPullToRefreshListView();
    }

    /**
     * 初始化listview和初始化进度条
     */
    protected void initPullToRefreshListView() {
        if (LayoutPullListView == null) {
            LayoutPullListView = findViewById(R.id.layout_listview_pulltorefresh_progressbar);
        }
        progressBar = (FrameLayout) LayoutPullListView.findViewById(
                R.id.progressbar_wait);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        pullToRefreshListView = (PullToRefreshListView) LayoutPullListView.findViewById(R.id.pull_to_refresh_listview);
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
                if (NetUtil.isNetAvailable(getApplicationContext())) {
                    page++;
                    pullDownRefresh(page);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pullToRefreshListView.onRefreshComplete();
                        }
                    }, 100);
                    ToastUtils.show(getApplicationContext(), getString(R.string.no_network));
                    return;
                }
            }
        });
        listView = pullToRefreshListView.getRefreshableView();
        listView.setOnItemClickListener(this);
        pullToRefreshListView.setRefreshing();
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
      /*  pullToRefreshListView.setPullToRefreshOverScrollEnabled(true);
        pullToRefreshListView.setScrollingWhileRefreshingEnabled(true);
        pullToRefreshListView.doPullRefreshing(true, 0);*/
        /*pullToRefreshListView.setVisibility(View.INVISIBLE);*/
    }


    @Override
    public void onRequestSuccess(String methodName, int statusCode, String msg,
                                 BaseSimpleBean baseSimpleBean) {
        super.onRequestSuccess(methodName, statusCode, msg, baseSimpleBean);
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            pullToRefreshListView.setVisibility(View.VISIBLE);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshListView.onRefreshComplete();
            }
        }, 100);
    }

    @Override
    public void onRequestFailed(String methodName, int statusCode, String error, BaseSimpleBean baseSimpleBean) {
        super.onRequestFailed(methodName, statusCode, error, baseSimpleBean);
        new Handler().postDelayed(new Runnable() {
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
//        setLastUpdateTime();
    }

    /**
     * 是否显示更多
     *
     * @param isShow
     */
    protected void showMoreData(boolean isShow) {
      /*  pullToRefreshListView.getgetFooterLoadingLayout().show(isShow);
        pullToRefreshListView.setHasMoreData(isShow);*/
    }

    /**
     * 请求页数数据
     *
     * @param page
     */
    public abstract void pullDownRefresh(int page);

}
