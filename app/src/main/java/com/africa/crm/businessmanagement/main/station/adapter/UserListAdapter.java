package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.UserInfoBean;
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
public class UserListAdapter extends BaseQuickAdapter<UserInfoBean, BaseViewHolder> {

    public UserListAdapter(@Nullable List<UserInfoBean> data) {
        super(R.layout.item_user_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfoBean item) {
        ImageView iv_head = helper.getView(R.id.iv_head);
        TextView tv_company = helper.getView(R.id.tv_company);
        TextView tv_role_name = helper.getView(R.id.tv_role_name);

        iv_head.setImageResource(R.drawable.iv_head_icon1);
        if (!TextUtils.isEmpty(item.getCompanyName())) {
            tv_company.setText(item.getCompanyName());
        } else {
            tv_company.setText("暂无");
        }
        tv_role_name.setText(item.getRoleName());
    }
}
