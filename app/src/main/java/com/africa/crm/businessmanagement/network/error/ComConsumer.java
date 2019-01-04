package com.africa.crm.businessmanagement.network.error;

import com.africa.crm.businessmanagement.network.base.BaseView;
import com.africa.crm.businessmanagement.widget.JumpPermissionManagement;
import com.africa.crm.businessmanagement.widget.LogUtil;

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
    private String port;


    public ComConsumer(BaseView view) {
        mView = view;
    }

    public ComConsumer(BaseView view, String port) {
        mView = view;
        this.port = port;
    }

    public BaseView getView() {
        return mView;
    }

    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
        LogUtil.d(throwable.toString());
        if (mView != null) {
            if (throwable instanceof ComException) {
                handle((ComException) throwable);
            } else if (throwable instanceof UnknownHostException) {
                mView.loadLocalData(port);
/*
                handle(new ComException(ComException.HTTP_ERROR, "网络连接异常，查看网络情况", "去设置", new ComException.OnErrorListener() {
                    @Override
                    public void errorAction() {
                        //如果检测到断网情况,可在此处处理,加载本地数据库数据
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        mView.getBVActivity().startActivity(intent);
                    }
                }));
*/
            } else if (throwable instanceof PermissionsException) {
                handle(new ComException(ComException.NO_PERMISSIONS, throwable.getMessage(), "去设置", new ComException.OnErrorListener() {
                    @Override
                    public void errorAction() {
                        JumpPermissionManagement.GoToSetting(mView.getBVActivity());
                    }
                }));
            } else if (throwable instanceof HttpException) {
                handle(new ComException(ComException.HTTP_ERROR, "网络出错，重新加载试试"));
            } else if (throwable instanceof ConnectException) {
                handle(new ComException(ComException.HTTP_ERROR, "网络出错，请检查网络是否可用"));
            } else {
                handle(new ComException(ComException.UNKONW, "未知错误，重新加载试试"));
            }
        }
    }

    public void handle(@NonNull ComException exception) {
        mView.onDialogHide();
        mView.onTakeException(exception);
    }
}
