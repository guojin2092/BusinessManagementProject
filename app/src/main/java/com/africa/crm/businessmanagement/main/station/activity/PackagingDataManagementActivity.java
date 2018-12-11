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
import com.africa.crm.businessmanagement.baseutil.common.util.ToastUtils;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseActivity;
import com.africa.crm.businessmanagement.main.bean.PackingDataInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.station.adapter.PackagingDataListAdapter;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

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
public class PackagingDataManagementActivity extends BaseActivity {
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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PackagingDataListAdapter mPackagingDataListAdapter;
    private List<PackingDataInfoBean> mDeleteList = new ArrayList<>();
    private List<PackingDataInfoBean> mPackingDataInfoBeanList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, PackagingDataManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_management_list);
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
                if (mPackagingDataListAdapter != null) {
                    mPackagingDataListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加包装数据");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(PackagingDataManagementActivity.this)
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
                                for (PackingDataInfoBean packingDataInfoBean : mPackingDataInfoBeanList) {
                                    if (packingDataInfoBean.isChosen()) {
                                        mDeleteList.add(packingDataInfoBean);
                                    }
                                }
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mPackingDataInfoBeanList.contains(mDeleteList.get(i))) {
                                        int position = mPackingDataInfoBeanList.indexOf(mDeleteList.get(i));
                                        mPackingDataInfoBeanList.remove(mDeleteList.get(i));
                                        if (mPackagingDataListAdapter != null) {
                                            mPackagingDataListAdapter.notifyItemRemoved(position);
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
        PackingDataInfoBean packingDataInfoBean = new PackingDataInfoBean();
        packingDataInfoBean.setNum("10个");
        packingDataInfoBean.setStartTime("2018-1-1");
        packingDataInfoBean.setEndTime("2018-2-1");
        packingDataInfoBean.setRemark("备注1");
        packingDataInfoBean.setChosen(false);
        mPackingDataInfoBeanList.add(packingDataInfoBean);

        PackingDataInfoBean packingDataInfoBean2 = new PackingDataInfoBean();
        packingDataInfoBean2.setNum("20个");
        packingDataInfoBean2.setStartTime("2018-3-1");
        packingDataInfoBean2.setEndTime("2018-4-1");
        packingDataInfoBean2.setRemark("备注2");
        packingDataInfoBean2.setChosen(false);
        mPackingDataInfoBeanList.add(packingDataInfoBean2);

        PackingDataInfoBean packingDataInfoBean3 = new PackingDataInfoBean();
        packingDataInfoBean3.setNum("30个");
        packingDataInfoBean3.setStartTime("2018-5-1");
        packingDataInfoBean3.setEndTime("2018-6-1");
        packingDataInfoBean3.setRemark("备注3");
        packingDataInfoBean3.setChosen(false);
        mPackingDataInfoBeanList.add(packingDataInfoBean3);

        setPackagingDatas(mPackingDataInfoBeanList);
    }

    /**
     * 设置包装数据
     *
     * @param packingDataInfoBeans
     */
    private void setPackagingDatas(final List<PackingDataInfoBean> packingDataInfoBeans) {
        mPackagingDataListAdapter = new PackagingDataListAdapter(packingDataInfoBeans);
        recyclerView.setAdapter(mPackagingDataListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        mPackagingDataListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                    mPackingDataInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                    mPackagingDataListAdapter.notifyDataSetChanged();
                } else {
                    PackagingDataDetailActivity.startActivity(PackagingDataManagementActivity.this, mPackingDataInfoBeanList.get(position));
                }
            }
        });
    }
}
