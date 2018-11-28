package com.africa.crm.businessmanagement.widget;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.africa.crm.businessmanagement.BuildConfig;


/**
 * Created by hkq325800 on 2017/4/14.
 */

public class JumpPermissionManagement {
    /**
     * Build.MANUFACTURER
     */
    private static final String MANUFACTURER_HUAWEI = "Huawei";//华为
    private static final String MANUFACTURER_MEIZU = "Meizu";//魅族
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";//小米
    private static final String MANUFACTURER_SONY = "Sony";//索尼
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_LG = "LG";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_SAMSUNG = "samsung";//三星
    private static final String MANUFACTURER_LETV = "LeMobile";//乐视
    private static final String MANUFACTURER_ZTE = "ZTE";//中兴
    private static final String MANUFACTURER_YULONG = "YuLong";//酷派
    private static final String MANUFACTURER_LENOVO = "LENOVO";//联想

    /**
     * 此函数可以自己定义
     *
     * @param activity
     */
    public static void GoToSetting(Activity activity) {
//        switch (Build.MANUFACTURER){
//            case MANUFACTURER_HUAWEI:
//                Huawei(activity);
//                break;
//            case MANUFACTURER_MEIZU:
//                Meizu(activity);
//                break;
//            case MANUFACTURER_XIAOMI:
//                Xiaomi(activity);
//                break;
//            case MANUFACTURER_SONY:
//                Sony(activity);
//                break;
//            case MANUFACTURER_OPPO:
//                OPPO(activity);
//                break;
//            case MANUFACTURER_LG:
//                LG(activity);
//                break;
//            case MANUFACTURER_LETV:
//                Letv(activity);
//                break;
//            default:
        ApplicationInfo(activity);
//                Log.e("goToSetting", "目前暂不支持此系统");
//                break;
//        }
    }

    public static void Huawei(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void Meizu(Activity activity) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        activity.startActivity(intent);
    }

    public static void Xiaomi(Activity activity) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.setComponent(componentName);
        intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
        activity.startActivity(intent);
    }

    public static void Sony(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void OPPO(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void LG(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    public static void Letv(Activity activity) {

        // Activities=[ActivityRecord{755e1fe u0 com.android.settings/.Settings, isShadow:false t442}, ActivityRecord{2eca046 u0 com.letv.android.letvsafe/.HomeActivity, isShadow:false t442}, ActivityRecord{b325791 u0 com.android.packageinstaller/.permission.ui.ManagePermissionsActivity, isShadow:false t442}, ActivityRecord{e7b3c82 u0 com.android.packageinstaller/.permission.ui.ManagePermissionsActivity, isShadow:false t442}]

        //action=android.intent.action.MANAGE_PERMISSION_APPS
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        //不行
//java.lang.SecurityException: Permission Denial: starting Intent { flg=0x10000000 FromType=0xffffffff mCallingUid=11157 cmp=com.android.packageinstaller/.permission.ui.ManagePermissionsActivity (has extras) } from ProcessRecord{4b3503d 10769:com.gloria.pysy.activity/u0a1157, isShadow:false} (pid=10769, uid=11157) requires android.permission.GRANT_RUNTIME_PERMISSIONS
//        ComponentName comp = new ComponentName("com.android.packageinstaller", "com.android.packageinstaller.permission.ui.ManagePermissionsActivity");
        //可以
//        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.HomeActivity");

        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 只能打开到自带安全软件
     *
     * @param activity
     */
    public static void _360(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    public static void ApplicationInfo(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(localIntent);
    }

    /**
     * 系统设置界面
     *
     * @param activity
     */
    public static void SystemConfig(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }
}
