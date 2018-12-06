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
import com.africa.crm.businessmanagement.main.bean.PdfInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.station.adapter.PdfReportListAdapter;
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
public class PdfReportManagementActivity extends BaseActivity {
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

    @BindView(R.id.rv_pdf_report)
    RecyclerView rv_pdf_report;
    private PdfReportListAdapter mPdfReportListAdapter;
    private List<PdfInfoBean> mDeleteList = new ArrayList<>();
    private List<PdfInfoBean> mPdfInfoBeanList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, PdfReportManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pdf_report_management);
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
                if (mPdfReportListAdapter != null) {
                    mPdfReportListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                ToastUtils.show(this, "添加任务订单");
                break;
            case R.id.tv_delete:
                new AlertDialog.Builder(PdfReportManagementActivity.this)
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
                                for (PdfInfoBean pdfInfoBean : mPdfInfoBeanList) {
                                    if (pdfInfoBean.isChosen()) {
                                        mDeleteList.add(pdfInfoBean);
                                    }
                                }
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    if (mPdfInfoBeanList.contains(mDeleteList.get(i))) {
                                        int position = mPdfInfoBeanList.indexOf(mDeleteList.get(i));
                                        mPdfInfoBeanList.remove(mDeleteList.get(i));
                                        if (mPdfReportListAdapter != null) {
                                            mPdfReportListAdapter.notifyItemRemoved(position);
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
        PdfInfoBean pdfInfoBean = new PdfInfoBean();
        pdfInfoBean.setName("云茂地产文件");
        pdfInfoBean.setTime("2018-10-18");
        pdfInfoBean.setRemark("第二次修订...");
        pdfInfoBean.setChosen(false);
        mPdfInfoBeanList.add(pdfInfoBean);

        PdfInfoBean pdfInfoBean2 = new PdfInfoBean();
        pdfInfoBean2.setName("西行设计文件");
        pdfInfoBean2.setTime("2018-09-12");
        pdfInfoBean2.setRemark("备注");
        pdfInfoBean2.setChosen(false);
        mPdfInfoBeanList.add(pdfInfoBean2);

        PdfInfoBean pdfInfoBean3 = new PdfInfoBean();
        pdfInfoBean3.setName("兴时科技文件");
        pdfInfoBean3.setTime("2018-08-04");
        pdfInfoBean3.setRemark("第三次修订");
        pdfInfoBean3.setChosen(false);
        mPdfInfoBeanList.add(pdfInfoBean3);

        setPdfReportDatas(mPdfInfoBeanList);
    }

    /**
     * 设置pdf报告数据
     *
     * @param pdfInfoBeans
     */
    private void setPdfReportDatas(final List<PdfInfoBean> pdfInfoBeans) {
        mPdfReportListAdapter = new PdfReportListAdapter(pdfInfoBeans);
        rv_pdf_report.setAdapter(mPdfReportListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_pdf_report.setLayoutManager(layoutManager);
        rv_pdf_report.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_pdf_report.setHasFixedSize(true);
        rv_pdf_report.setNestedScrollingEnabled(false);

        mPdfReportListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_pdf_report, position, R.id.cb_choose);
                    mPdfInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                    mPdfReportListAdapter.notifyDataSetChanged();
                } else {
                    showShortToast("查看PDF文件");
                }
            }
        });
    }
}
