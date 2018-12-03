package baselibrary.library.base.progress;

import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.africa.crm.businessmanagement.R;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import baselibrary.library.http.MyHttpRequestFinishInterface;
import baselibrary.library.special.view.scrollview.NestListView;
import baselibrary.common.util.ToastUtils;


/**
 * Created by Administrator on 2015/1/12.
 */
public abstract class BaseNestListActivityProgress extends BaseActivityProgress implements View.OnClickListener,
        MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    protected View layoutListView;
    protected NestListView nestListView;
    protected FrameLayout progressBar;

    @Override
    public void initView() {
        if (layoutListView == null) {
            layoutListView = getListViewProgress();
            progressBar = (FrameLayout) layoutListView.findViewById(
                    R.id.progressbar_wait);
            nestListView = (NestListView) layoutListView.findViewById(R.id.nest_list_view);
            nestListView.setVisibility(View.INVISIBLE);
            nestListView.setOnItemClickListener(this);
        }
    }

    /**
     * http请求成功
     */
    @Override
    public void onRequestSuccess(String methodName, int statusCode, String msg,
                                 BaseSimpleBean baseSimpleBean) {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            nestListView.setVisibility(View.VISIBLE);
        }
        if (statusCode == -1) {
            ToastUtils.show(this, getString(R.string.no_network));
        }
        if (baseSimpleBean == null)
            return;
        canClick();
    }

    public abstract View getListViewProgress();
}
