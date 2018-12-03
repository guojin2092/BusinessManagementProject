package baselibrary.library.base.progress;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.africa.crm.businessmanagement.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import baselibrary.library.http.MyHttpRequestFinishInterface;
import baselibrary.library.util.NetUtil;
import baselibrary.common.util.ToastUtils;

/**
 * 下拉列表刷新
 *
 * @author Administrator
 */
@SuppressLint("SimpleDateFormat")
public abstract class BasePullToRefreshScrollActivity extends BaseActivityProgress implements
        MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    protected PullToRefreshScrollView pullToRefreshScrollView;
    protected int page = 1;
    protected int epage = 10; // 每页显示多少条数据
    protected ListView nestListView;


    @Override
    public void initView() {

        nestListView = (ListView) findViewById(R.id.nest_list_view);
        nestListView.setOnItemClickListener(this);
        initPullToRefreshListView();
    }

    /**
     * 初始化listview和初始化进度条
     */
    protected void initPullToRefreshListView() {
        if (pullToRefreshScrollView == null) {
            pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_to_refresh_scrollView);
        }
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ScrollView> refreshView) {
                page = 1;
                pullDownRefresh(page);
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ScrollView> refreshView) {
                if (NetUtil.isNetAvailable(getApplicationContext())) {
                    page++;
                    pullDownRefresh(page);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pullToRefreshScrollView.onRefreshComplete();
                        }
                    }, 100);
                    ToastUtils.show(getApplicationContext(), getString(R.string.no_network));
                    return;
                }
            }
        });
        pullToRefreshScrollView.setRefreshing();
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshScrollView.setPullToRefreshOverScrollEnabled(false);
        pullToRefreshScrollView.setScrollingWhileRefreshingEnabled(false);
        pullToRefreshScrollView.doPullRefreshing(true, 0);
        /*pullToRefreshListView.setVisibility(View.INVISIBLE);*/
    }


    @Override
    public void onRequestSuccess(String methodName, int statusCode, String msg,
                                 BaseSimpleBean baseSimpleBean) {
        super.onRequestSuccess(methodName, statusCode, msg, baseSimpleBean);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshScrollView.onRefreshComplete();
            }
        }, 100);
    }

    @Override
    public void onRequestFailed(String methodName, int statusCode, String error, BaseSimpleBean baseSimpleBean) {
        if (page == 1) {
            super.onRequestFailed(methodName, statusCode, error, baseSimpleBean);

        } else {
            ToastUtils.show(this, getString(R.string.no_network));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshScrollView.onRefreshComplete();
            }
        }, 100);
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
