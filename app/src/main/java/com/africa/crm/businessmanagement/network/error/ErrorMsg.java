package com.africa.crm.businessmanagement.network.error;

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
        switch (errorType) {
            case "1":
                errorMsg = "用户不存在";
                break;
            case "2":
                errorMsg = "密码错误";
                break;
            case "3":
                errorMsg = "用户被禁用";
                break;
            case "11":
                errorMsg = "角色名不能为空";
                break;
            case "12":
                errorMsg = "角色编码不能为空";
                break;
            case "13":
                errorMsg = "角色分类不能为空";
                break;
            case "21":
                errorMsg = "参数错误";
                break;
            case "22":
                errorMsg = "密码长度不能低于6位";
                break;
            case "23":
                errorMsg = "用户名已存在";
                break;
            case "24":
                errorMsg = "用户不存在";
                break;
            case "25":
                errorMsg = "原密码错误！";
                break;
            case "26":
                errorMsg = "新密码不能为空！";
                break;
            case "27":
                errorMsg = "新密码长度必须大于6位！";
                break;
            case "101":
                errorMsg = "没有权限";
                break;
            case "102":
                errorMsg = "排序号不能为空";
                break;
            case "103":
                errorMsg = "对应ID数据不存在";
                break;
            default:
                errorMsg = errorType;
                break;
        }
        return errorMsg;
    }
}
