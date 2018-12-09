package com.africa.crm.businessmanagement.main.station.adapter;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/8 0008 21:16
 * Modification  History:
 * Why & What is modified:
 */

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * 给下拉框的适配器
 * Created by IBM on 2016/10/25.
 */

public class SpinnerMenuAdapter<T> extends ArrayAdapter {
    //构造方法
    public SpinnerMenuAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
    }

    //复写这个方法，使返回的数据没有最后一项
    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}


