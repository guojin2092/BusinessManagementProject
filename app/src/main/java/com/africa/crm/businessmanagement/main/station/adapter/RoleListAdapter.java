package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.station.bean.CostumerInfoBean;
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
public class RoleListAdapter extends BaseQuickAdapter<CostumerInfoBean, BaseViewHolder> {
    public RoleListAdapter(@Nullable List<CostumerInfoBean> data) {
        super(R.layout.item_role_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CostumerInfoBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_company = helper.getView(R.id.tv_company);
        helper.addOnClickListener(R.id.tv_see_detail);
        helper.addOnClickListener(R.id.tv_auth_allocation);
        int icon = Integer.valueOf(item.getIcon());
        int iconSrc = 0;
        switch (icon) {
            case 1:
                iconSrc = R.drawable.iv_head_icon1;
                break;
            case 2:
                iconSrc = R.drawable.iv_head_icon2;
                break;
            case 3:
                iconSrc = R.drawable.iv_head_icon3;
                break;
        }
        iv_icon.setImageResource(iconSrc);
        tv_company.setText(item.getCompany());
    }
}
