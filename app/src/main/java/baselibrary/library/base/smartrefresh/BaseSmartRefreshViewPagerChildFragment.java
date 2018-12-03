package baselibrary.library.base.smartrefresh;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.africa.crm.businessmanagement.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.simplesoft.baselibrary.http.MyNetworkUtil;
import com.simplesoft.baselibrary.utils.ToastUtils;

import baselibrary.library.base.progress.BaseFragmentProgress;

/**
 * Project：czbz_project_new
 * Author:  guojin
 * Version: 1.0.0
 * Description：viewpager+fragment组合(主页没有搜索框)[子fragment类继承]
 * Date：2018/1/18 13:56
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseSmartRefreshViewPagerChildFragment extends BaseFragmentProgress implements AdapterView.OnItemClickListener {
    protected int page = 1;
    protected int epage = 20;
    protected ListView listView;

    protected RefreshLayout mRefreshLayout;

    @Override
    public void initView() {
        super.initView();
        getView().findViewById(R.id.btn_net_refresh).setOnClickListener(this);
        listView = (ListView) getView().findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_net_refresh:
                if (MyNetworkUtil.isNetworkConnected(getActivity())) {
                    //默认请求第一页
                    page = 1;
                    refreshIndexData(page, mRefreshLayout);
                } else {
                    ToastUtils.show(getActivity(), "网络没有连接，请检查网络");
                }
                break;
            default:
                break;
        }
    }

    public void refreshData(RefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
        mRefreshLayout.finishRefresh();
        mRefreshLayout.resetNoMoreData();
        page = 1;
        refreshIndexData(page, mRefreshLayout);
    }

    public void loadMore(RefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
        page++;
        loadMoreData(page,mRefreshLayout);
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

    /**
     * 请求首页数据
     */
    protected abstract void refreshIndexData(int firstPage, RefreshLayout refreshLayout);

    /**
     * 加载更多数据
     */
    protected abstract void loadMoreData(int addPage, RefreshLayout refreshLayout);
}
