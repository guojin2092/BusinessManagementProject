package com.africa.crm.businessmanagement.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.africa.crm.businessmanagement.R;
import com.wang.avi.AVLoadingIndicatorView;


public class LoadingDialog extends Dialog {
    boolean isCancelable = true;
    private AVLoadingIndicatorView load;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.AppTheme_Dialog_Transparent);
        setContentView(R.layout.trans_dialog);
        load = (AVLoadingIndicatorView) findViewById(R.id.load);
        setCancelable(isCancelable);
        setCanceledOnTouchOutside(false);
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    @Override
    public void show() {
        super.show();
        if (load != null)
            load.smoothToShow();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (load != null)
            load.smoothToHide();
    }
}
