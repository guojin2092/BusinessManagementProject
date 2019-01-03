package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyContactEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.CompanyContactDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyContactPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.MySpinner;

import org.greenrobot.eventbus.EventBus;

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
public class CompanyContactDetailActivity extends BaseMvpActivity<CompanyContactPresenter> implements CompanyContactDetailContract.View, SinglePopup.OnPopItemClickListener, CameraCore.CameraResult {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_add_icon)
    TextView tv_add_icon;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_job)
    EditText et_job;
    @BindView(R.id.et_from_company_name)
    EditText et_from_company_name;
    @BindView(R.id.et_telephone)
    EditText et_telephone;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_zip_address)
    EditText et_zip_address;
    @BindView(R.id.et_remark)
    EditText et_remark;

    @BindView(R.id.spinner_from_type)
    MySpinner spinner_from_type;
    private static final String CONTACT_FROM_TYPE = "CONTACTFROMTYPE";
    private List<DicInfo> mSpinnerFromTypeList = new ArrayList<>();
    private String mFromType = "";

    @BindView(R.id.ll_from_user)
    LinearLayout ll_from_user;
    @BindView(R.id.spinner_from_user)
    MySpinner spinner_from_user;
    private List<DicInfo> mSpinnerCompanyUserList = new ArrayList<>();
    private String mFromUserId = "";

    private String mRoleCode = "";//角色code
    private String mContactId = "";//联系人ID
    private String mCompanyId = "";

    private CameraCore cameraCore;
    private SinglePopup singlePopup;
    private String mHeadCode = "";//头像ID


    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyContactDetailActivity.class);
        intent.putExtra("contactId", id);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_contact_detail);
    }

    @Override
    protected CompanyContactPresenter injectPresenter() {
        return new CompanyContactPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mContactId = getIntent().getStringExtra("contactId");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        titlebar_name.setText("联系人详情");
        tv_add_icon.setOnClickListener(this);
        tv_save.setOnClickListener(this);

        if (!TextUtils.isEmpty(mContactId)) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        }
        cameraCore = new CameraCore.Builder(this)
                .setNeedCrop(true)
                .setZipInfo(new CameraCore.ZipInfo(true, 200, 200, 100 * 1024))
                .build();
    }


    @Override
    public void initData() {
    }

    @Override
    protected void requestData() {
        if ("companyRoot".equals(mRoleCode)) {
            ll_from_user.setVisibility(View.VISIBLE);
            mPresenter.getAllCompanyUsers(mCompanyId);
        } else {
            ll_from_user.setVisibility(View.GONE);
        }
        mPresenter.getFromType(CONTACT_FROM_TYPE);
        if (!TextUtils.isEmpty(mContactId)) {
            mPresenter.getContactDetail(mContactId);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_add_icon:
                if (singlePopup == null) {
                    List<String> list = new ArrayList<>();
                    list.add("拍照");
                    list.add("从相册选择");
                    singlePopup = new SinglePopup(this, list, this);
                    singlePopup.setTitle(View.GONE, "选择来源");
                }
                singlePopup.showAtLocation(tv_add_icon, Gravity.BOTTOM, 0, 0);
                break;
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
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                    toastMsg("尚未填写姓名");
                    return;
                }
                String userId = "";
                if (mRoleCode.equals("companySales")) {
                    userId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
                } else {
                    userId = mFromUserId;
                }
                mPresenter.saveCompanyContact(mContactId, mCompanyId, userId, mHeadCode, et_name.getText().toString().trim(), mFromType, et_address.getText().toString().trim(), et_zip_address.getText().toString().trim(), et_telephone.getText().toString().trim(), et_phone.getText().toString().trim(), et_email.getText().toString().trim(), et_job.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }


    @Override
    public void getAllCompanyUsers(List<DicInfo2> dicInfo2List) {
        mSpinnerCompanyUserList.clear();
        if (!ListUtils.isEmpty(dicInfo2List)) {
            for (DicInfo2 dicInfo2 : dicInfo2List) {
                DicInfo dicInfo = new DicInfo(dicInfo2.getName(), dicInfo2.getId());
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
    public void getFromType(List<DicInfo> dicInfoList) {
        mSpinnerFromTypeList.clear();
        mSpinnerFromTypeList.addAll(dicInfoList);
        spinner_from_type.setListDatas(this, mSpinnerFromTypeList);

        spinner_from_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mFromType = dicInfo.getCode();
            }
        });
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        tv_add_icon.setEnabled(canInput);
        et_name.setEnabled(canInput);
        et_job.setEnabled(canInput);
        et_from_company_name.setEnabled(canInput);
        spinner_from_user.getTextView().setEnabled(canInput);
        spinner_from_type.getTextView().setEnabled(canInput);
        et_telephone.setEnabled(canInput);
        et_phone.setEnabled(canInput);
        et_email.setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_zip_address.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void getContactDetail(CompanyContactInfo companyContactInfo) {
        if (companyContactInfo != null) {
            et_name.setText(companyContactInfo.getName());
            et_job.setText(companyContactInfo.getJob());
            et_from_company_name.setText(companyContactInfo.getCompanyName());
            spinner_from_user.setText(companyContactInfo.getUserNickName());
            mFromUserId = companyContactInfo.getUserId();
            spinner_from_type.setText(companyContactInfo.getFromTypeName());
            mFromType = companyContactInfo.getFromType();
            et_telephone.setText(companyContactInfo.getPhone());
            et_phone.setText(companyContactInfo.getTel());
            et_email.setText(companyContactInfo.getEmail());
            et_address.setText(companyContactInfo.getAddress());
            et_zip_address.setText(companyContactInfo.getMailAddress());
            et_remark.setText(companyContactInfo.getRemark());
            mHeadCode = companyContactInfo.getHead();
            if (!TextUtils.isEmpty(mHeadCode)) {
                GlideUtil.showImg(iv_icon, mHeadCode);
            }
        }
    }

    @Override
    public void saveCompanyContact(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mContactId)) {
                toastString = "企业联系人创建成功";
            } else {
                toastString = "企业联系人修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyContactEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }

    @Override
    public void itemClick(int position, String s) {
        switch (position) {
            case 0:
                cameraCore.openCamera();
                break;
            case 1:
                cameraCore.openAlbum();
                break;
        }
    }

    @Override
    public void success(String path) {
        if (!TextUtils.isEmpty(path)) {
            mPresenter.uploadImages(path);
        }
    }

    @Override
    public void uploadImages(FileInfoBean fileInfoBean) {
        if (!TextUtils.isEmpty(fileInfoBean.getCode())) {
            mHeadCode = fileInfoBean.getCode();
            if (!TextUtils.isEmpty(mHeadCode)) {
                GlideUtil.showImg(iv_icon, mHeadCode);
            }
        }
    }

    @Override
    public void fail(int code, String message) {
        toastMsg(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraCore.onResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraCore.onPermission(requestCode, permissions, grantResults);
    }
}
