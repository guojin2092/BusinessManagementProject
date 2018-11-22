package com.africa.crm.businessmanagementproject.network.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.africa.crm.businessmanagementproject.network.error.ComException;


/**
 * 作者: 51hs_android
 * 时间: 2017/3/24
 * 简介:
 */

public interface BaseView {

    void showLoad();

    void showActionLoad();

    void dismissLoad();

    void dismissActionLoad();

    void onTakeException(@NonNull ComException e);

    void onTakeException(boolean action, @NonNull ComException e);

    void reLogin();

    @Deprecated
    void dismissLoad(boolean isSuccess, String showWord);

    Context getContext();

    Activity getActivity();

}
