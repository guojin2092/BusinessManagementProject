package com.africa.crm.businessmanagement.main.station.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.delete.WebInfoBean;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyStatisticalFormWebContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyStatisticalFormWebPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.retrofit.RetrofitUrlManager;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_COMPANY;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_USERS;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2019/1/15 0015 9:05
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyStatisticalFormWebActivity extends BaseMvpActivity<CompanyStatisticalFormWebPresenter> implements CompanyStatisticalFormWebContract.View {
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_export)
    TextView tv_export;

    @BindView(R.id.spinner_from_company)
    MySpinner spinner_from_company;
    private List<DicInfo> mSpinnerCompanyList = new ArrayList<>();
    private String mFromCompanyId = "";

    @BindView(R.id.spinner_from_user)
    MySpinner spinner_from_user;
    private List<DicInfo> mSpinnerCompanyUserList = new ArrayList<>();
    private String mFromUserId = "";

    @BindView(R.id.spinner_show_delete_data)
    MySpinner spinner_show_delete_data;
    private List<DicInfo> mSpinnerSfList = new ArrayList<>();
    private String mShowDeleteCode = "2";

    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private DicInfo2 mDicInfo;
    private String mRoleCode = "";
    private String mCompanyId = "";
    private String mCompanyName = "";

    private String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CRM";

    private final static String BASE_URL = RetrofitUrlManager.getInstance().getGlobalDomain().toString();

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, DicInfo2 dicInfo) {
        Intent intent = new Intent(activity, CompanyStatisticalFormWebActivity.class);
        intent.putExtra("info", dicInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_statistical_form_web);
    }

    @Override
    public void initView() {
        super.initView();
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.requestFocus();
        mDicInfo = (DicInfo2) getIntent().getSerializableExtra("info");
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_export.setOnClickListener(this);
        if (mDicInfo != null) {
            titlebar_name.setText(mDicInfo.getName());
        }
        if (mRoleCode.equals("root")) {
            spinner_from_company.setVisibility(View.VISIBLE);
            if (!mDicInfo.getCode().equals("5")) {
                spinner_from_user.setVisibility(View.VISIBLE);
            } else {
                spinner_from_user.setVisibility(View.GONE);
            }
        } else if (mRoleCode.equals("companyRoot")) {
            spinner_from_company.setVisibility(View.GONE);
            if (!mDicInfo.getCode().equals("5")) {
                spinner_from_user.setVisibility(View.VISIBLE);
            } else {
                spinner_from_user.setVisibility(View.GONE);
            }
        } else if (mRoleCode.equals("companySales")) {
            spinner_from_company.setVisibility(View.GONE);
            spinner_from_user.setVisibility(View.GONE);
        }
        initTimePicker();
        mSpinnerSfList.clear();
        mSpinnerSfList.add(new DicInfo("是", "1"));
        mSpinnerSfList.add(new DicInfo("否", "2"));
        spinner_show_delete_data.setListDatas(getBVActivity(), mSpinnerSfList);
        spinner_show_delete_data.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mShowDeleteCode = dicInfo.getCode();
            }
        });
    }

    private void initTimePicker() {
        pvStartTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mEndDate != null) {
                    if (mEndDate.getTime() < date.getTime()) {
                        toastMsg("起止时间不得小于起始时间");
                        return;
                    }
                }
                mStartDate = date;
                tv_start_time.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));

        pvEndTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mStartDate != null) {
                    if (date.getTime() < mStartDate.getTime()) {
                        toastMsg("起止时间不得小于起始时间");
                        return;
                    }
                }
                mEndDate = date;
                tv_end_time.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
    }


    @Override
    public void initData() {
    }

    @Override
    protected CompanyStatisticalFormWebPresenter injectPresenter() {
        return new CompanyStatisticalFormWebPresenter();
    }

    @Override
    protected void requestData() {
        if (mRoleCode.equals("root")) {
            mPresenter.getAllCompany(mCompanyName);
            mPresenter.getAllCompanyUsers(mCompanyId);
        } else if (mRoleCode.equals("companyRoot")) {
            mPresenter.getAllCompanyUsers(mCompanyId);
        } else if (mRoleCode.equals("companySales")) {
        }
        if (mDicInfo != null) {
            titlebar_name.setText(mDicInfo.getName());
        }
        ShowStatisticalForm();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                if (mWebview.canGoBack()) {
                    mWebview.goBack();
                }
                onBackPressed();
                break;
            case R.id.tv_start_time:
                if (pvStartTime != null)
                    pvStartTime.show();
                break;
            case R.id.tv_end_time:
                if (pvEndTime != null)
                    pvEndTime.show();
                break;
            case R.id.tv_search:
                ShowStatisticalForm();
                break;
            case R.id.tv_export:
                ExPortStatisticalForm();
                break;
        }
    }

    /**
     * 显示统计图表
     */
    private void ShowStatisticalForm() {
        String code = mDicInfo.getCode();
        if (!TextUtils.isEmpty(code)) {
            switch (code) {
                case "1":
                    mPresenter.getOrderAmountStatisticsReport(StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time), mShowDeleteCode, mFromUserId, mFromCompanyId);
                    break;
                case "2":
                    mPresenter.getOrderNumStatisticsReport(StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time), mShowDeleteCode, mFromUserId, mFromCompanyId);
                    break;
                case "3":
                    mPresenter.getPayRecordStatisticsReport(StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time), mShowDeleteCode, mFromUserId, mFromCompanyId);
                    break;
                case "4":
                    mPresenter.getServiceStatisticsReport(StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time), mShowDeleteCode, mFromUserId, mFromCompanyId);
                    break;
                case "5":
                    mPresenter.getStockStatisticsReport(StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time), mShowDeleteCode, mFromUserId, mFromCompanyId);
                    break;
            }
        }
    }

    /**
     * 数据导出
     */
    private void ExPortStatisticalForm() {
        String code = mDicInfo.getCode();
        if (!TextUtils.isEmpty(code)) {
            switch (code) {
                case "1":
                    mPresenter.getOrderAmountStatisticsExport(StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time), mShowDeleteCode, mFromUserId, mFromCompanyId);
                    break;
                case "2":
                    mPresenter.getOrderNumStatisticsExport(StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time), mShowDeleteCode, mFromUserId, mFromCompanyId);
                    break;
                case "3":
                    mPresenter.getPayRecordStatisticsExport(StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time), mShowDeleteCode, mFromUserId, mFromCompanyId);
                    break;
                case "4":
                    mPresenter.getServiceStatisticsExport(StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time), mShowDeleteCode, mFromUserId, mFromCompanyId);
                    break;
                case "5":
                    mPresenter.getStockStatisticsExport(StringUtil.getText(tv_start_time), StringUtil.getText(tv_end_time), mShowDeleteCode, mFromUserId, mFromCompanyId);
                    break;
            }
        }
    }

    @Override
    public void getAllCompany(List<DicInfo2> dicInfoList) {
        mSpinnerCompanyList.clear();
        if (!ListUtils.isEmpty(dicInfoList)) {
            for (DicInfo2 dicInfo2 : dicInfoList) {
                DicInfo dicInfo = new DicInfo(dicInfo2.getId(), QUERY_ALL_COMPANY, dicInfo2.getName(), dicInfo2.getCode());
                mSpinnerCompanyList.add(dicInfo);
            }
            spinner_from_company.setListDatas(getBVActivity(), mSpinnerCompanyList);
            spinner_from_company.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mFromCompanyId = dicInfo.getCode();
                }
            });
        }
    }

    @Override
    public void getAllCompanyUsers(List<DicInfo2> dicInfo2List) {
        mSpinnerCompanyUserList.clear();
        if (!ListUtils.isEmpty(dicInfo2List)) {
            for (DicInfo2 dicInfo2 : dicInfo2List) {
                DicInfo dicInfo = new DicInfo(dicInfo2.getId(), QUERY_ALL_USERS, dicInfo2.getName(), dicInfo2.getCode());
                mSpinnerCompanyUserList.add(dicInfo);
            }
            spinner_from_user.setListDatas(getBVActivity(), mSpinnerCompanyUserList);
            spinner_from_user.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mFromUserId = dicInfo.getCode();
                }
            });
        }
    }

    @Override
    public void getOrderAmountStatisticsReport(WebInfoBean webInfoBean) {
        if (!TextUtils.isEmpty(webInfoBean.getWebviewurl())) {
            mWebview.loadUrl(BASE_URL + webInfoBean.getWebviewurl());
        }
    }

    @Override
    public void getOrderAmountStatisticsExport(ResponseBody responseBody) {
        saveFileToLocal(responseBody);
    }


    @Override
    public void getOrderNumStatisticsReport(WebInfoBean webInfoBean) {
        if (!TextUtils.isEmpty(webInfoBean.getWebviewurl())) {
            mWebview.loadUrl(BASE_URL + webInfoBean.getWebviewurl());
        }
    }

    @Override
    public void getOrderNumStatisticsExport(ResponseBody responseBody) {
        saveFileToLocal(responseBody);
    }

    @Override
    public void getPayRecordStatisticsReport(WebInfoBean webInfoBean) {
        if (!TextUtils.isEmpty(webInfoBean.getWebviewurl())) {
            mWebview.loadUrl(BASE_URL + webInfoBean.getWebviewurl());
        }
    }

    @Override
    public void getPayRecordStatisticsExport(ResponseBody responseBody) {
        saveFileToLocal(responseBody);
    }

    @Override
    public void getServiceStatisticsReport(WebInfoBean webInfoBean) {
        if (!TextUtils.isEmpty(webInfoBean.getWebviewurl())) {
            mWebview.loadUrl(BASE_URL + webInfoBean.getWebviewurl());
        }
    }

    @Override
    public void getServiceStatisticsExport(ResponseBody responseBody) {
        saveFileToLocal(responseBody);
    }

    @Override
    public void getStockStatisticsReport(WebInfoBean webInfoBean) {
        if (!TextUtils.isEmpty(webInfoBean.getWebviewurl())) {
            mWebview.loadUrl(BASE_URL + webInfoBean.getWebviewurl());
        }
    }

    @Override
    public void getStockStatisticsExport(ResponseBody responseBody) {
        saveFileToLocal(responseBody);
    }

    /**
     * 权限申请
     *
     * @param responseBody
     */
    private void saveFileToLocal(ResponseBody responseBody) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            } else {
                saveFile(responseBody, fileDir, mDicInfo.getName());
            }
        } else {
            saveFile(responseBody, fileDir, mDicInfo.getName());
        }
    }

    /**
     * 保存文件到本地
     *
     * @param body
     * @param fileDir
     * @param fileName
     * @return
     */
    public File saveFile(ResponseBody body, String fileDir, String fileName) {
        InputStream is = null;
        File file = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(fileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(dir, fileName + System.currentTimeMillis() + ".xls");
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            toastMsg("保存成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
        return file;
    }
}
