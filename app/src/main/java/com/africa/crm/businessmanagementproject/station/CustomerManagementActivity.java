package com.africa.crm.businessmanagementproject.station;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.africa.crm.businessmanagementproject.R;

import baselibrary.library.base.smartrefresh.BaseSmartRefreshListActivity;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/14 0014 16:39
 * Modification  History:
 * Why & What is modified:
 */
public class CustomerManagementActivity extends BaseSmartRefreshListActivity {

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_customer_management_list);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {

    }

    @Override
    public void refreshListDatas(int page) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
