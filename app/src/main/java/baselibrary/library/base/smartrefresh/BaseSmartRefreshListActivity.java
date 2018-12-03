package baselibrary.library.base.smartrefresh;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;
import com.simplesoft.baselibrary.http.MyHttpRequestFinishInterface;

import baselibrary.library.base.progress.BaseActivityProgress;


/**
 * Project：zhangshangjisheng
 * Author:  guojin
 * Version: 1.0.0
 * Description：下拉刷新适用于Activity
 * Date：2018/1/18 9:28
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseSmartRefreshListActivity extends BaseActivityProgress implements MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    protected int page = 1;
    protected int epage = 20; // 每页显示多少条数据

    protected TextView tv_setting_title;

    protected RefreshLayout mRefreshLayout;

    protected ListView listView;


    @Override
    public void initView() {
        super.initView();
        initSmartRefreshListView();
    }

    /**
     * 初始化listview和初始化进度条
     */
    protected void initSmartRefreshListView() {
        tv_setting_title = (TextView) findViewById(R.id.tv_setting_title);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.smart_refresh_layout);

        //自动加载更多
        mRefreshLayout.setEnableAutoLoadmore(true);
        //触发自动刷新
//            mRefreshLayout.autoRefresh();
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
                        refreshListDatas(page);
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
                        refreshListDatas(page);
                    }
                }, 10);
            }
        });

    }

    /**
     * 将listview移动到顶部
     */
    protected void stackFromBottom() {
        if (!listView.isStackFromBottom()) {
            listView.setStackFromBottom(true);
        }
        listView.setStackFromBottom(false);
    }

    @Override
    public void onRequestSuccess(String methodName, int statusCode, String msg,
                                 BaseSimpleBean baseSimpleBean) {
        super.onRequestSuccess(methodName, statusCode, msg, baseSimpleBean);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestFailed(String methodName, int statusCode, String error, BaseSimpleBean baseSimpleBean) {
        super.onRequestFailed(methodName, statusCode, error, baseSimpleBean);
    }


    /**
     * 请求页数数据
     *
     * @param page
     */
    public abstract void refreshListDatas(int page);

}
