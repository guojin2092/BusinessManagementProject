package baseutil.library.base.progress;

import android.view.View;
import android.widget.AdapterView;

import com.africa.crm.businessmanagement.R;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import baseutil.library.http.MyHttpRequestFinishInterface;
import baseutil.library.special.view.scrollview.NestListView;


/**
 * Created by Administrator on 2015/1/13.
 */
public abstract class BaseListFragmentViewPager extends BaseFragmentProgress implements View.OnClickListener,
        MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    protected View layoutListView;
    public NestListView listView;

    @Override
    public void initView(View layout) {
        if (layoutListView == null) {
            layoutListView = getListViewProgress();
            initProgressBar(layoutListView);
            listView = (NestListView) layoutListView.findViewById(R.id.nest_list_view);
            listView.setVisibility(View.INVISIBLE);
            listView.setOnItemClickListener(this);
        }
    }

    /**
     * http请求成功
     */
    @Override
    public void onRequestSuccess(String methodName, int statusCode, String msg,
                                 BaseSimpleBean baseSimpleBean) {
        if (baseSimpleBean == null)
            return;
    }

    /**
     * 是否显示listview
     *
     * @param isShowListView
     */
    public void isShowListView(boolean isShowListView) {
        if (isShowListView) {
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    public abstract View getListViewProgress();
}
