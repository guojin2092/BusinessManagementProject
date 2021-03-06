package com.africa.crm.businessmanagement.baseutil.library.base.progress;

import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.africa.crm.businessmanagement.R;
import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import com.africa.crm.businessmanagement.baseutil.library.http.MyHttpRequestFinishInterface;
import com.africa.crm.businessmanagement.baseutil.common.util.ToastUtils;


/**
 * Created by Administrator on 2015/1/12.
 */
public abstract class BaseListActivityProgress extends BaseActivityProgress implements View.OnClickListener,
        MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    protected View layoutListView;
    public ListView listView;
    protected FrameLayout progressBar;

    @Override
    public void initView() {
        if (layoutListView == null) {
            layoutListView = getListViewProgress();
            progressBar = (FrameLayout) layoutListView.findViewById(
                    R.id.progressbar_wait);
            listView = (ListView) layoutListView.findViewById(R.id.nest_list_view);
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
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
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
