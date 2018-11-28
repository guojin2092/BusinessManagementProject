package com.africa.crm.businessmanagement.network.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.africa.crm.businessmanagement.network.error.ComException;


/**
 * Created by warm on 17/6/17.
 */

public interface BaseView {

    void onDialogShow();

    void onDialogHide();

    void onTakeException(@NonNull ComException error);

    void reLogin();

    Context getBVContext();

    FragmentActivity getBVActivity();


}
