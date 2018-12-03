package baseutil.library.base.defined;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.africa.crm.businessmanagement.R;

import baseutil.library.base.bean.UpdateBean;
import baseutil.library.base.progress.BaseActivityProgress;
import baseutil.library.http.MyHttpRequestManager;
import baseutil.library.util.CommonUtil;
import baseutil.library.util.ParseUtils;
import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * 提示更新
 *
 * @author Administrator
 */
public abstract class BaseAppVersionActivity extends BaseActivityProgress {
    private String DWONLOAD_FILENAME = "haloshop.apk";
    public static String UPDATE_FILEPATH;
    private long currentSize;
    private Notification notification;
    private NotificationManager notificationManager;

    /**
     * 请求后台最新的app版本
     */
    protected void requestUpdateVersion(String module) {
//        MyHttpRequestManager.getInstance(this).netGetRequest(module,
//                null, "http://www.halobear.com/api/apps/version2.json?type=67",
//                UpdateBean.class, this);
        RequestBody requestBody = new FormBody.Builder()
                .add("type", "93")
                .build();

        MyHttpRequestManager.getInstance(this).netGetRequest(module,
                requestBody, "http://www.halobear.com/api/apps/version2.json",
                UpdateBean.class, this);
    }

    /**
     * 如果存在最新的版本，则显示最新版本，否则显示取消
     */
    protected void showAppVersionOrCancel(UpdateBean.UpdateData updateData) {
        if (updateData != null) {
            int currentCode = CommonUtil.getVersionCode(this);
            int updateCode = ParseUtils.parseInt(updateData.version);
            if (updateCode > currentCode) {
                showAppVersion(updateData);
            } else {
                MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(this);
                materialDialog.contentColorRes(R.color.app_title_bg_color)
                        .widgetColorRes(R.color.app_title_bg_color)
                        .cancelable(false)
                        .autoDismiss(false)
                        .canceledOnTouchOutside(false)
                        .title(R.string.my_setting_versionupdate_title)
                        .content(R.string.my_setting_versionupdate_latest)
                        .positiveText(R.string.dialog_ok)
                        .neutralText(R.string.cancel)
                        .show();
            }
        }
    }

    /**
     * 如果存在最新的版本，则显示最新的版本
     *
     * @param updateData
     */
    protected void showAppVersionNoCancel(UpdateBean.UpdateData updateData) {
        if (updateData != null) {
            /*int currentCode = ParseUtils.parseInt(AppInfoManager
                    .getStringValue(this,
                            AppInfoManager.APP_CANCEL_UPDATE_VERSION));*/
            String updateCode = updateData.version;
            String currentCode = CommonUtil.getVersionName(this);
            if (updateCode.compareTo(currentCode) > 0) {
                showAppVersion(updateData);
//                cancelUpdateVersion();
            } else {
                cancelUpdateVersion();
            }
        }
    }


    /**
     * 显示最新版本
     *
     * @param updateData
     */
    private void showAppVersion(final UpdateBean.UpdateData updateData) {
        /*  initNotification(getApplicationContext());*/
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(this);
        materialDialog.title("更新提示")
                .content("发现新版本，点击更新")
                .positiveText("前往更新")
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.app_title_bg_color)
                .positiveColorRes(R.color.app_title_bg_color)
                .cancelable(false)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        /*WebViewWebsiteActivity.startActivity(BaseAppVersionActivity.this, updateBean.downurl, "幻熊独角兽");*/
                        Uri uri = Uri.parse(updateData.downurl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        cancelUpdateVersion();
                        /*cancelUpdateVersion();*/
                    }
                }).show();
/*        ibuilder.setNegativeButton(R.string.cancel, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                // 记录取消本次更新
              *//*  AppInfoManager.saveStringValue(BaseAppVersionActivity.this,
                        AppInfoManager.APP_CANCEL_UPDATE_VERSION,
                        updateBean.version);
                cancelUpdateVersion();*//*
            }
        });*/
    }

    public abstract void cancelUpdateVersion();

    /**
     * 初始化通知
     *
     * @param context
     */
/*    private void initNotification(Context context) {
        notification = new Notification(R.drawable.ic_launcher_flower, "开始下载",
                System.currentTimeMillis());
        notification.flags = 4;
        notification.contentView = new RemoteViews(context.getPackageName(),
                R.layout.layout_notification);
        notification.contentView.setTextViewText(
                R.id.nofification_text_download, "0%");
        PendingIntent localPendingIntent = PendingIntent.getActivity(context,
                0, new Intent(""), 0);
        notification.contentIntent = localPendingIntent;
        notificationManager = ((NotificationManager) context
                .getSystemService("notification"));
    }*/
}
