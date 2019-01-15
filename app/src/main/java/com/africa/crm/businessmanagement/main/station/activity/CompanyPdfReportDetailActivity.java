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

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPdfEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyPdfInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPdfReportDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPdfReportDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PDF_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DOWNLOAD_PDF_FILE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_PDF;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_PDF_FILE;


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
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mCompanyName = "";
    private String mFromName = "";
    private String mUserId = "";

    private String mFilePath = "";
    private String mFileName = "";
    private String mFileCode = "";
    private String mLocalPath = "";

    private String mEditAble = "1";

    private GreendaoManager<CompanyPdfInfo, CompanyPdfInfoDao> mPdfInfoDaoManager;
    private List<CompanyPdfInfo> mCompanyPdfLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanyPdfReportDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("localId", localId);
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
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        mFromName = UserInfoManager.getUserLoginInfo(this).getName();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("PDF文件详情");
        tv_save.setOnClickListener(this);
        tv_file_name.setOnClickListener(this);
        if (TextUtils.isEmpty(mPdfDetailId) && mLocalId == 0l) {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mPdfDetailId) || mLocalId != 0l) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        }
        //得到Dao对象管理器
        mPdfInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyPdfInfoDao());
        //得到本地数据
        mCompanyPdfLocalList = mPdfInfoDaoManager.queryAll();
    }

    @Override
    protected CompanyPdfReportDetailPresenter injectPresenter() {
        return new CompanyPdfReportDetailPresenter();
    }

    @Override
    protected void requestData() {
        if (!TextUtils.isEmpty(mPdfDetailId) || mLocalId != 0l) {
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
                    setEditTextInput(true);
                } else {
                    titlebar_right.setText(R.string.edit);
                    tv_save.setVisibility(View.GONE);
                    setEditTextInput(false);
                }
                break;
            case R.id.tv_file_name:
                if (titlebar_right.getText().toString().equals(getString(R.string.edit))) {
                    if (mLocalPath.contains(".pdf")) {
                        openOtherFile(mLocalPath);
                    }
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
    public void downLoadFile(ResponseBody responseBody, boolean isLocal) {
        if (!isLocal) {
            String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CRM";
            File file = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    file = saveFile(responseBody, fileDir, mFileName);
                }
            } else {
                file = saveFile(responseBody, fileDir, mFileName);
            }
            if (file != null) {
                mLocalPath = file.getAbsolutePath();
            }
        } else {
            mLocalPath = mFileCode;
        }
    }

    @Override
    public void getCompanyPdfDetail(CompanyPdfInfo companyPdfInfo) {
        if (companyPdfInfo != null) {
            et_title.setText(companyPdfInfo.getName());
            et_remark.setText(companyPdfInfo.getRemark());
            tv_file_name.setBackground(null);
            mFileName = companyPdfInfo.getName();
            tv_file_name.setText(mFileName + ".pdf");
            mPresenter.downLoadFile(companyPdfInfo.getCode());
            mEditAble = companyPdfInfo.getEditAble();
            mCompanyName = companyPdfInfo.getCompanyName();
            mFileCode = companyPdfInfo.getCode();
            for (CompanyPdfInfo localInfo : mCompanyPdfLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyPdfInfo.getId())) {
                    if (localInfo.getId().equals(companyPdfInfo.getId())) {
                        companyPdfInfo.setLocalId(localInfo.getLocalId());
                        mPdfInfoDaoManager.correct(companyPdfInfo);
                    }
                }
            }
        }

    }

    @Override
    public void saveCompanyPdfDetail(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mPdfDetailId) && mLocalId == 0l) {
            toastString = "PDF文件创建成功";
        } else {
            toastString = "PDF文件修改成功";
        }
        if (isLocal) {
            CompanyPdfInfo companyPdfInfo = null;
            if (mLocalId == 0l) {
                companyPdfInfo = new CompanyPdfInfo(mPdfDetailId, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_remark), mEditAble, StringUtil.getText(et_title), mUserId, mFileCode, mCompanyId, mCompanyName, mFromName, false, isLocal);
                mPdfInfoDaoManager.insertOrReplace(companyPdfInfo);
            } else {
                for (CompanyPdfInfo info : mCompanyPdfLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyPdfInfo = new CompanyPdfInfo(info.getLocalId(), mPdfDetailId, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_remark), mEditAble, StringUtil.getText(et_title), mUserId, mFileCode, mCompanyId, mCompanyName, mFromName, false, isLocal);
                        mPdfInfoDaoManager.correct(companyPdfInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyPdfEvent(toastString));
        finish();
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
            file = new File(dir, fileName + ".pdf");
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
        return file;
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_UPLOAD_PDF_FILE:
                FileInfoBean localInfoBean = new FileInfoBean(mFilePath);
                uploadFile(localInfoBean);
                break;
            case REQUEST_DOWNLOAD_PDF_FILE:
                downLoadFile(null, true);
                break;
            case REQUEST_COMPANY_PDF_DETAIL:
                CompanyPdfInfo companyPdfInfo = null;
                for (CompanyPdfInfo info : mCompanyPdfLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyPdfInfo = info;
                    }
                }
                getCompanyPdfDetail(companyPdfInfo);
                break;
            case REQUEST_SAVE_COMPANY_PDF:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mPdfDetailId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyPdfDetail(uploadInfoBean, true);
                break;
        }
    }

    private void openOtherFile(String path) {
      /*  QbSdk.canOpenFile(this, path, new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean aBoolean) {
                Log.e(TAG, "文件是否能够打开:" + aBoolean);
            }
        });*/
        HashMap<String, String> params = new HashMap<>();
        //“0”表示文件查看器使用默认的UI 样式。“1”表示文件查看器使用微信的UI 样式。不设置此key或设置错误值，则为默认UI 样式。
        params.put("style", "0");
        //“true”表示是进入文件查看器，如果不设置或设置为“false”，则进入miniqb 浏览器模式。不是必须设置项
        params.put("local", "false");
        //定制文件查看器的顶部栏背景色。格式为“#xxxxxx”，例“#2CFC47”;不设置此key 或设置错误值，则为默认UI 样式。
        params.put("topBarBgColor", "#516996");
        QbSdk.openFileReader(this, path, params, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
            }
        });
    }

}
