package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseActivity;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.PackingDataInfoBean;
import com.africa.crm.businessmanagement.main.station.dialog.AddPackagingDataDialog;
import com.africa.crm.businessmanagement.main.station.dialog.PackagingDataListDialog;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PackagingDataDetailActivity extends BaseActivity {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.titlebar_right)
    TextView titlebar_right;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_preview)
    TextView tv_preview;
    private PackingDataInfoBean mPackingDataInfoBean;

    private AddPackagingDataDialog mAddPackagingDataDialog;
    private PackagingDataListDialog mPackagingDataListDialog;
    private List<DicInfo> mPartList = new ArrayList<>();

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, PackingDataInfoBean packingDataInfoBean) {
        Intent intent = new Intent(activity, PackagingDataDetailActivity.class);
        intent.putExtra("info", packingDataInfoBean);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_packaging_data_detail);
    }

    @Override
    public void initView() {
        mPackingDataInfoBean = (PackingDataInfoBean) getIntent().getSerializableExtra("info");
        titlebar_name.setText("包装数据详情");
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_preview.setOnClickListener(this);
        titlebar_right.setText(R.string.edit);
        mAddPackagingDataDialog = AddPackagingDataDialog.getInstance(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.edit))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_save.setVisibility(View.VISIBLE);
                } else {
                    titlebar_right.setText(R.string.edit);
                    tv_save.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_preview:
                mPackagingDataListDialog.isCancelableOnTouchOutside(false)
                        .withDuration(300)
                        .withEffect(Effectstype.Fadein)
                        .setCancelClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mPackagingDataListDialog.dismiss();
                            }
                        })
                        .setSaveClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mPackagingDataListDialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.ll_add:
                mAddPackagingDataDialog.isCancelableOnTouchOutside(false)
                        .withDuration(300)
                        .withEffect(Effectstype.Fadein)
                        .setCancelClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mAddPackagingDataDialog.dismiss();
                            }
                        })
                        .show();
                mAddPackagingDataDialog.addOnSaveClickListener(new AddPackagingDataDialog.OnSaveClickListener() {
                    @Override
                    public void onSaveClick(DicInfo dicInfo) {
                        if (TextUtils.isEmpty(dicInfo.getText())) {
                            toastMsg("尚未填写模块");
                            return;
                        }
                        if (TextUtils.isEmpty(dicInfo.getCode())) {
                            toastMsg("尚未填写数量");
                            return;
                        }
                        mPartList.add(dicInfo);
                        PackagingDataListDialog.getInstance(PackagingDataDetailActivity.this, mPartList);
                        toastMsg("包装模块添加成功");
                        mAddPackagingDataDialog.dismiss();
                    }
                });
                break;
            case R.id.tv_save:
                showShortToast("保存数据");
                break;
        }
    }

    @Override
    public void initData() {
        mPartList = new ArrayList<>();
        mPartList.add(new DicInfo("A模块", "10"));
        mPartList.add(new DicInfo("B模块", "20"));
        mPartList.add(new DicInfo("C模块", "30"));
        mPackagingDataListDialog = PackagingDataListDialog.getInstance(this, mPartList);
    }

}
