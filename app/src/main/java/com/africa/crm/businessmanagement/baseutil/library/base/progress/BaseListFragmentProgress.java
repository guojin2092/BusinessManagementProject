package com.africa.crm.businessmanagement.baseutil.library.base.progress;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.africa.crm.businessmanagement.baseutil.library.http.MyHttpRequestFinishInterface;


/**
 * Created by Administrator on 2015/1/12.
 */
public abstract class BaseListFragmentProgress extends BaseFragmentProgress implements View.OnClickListener,
        MyHttpRequestFinishInterface, AdapterView.OnItemClickListener {
    public ListView listview;


}
