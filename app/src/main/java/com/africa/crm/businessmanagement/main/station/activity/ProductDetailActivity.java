package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.ProductInfoBean;

import baseutil.library.base.BaseActivity;
import butterknife.BindView;

public class ProductDetailActivity extends BaseActivity {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.titlebar_right)
    TextView titlebar_right;
    @BindView(R.id.tv_save)
    TextView tv_save;
    private ProductInfoBean mProductInfoBean;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, ProductInfoBean productInfoBean) {
        Intent intent = new Intent(activity, ProductDetailActivity.class);
        intent.putExtra("info", productInfoBean);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_product_detail);
    }

    @Override
    public void initView() {
        mProductInfoBean = (ProductInfoBean) getIntent().getSerializableExtra("info");
        if (mProductInfoBean != null) {
            titlebar_name.setText(mProductInfoBean.getProduct());
        }
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        titlebar_right.setText(R.string.edit);
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
            case R.id.tv_save:
                showShortToast("保存数据");
                break;
        }
    }

    @Override
    public void initData() {

    }
}
