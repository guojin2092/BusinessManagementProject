package com.africa.crm.businessmanagement.main.station.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPdfEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPdfReportDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPdfReportDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;


public class CompanyPdfReportDetailActivity extends BaseMvpActivity<CompanyPdfReportDetailPresenter> implements CompanyPdfReportDetailContract.View {
    private final static int REQUEST_PICK_FILE = 1001;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.tv_file_name)
    TextView tv_file_name;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private String mPdfDetailId = "";
    private String mCompanyId = "";
    private String mUserId = "";

    private String mFilePath = "";
    private String mFileName = "";
    private String mFileCode = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyPdfReportDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pdf_report_detail);
    }

    @Override
    public void initView() {
        super.initView();
        mPdfDetailId = getIntent().getStringExtra("id");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("PDF文件详情");
        tv_save.setOnClickListener(this);
        tv_file_name.setOnClickListener(this);
        if (!TextUtils.isEmpty(mPdfDetailId)) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected CompanyPdfReportDetailPresenter injectPresenter() {
        return new CompanyPdfReportDetailPresenter();
    }

    @Override
    protected void requestData() {
        if (!TextUtils.isEmpty(mPdfDetailId)) {
            mPresenter.getCompanyPdfDetail(mPdfDetailId);
        }
    }

    @Override
    public void initData() {

    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_title.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.edit))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_save.setVisibility(View.VISIBLE);
                    tv_file_name.setBackground(ContextCompat.getDrawable(this, R.drawable.iv_upload_img));
                    tv_file_name.setText("");
                    setEditTextInput(true);
                } else {
                    titlebar_right.setText(R.string.edit);
                    tv_save.setVisibility(View.GONE);
                    tv_file_name.setBackground(null);
                    tv_file_name.setText(mFileName + ".pdf");
                    setEditTextInput(false);
                }
                break;
            case R.id.tv_file_name:
                if (titlebar_right.getText().toString().equals(getString(R.string.edit))) {
                    toastMsg("查看PDF文件");
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_PICK_FILE);
                        } else {
                            new LFilePicker()
                                    .withActivity(this)
                                    .withRequestCode(REQUEST_PICK_FILE)
                                    .withTitle("文件选择")
                                    .withIconStyle(Constant.ICON_STYLE_BLUE)
                                    .withMutilyMode(false)
                                    .withMaxNum(1)
                                    .withStartPath("/storage/emulated/0")//指定初始显示路径
                                    .withNotFoundBooks("请选择一个文件")
                                    .withIsGreater(false)//过滤文件大小 小于指定大小的文件
                                    .withFileSize(500 * 1024)//指定文件大小为500K
                                    .withChooseMode(true)//文件夹选择模式
                                    .withFileFilter(new String[]{"pdf"})
                                    .start();
                        }
                    }
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_title.getText().toString().trim())) {
                    toastMsg("尚未填写文件名称");
                    return;
                }
                if (TextUtils.isEmpty(mFileCode)) {
                    toastMsg("尚未上传PDF文件");
                    return;
                }
                mPresenter.saveCompanyPdfDetail(mPdfDetailId, mCompanyId, mUserId, et_title.getText().toString().trim(), mFileCode, et_remark.getText().toString().trim());
                break;
        }
    }

    @Override
    public void uploadFile(FileInfoBean fileInfoBean) {
        if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
            mFileCode = fileInfoBean.getCode();
            tv_file_name.setText(mFilePath);
            tv_file_name.setBackground(null);
            tv_file_name.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    @Override
    public void downLoadFile(ResponseBody responseBody) {
        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CRM";
        saveFile(responseBody, fileDir, mFileName);
    }

    @Override
    public void getCompanyPdfDetail(CompanyPdfInfo companyPdfInfo) {
        et_title.setText(companyPdfInfo.getName());
        et_remark.setText(companyPdfInfo.getRemark());
        tv_file_name.setBackground(null);
        mFileName = companyPdfInfo.getName();
        tv_file_name.setText(mFileName + ".pdf");
        mPresenter.downLoadFile(companyPdfInfo.getCode());
    }

    @Override
    public void saveCompanyPdfDetail(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mPdfDetailId)) {
                toastString = "PDF文件创建成功";
            } else {
                toastString = "PDF文件修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyPdfEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_FILE) {
//                String path = data.getStringExtra("path");//文件夹路径
                List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
                if (!ListUtils.isEmpty(list)) {
                    mFilePath = list.get(0);
                    mPresenter.uploadFile(mFilePath);
                } else {
                    toastMsg("尚未选择上传文件");
                }
            }
        }
    }

    public void saveFile(ResponseBody body, String fileDir, String fileName) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(fileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName + ".pdf");
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
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
    }

}
