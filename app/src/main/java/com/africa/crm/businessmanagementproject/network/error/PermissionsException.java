package com.africa.crm.businessmanagementproject.network.error;

import android.Manifest;

/**
 * 作者：warm
 * 时间：2017-10-19 13:57
 * 描述：
 */

public class PermissionsException extends Exception {

    public PermissionsException(String message) {
        super(message);
    }

    public PermissionsException(String... permissions) {
        this(translatePermissions(permissions));
    }

    private static String translatePermissions(String[] permissions) {
        StringBuilder sb = new StringBuilder("缺少");

        switch (permissions[0]) {
            case Manifest.permission.CAMERA:
                sb.append("相机");
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                sb.append("地图定位");
                break;

            case Manifest.permission.CALL_PHONE:
            case Manifest.permission.READ_PHONE_STATE:
                sb.append("拨号");
                break;

            case Manifest.permission.READ_SMS:
            case Manifest.permission.SEND_SMS:
                sb.append("定位");
                break;

            case Manifest.permission.WRITE_CONTACTS:
            case Manifest.permission.GET_ACCOUNTS:
            case Manifest.permission.READ_CONTACTS:
                sb.append("查看联系人");
                break;

            case Manifest.permission.READ_CALENDAR:
            case Manifest.permission.WRITE_CALENDAR:
                sb.append("查看日历");
                break;

            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                sb.append("读取文件");
                break;
        }
        if (permissions.length > 1) {
            sb.append("等");
        }
        sb.append("相关权限");
        return sb.toString();
    }

}
