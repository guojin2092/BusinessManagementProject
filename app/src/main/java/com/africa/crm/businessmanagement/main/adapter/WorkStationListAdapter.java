package com.africa.crm.businessmanagement.main.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/14 0014 14:52
 * Modification  History:
 * Why & What is modified:
 */
public class WorkStationListAdapter extends BaseQuickAdapter<WorkStationInfo, BaseViewHolder> {

    public WorkStationListAdapter(@Nullable List<WorkStationInfo> data) {
        super(R.layout.item_work_station_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkStationInfo item) {
        ImageView iv_work_type = helper.getView(R.id.iv_work_type);
        TextView tv_work_type = helper.getView(R.id.tv_work_type);
        int imgType = 0;
        String stringType = "";
        int type = Integer.valueOf(item.getWork_type());

        switch (type) {
            case 1:
                imgType = R.drawable.iv_1;
                stringType = mContext.getString(R.string.enterprise_information_management);
                break;
            case 2:
                imgType = R.drawable.iv_2;
                stringType = mContext.getString(R.string.enterprise_account_management);
                break;
            case 3:
                imgType = R.drawable.iv_3;
                stringType = mContext.getString(R.string.supplier_management);
                break;
            case 4:
                imgType = R.drawable.iv_4;
                stringType = mContext.getString(R.string.product_management);
                break;
            case 5:
                imgType = R.drawable.iv_5;
                stringType = mContext.getString(R.string.customer_management);
                break;
            case 6:
                imgType = R.drawable.iv_6;
                stringType = mContext.getString(R.string.contact_management);
                break;
            case 7:
                imgType = R.drawable.iv_7;
                stringType = mContext.getString(R.string.trading_order_management);
                break;
            case 8:
                imgType = R.drawable.iv_8;
                stringType = mContext.getString(R.string.quotation_management);
                break;
            case 9:
                imgType = R.drawable.iv_9;
                stringType = mContext.getString(R.string.sales_order_management);
                break;
            case 10:
                imgType = R.drawable.iv_10;
                stringType = mContext.getString(R.string.delivery_order_management);
                break;
            case 11:
                imgType = R.drawable.iv_11;
                stringType = mContext.getString(R.string.payment_management);
                break;
            case 12:
                imgType = R.drawable.iv_12;
                stringType = mContext.getString(R.string.service_record_management);
                break;
            case 13:
                imgType = R.drawable.iv_13;
                stringType = mContext.getString(R.string.inventory_management);
                break;
            case 14:
                imgType = R.drawable.iv_14;
                stringType = mContext.getString(R.string.purchasing_management);
                break;
            case 15:
                imgType = R.drawable.iv_15;
                stringType = mContext.getString(R.string.task_management);
                break;
            case 16:
                imgType = R.drawable.iv_16;
                stringType = mContext.getString(R.string.pdf_report_management);
                break;
            case 17:
                imgType = R.drawable.iv_17;
                stringType = mContext.getString(R.string.packaging_data_management);
                break;
            case 18:
                imgType = R.drawable.iv_18;
                stringType = mContext.getString(R.string.statistical_report_forms);
                break;
            case 19:
                imgType = R.drawable.iv_19;
                stringType = mContext.getString(R.string.enterprise_expenditure_management);
                break;
            case 20:
                imgType = R.drawable.iv_20;
                stringType = mContext.getString(R.string.system_management);
                break;
            case 21:
                imgType = R.drawable.iv_21;
                stringType = mContext.getString(R.string.setting);
                break;
        }
        iv_work_type.setImageResource(imgType);
        tv_work_type.setText(stringType);
    }
}
