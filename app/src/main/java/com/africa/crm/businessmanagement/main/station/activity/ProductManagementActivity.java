package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.station.adapter.ProductListAdapter;
import com.africa.crm.businessmanagement.main.bean.ProductInfoBean;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import com.africa.crm.businessmanagement.baseutil.common.util.ToastUtils;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseActivity;
import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class ProductManagementActivity extends BaseActivity {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.titlebar_right)
    TextView titlebar_right;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_delete)
    TextView tv_delete;

    private WorkStationInfo mWorkStationInfo;

    @BindView(R.id.rv_product)
    RecyclerView rv_product;
    private ProductListAdapter mProductListAdapter;
    private List<ProductInfoBean> mDeleteList = new ArrayList<>();
    private List<ProductInfoBean> mProductInfoBeanList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, ProductManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_product_management);

    }

    @Override
    public void initView() {
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        titlebar_right.setText(R.string.delete);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.delete))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_delete.setVisibility(View.VISIBLE);
                    mShowCheckBox = true;
                } else {
                    titlebar_right.setText(R.string.delete);
                    tv_delete.setVisibility(View.GONE);
                    mShowCheckBox = false;
                }
                if (mProductListAdapter != null) {
                    mProductListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加产品");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(ProductManagementActivity.this)
                        .setTitle("温馨提示")
                        .setMessage("是否确认删除？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                for (ProductInfoBean productInfoBean : mProductInfoBeanList) {
                                    if (productInfoBean.isChosen()) {
                                        mDeleteList.add(productInfoBean);
                                    }
                                }
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mProductInfoBeanList.contains(mDeleteList.get(i))) {
                                        int position = mProductInfoBeanList.indexOf(mDeleteList.get(i));
                                        mProductInfoBeanList.remove(mDeleteList.get(i));
                                        if (mProductListAdapter != null) {
                                            mProductListAdapter.notifyItemRemoved(position);
                                        }
                                    }
                                }
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void initData() {
        ProductInfoBean productInfoBean = new ProductInfoBean();
        productInfoBean.setProduct("云茂地产");
        productInfoBean.setType("地产");
        productInfoBean.setLocation("上海");
        productInfoBean.setChosen(false);
        mProductInfoBeanList.add(productInfoBean);

        ProductInfoBean productInfoBean2 = new ProductInfoBean();
        productInfoBean2.setProduct("西行科技");
        productInfoBean2.setType("软件");
        productInfoBean2.setLocation("沈阳");
        productInfoBean2.setChosen(false);
        mProductInfoBeanList.add(productInfoBean2);

        ProductInfoBean productInfoBean3 = new ProductInfoBean();
        productInfoBean3.setProduct("兴时科技");
        productInfoBean3.setType("科技");
        productInfoBean3.setLocation("江西");
        productInfoBean3.setChosen(false);
        mProductInfoBeanList.add(productInfoBean3);

        setProductListDatas(mProductInfoBeanList);
    }

    /**
     * 设置产品管理数据
     *
     * @param productInfoBeanList
     */
    private void setProductListDatas(final List<ProductInfoBean> productInfoBeanList) {
        mProductListAdapter = new ProductListAdapter(productInfoBeanList);
        rv_product.setAdapter(mProductListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_product.setLayoutManager(layoutManager);
        rv_product.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_product.setHasFixedSize(true);
        rv_product.setNestedScrollingEnabled(false);

        mProductListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_product, position, R.id.cb_choose);
                    mProductInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                    mProductListAdapter.notifyDataSetChanged();
                } else {
                    ProductDetailActivity.startActivity(ProductManagementActivity.this, mProductInfoBeanList.get(position));
                }
            }
        });
    }
}
