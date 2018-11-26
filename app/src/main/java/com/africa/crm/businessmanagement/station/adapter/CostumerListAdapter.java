package com.africa.crm.businessmanagement.station.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.station.bean.CostumerInfoBean;
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
public class CostumerListAdapter extends BaseQuickAdapter<CostumerInfoBean, BaseViewHolder> {

    public CostumerListAdapter(@Nullable List<CostumerInfoBean> data) {
        super(R.layout.item_customer_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CostumerInfoBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_company = helper.getView(R.id.tv_company);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_location = helper.getView(R.id.tv_location);

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
        tv_type.setText(item.getType());
        tv_location.setText(item.getLocation());
    }
}
