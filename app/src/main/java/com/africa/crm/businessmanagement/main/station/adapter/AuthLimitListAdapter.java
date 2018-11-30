package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.AuthInfoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/25 0025 18:29
 * Modification  History:
 * Why & What is modified:
 */
public class AuthLimitListAdapter extends BaseQuickAdapter<AuthInfoBean, BaseViewHolder> {
    public AuthLimitListAdapter(@Nullable List<AuthInfoBean> data) {
        super(R.layout.item_role_limit_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuthInfoBean item) {
        TextView tv_auth_type = helper.getView(R.id.tv_auth_type);
        CheckBox cb_auth_type = helper.getView(R.id.cb_auth_type);
        CheckBox cb_add = helper.getView(R.id.cb_add);
        CheckBox cb_delete = helper.getView(R.id.cb_delete);
        CheckBox cb_check = helper.getView(R.id.cb_check);

        tv_auth_type.setText(item.getAuthType());
    }
}
