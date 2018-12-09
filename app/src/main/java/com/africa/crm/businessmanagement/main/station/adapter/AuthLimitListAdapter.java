package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.main.bean.LimitBtnInfo;
import com.africa.crm.businessmanagement.main.bean.RoleLimitInfoBean;
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
public class AuthLimitListAdapter extends BaseQuickAdapter<RoleLimitInfoBean, BaseViewHolder> {
    public AuthLimitListAdapter(@Nullable List<RoleLimitInfoBean> data) {
        super(R.layout.item_role_limit_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoleLimitInfoBean item) {
        CheckBox cb_auth_type = helper.getView(R.id.cb_auth_type);
        TextView tv_auth_type = helper.getView(R.id.tv_auth_type);
        RelativeLayout rl_limit = helper.getView(R.id.rl_limit);
        CheckBox cb_add = helper.getView(R.id.cb_add);
        CheckBox cb_modify = helper.getView(R.id.cb_modify);
        CheckBox cb_delete = helper.getView(R.id.cb_delete);

        /*模块部分*/
        helper.addOnClickListener(R.id.cb_auth_type);
        cb_auth_type.setChecked(item.isChecked());
        tv_auth_type.setText(item.getResName());

        /*按钮部分*/
        helper.addOnClickListener(R.id.cb_add);
        helper.addOnClickListener(R.id.cb_modify);
        helper.addOnClickListener(R.id.cb_delete);
        List<LimitBtnInfo> btnInfoList = item.getBtns();
        if (ListUtils.isEmpty(btnInfoList)) {
            rl_limit.setVisibility(View.INVISIBLE);
        } else {
            rl_limit.setVisibility(View.VISIBLE);
            cb_add.setChecked(btnInfoList.get(0).isChecked());
            cb_modify.setChecked(btnInfoList.get(1).isChecked());
            cb_delete.setChecked(btnInfoList.get(2).isChecked());
        }

    }
}
