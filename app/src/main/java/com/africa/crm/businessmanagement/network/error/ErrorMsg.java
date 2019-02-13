package com.africa.crm.businessmanagement.network.error;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 22:15
 * Modification  History:
 * Why & What is modified:
 */
public class ErrorMsg {

    public static String showErrorMsg(String errorType) {
        String errorMsg = "";
        MyApplication mContext = MyApplication.getInstance();
        switch (errorType) {
            case "1":
            case "24":
                errorMsg = mContext.getString(R.string.The_user_does_not_exist);
                break;
            case "2":
                errorMsg = mContext.getString(R.string.The_password_is_wrong);
                break;
            case "3":
                errorMsg = mContext.getString(R.string.The_user_is_disabled);
                break;
            case "11":
                errorMsg = mContext.getString(R.string.The_role_name_cannot_be_empty);
                break;
            case "12":
                errorMsg = mContext.getString(R.string.The_role_coding_cannot_be_empty);
                break;
            case "13":
                errorMsg = mContext.getString(R.string.The_role_classification_cannot_be_empty);
                break;
            case "21":
                errorMsg = mContext.getString(R.string.Parameter_error);
                break;
            case "22":
                errorMsg = mContext.getString(R.string.The_password_cannot_be_less_than_six_bytes);
                break;
            case "23":
                errorMsg = mContext.getString(R.string.The_username_already_exists);
                break;
            case "25":
                errorMsg = mContext.getString(R.string.The_original_password_is_incorrect);
                break;
            case "26":
                errorMsg = mContext.getString(R.string.The_new_password_cannot_be_empty);
                break;
            case "27":
                errorMsg = mContext.getString(R.string.The_new_password_must_be_longer_than_6_bytes);
                break;
            case "101":
                errorMsg = mContext.getString(R.string.No_permission);
                break;
            case "102":
                errorMsg = mContext.getString(R.string.The_serial_number_cannot_be_empty);
                break;
            case "103":
                errorMsg = mContext.getString(R.string.Corresponding_ID_data_does_not_exist);
                break;
            case "201":
                errorMsg = mContext.getString(R.string.Inventory_shortage);
                break;
            case "301":
                errorMsg = mContext.getString(R.string.The_start_date_cannot_be_later_than_the_end_date);
                break;
            case "302":
                errorMsg = mContext.getString(R.string.A_packaging_record_has_already_exists_in_this_time_period);
                break;
            default:
                errorMsg = errorType;
                break;
        }
        return errorMsg;
    }
}
