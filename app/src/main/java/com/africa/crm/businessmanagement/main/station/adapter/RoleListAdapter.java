package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/15 0015 9:56
 * Modification  History:
 * Why & What is modified:
 */
public class RoleListAdapter extends BaseQuickAdapter<RoleInfoBean, BaseViewHolder> {
    public RoleListAdapter(@Nullable List<RoleInfoBean> data) {
        super(R.layout.item_role_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoleInfoBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_company = helper.getView(R.id.tv_company);
        helper.addOnClickListener(R.id.tv_see_detail);
        helper.addOnClickListener(R.id.tv_auth_allocation);
        iv_icon.setImageResource(R.drawable.iv_head_icon1);
        tv_company.setText(item.getRoleName());
    }
}
