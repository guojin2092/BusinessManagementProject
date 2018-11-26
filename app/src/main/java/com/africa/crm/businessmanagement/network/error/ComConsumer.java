package com.africa.crm.businessmanagement.network.error;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.africa.crm.businessmanagement.network.base.BaseView;

import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * 作者: 51hs_android
 * 时间: 2017/7/27
 * 简介:
 */

public class ComConsumer implements Consumer<Throwable> {

    private BaseView mView;
    private boolean action;


    public ComConsumer(BaseView view) {
        this(view, false);
    }

    public ComConsumer(BaseView view, boolean action) {
        mView = view;
        this.action = action;
    }

    public BaseView getView() {
        return mView;
    }

    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
        if (mView != null) {
            if (action) {
                mView.dismissActionLoad();
            } else {
                mView.dismissLoad();
            }
            if (throwable instanceof ComException) {
                handle((ComException) throwable);
            } else if (throwable instanceof UnknownHostException) {
                handle(new ComException(ComException.HTTP_ERROR, "网络连接异常，查看网络情况", "去设置", new ComException.OnErrorListener() {
                    @Override
                    public void errorAction() {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        mView.getActivity().startActivity(intent);
                    }
                }));
            } else if (throwable instanceof PermissionsException) {
                handle(new ComException(ComException.NO_PERMISSIONS, throwable.getMessage(), "去设置", new ComException.OnErrorListener() {
                    @Override
                    public void errorAction() {
                        goApplicationInfo(mView.getActivity());
                    }
                }));
            } else if (throwable instanceof HttpException) {
                int responseCode = ((HttpException) throwable).code();
                if (responseCode == 415) {
                    mView.reLogin();
                } else {
                    handle(new ComException(ComException.HTTP_ERROR, "网络出错，重新加载试试"));
                }
            } else if (throwable instanceof ConnectException) {
                handle(new ComException(ComException.HTTP_ERROR, "网络出错，请检查网络是否可用"));
            } else {
                mView.onTakeException(new ComException(ComException.UNKONW, "未知错误，重新加载试试"));
            }
        }
    }

    public void handle(@NonNull ComException exception) {
        mView.onTakeException(action, exception);
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    public static void goApplicationInfo(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(localIntent);
    }
}
