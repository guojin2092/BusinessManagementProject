package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyProductEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyProductInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyProductDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyProductDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.DicUtil.PRODUCT_TYPE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_SUPPLIERS;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_SUPPLIER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PRODUCT_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_PRODUCT_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_PRODUCT;

public class CompanyProductDetailActivity extends BaseMvpActivity<CompanyProductDetailPresenter> implements CompanyProductDetailContract.View {
    @BindView(R.id.et_product_name)
    EditText et_product_name;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_maker_name)
    EditText et_maker_name;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.et_unit)
    EditText et_unit;
    @BindView(R.id.et_inventory_num)
    EditText et_inventory_num;
    @BindView(R.id.et_warn_num)
    EditText et_warn_num;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.spinner_supplier_name)
    MySpinner spinner_supplier_name;

    @BindView(R.id.spinner_product_type)
    MySpinner spinner_product_type;
    private List<DicInfo> mSpinnerProductList = new ArrayList<>();
    private String mProductType = "";

    private String mProductId = "";
    private String mCompanyId = "";
    private String mCompanyName = "";
    private Long mLocalId = 0l;//本地数据库ID

    private GreendaoManager<CompanyProductInfo, CompanyProductInfoDao> mProductInfoDaoManager;
    private List<CompanyProductInfo> mProductInfoLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String productId, Long localId) {
        Intent intent = new Intent(activity, CompanyProductDetailActivity.class);
        intent.putExtra("productId", productId);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_product_detail);
    }

    @Override
    protected CompanyProductDetailPresenter injectPresenter() {
        return new CompanyProductDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mProductId = getIntent().getStringExtra("productId");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        mLocalId = getIntent().getLongExtra("localId", 0l);
        titlebar_name.setText("产品详情");
        tv_save.setOnClickListener(this);

        if (TextUtils.isEmpty(mProductId) && mLocalId == 0l) {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
            et_inventory_num.setEnabled(true);
        } else if (!TextUtils.isEmpty(mProductId) || mLocalId != 0l) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
            et_inventory_num.setEnabled(false);
        }
        //得到Dao对象管理器
        mProductInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyProductInfoDao());
        //得到本地数据
        mProductInfoLocalList = mProductInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void requestData() {
        mPresenter.getAllSuppliers(mCompanyId);
        mPresenter.getProductType(PRODUCT_TYPE);
        if (!TextUtils.isEmpty(mProductId) || mLocalId != 0l) {
            mPresenter.getCompanyProductDetail(mProductId);
        }
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
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_product_name.getText().toString().trim())) {
                    toastMsg("尚未填写产品名称");
                    return;
                }
                mPresenter.saveCompanyProduct(mProductId, mCompanyId, et_product_name.getText().toString().trim(), et_code.getText().toString().trim(), spinner_supplier_name.getText(), et_maker_name.getText().toString().trim(), mProductType, et_price.getText().toString().trim(), et_unit.getText().toString().trim(), et_inventory_num.getText().toString().trim(), et_warn_num.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_product_name.setEnabled(canInput);
        et_code.setEnabled(canInput);
        spinner_supplier_name.getTextView().setEnabled(canInput);
        et_maker_name.setEnabled(canInput);
        spinner_product_type.getTextView().setEnabled(canInput);
        et_price.setEnabled(canInput);
        et_unit.setEnabled(canInput);
        et_warn_num.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }


    @Override
    public void getAllSuppliers(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_SUPPLIERS, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(QUERY_ALL_SUPPLIERS);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_supplier_name.setListDatas(this, list);
    }

    @Override
    public void getProductType(List<DicInfo> dicInfoList) {
        mSpinnerProductList.clear();
        mSpinnerProductList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerProductList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(PRODUCT_TYPE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_product_type.setListDatas(this, mSpinnerProductList);

        spinner_product_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mProductType = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyProductDetail(CompanyProductInfo companyProductInfo) {
        if (companyProductInfo != null) {
            et_product_name.setText(companyProductInfo.getName());
            et_code.setText(companyProductInfo.getCode());
            spinner_supplier_name.setText(companyProductInfo.getSupplierName());
            et_maker_name.setText(companyProductInfo.getMakerName());
            spinner_product_type.setText(companyProductInfo.getTypeName());
            mProductType = companyProductInfo.getType();
            et_price.setText(companyProductInfo.getUnitPrice());
            et_unit.setText(companyProductInfo.getUnit());
            et_inventory_num.setText(companyProductInfo.getStockNum());
            et_warn_num.setText(companyProductInfo.getWarnNum());
            et_remark.setText(companyProductInfo.getRemark());
            mCompanyName = companyProductInfo.getCompanyName();
            for (CompanyProductInfo localInfo : mProductInfoLocalList) {
                if (localInfo.getId().equals(companyProductInfo.getId())) {
                    companyProductInfo.setLocalId(localInfo.getLocalId());
                    mProductInfoDaoManager.correct(companyProductInfo);
                }
            }
        }
    }

    @Override
    public void saveCompanyProduct(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mProductId) && mLocalId == 0l) {
            toastString = "企业产品创建成功";
        } else {
            toastString = "企业产品修改成功";
        }
        if (isLocal) {
            CompanyProductInfo companyProductInfo = null;
            if (mLocalId == 0l) {
                companyProductInfo = new CompanyProductInfo(TimeUtils.getCurrentTime(new Date()), spinner_supplier_name.getText(), StringUtil.getText(et_remark), StringUtil.getText(et_inventory_num), StringUtil.getText(et_price), StringUtil.getText(et_code), mCompanyName, mProductType, StringUtil.getText(et_warn_num), mProductId, spinner_product_type.getText(), StringUtil.getText(et_unit), StringUtil.getText(et_product_name), mCompanyId, StringUtil.getText(et_maker_name), false, isLocal);
                mProductInfoDaoManager.insertOrReplace(companyProductInfo);
            } else {
                for (CompanyProductInfo info : mProductInfoLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyProductInfo = new CompanyProductInfo(info.getLocalId(), TimeUtils.getCurrentTime(new Date()), spinner_supplier_name.getText(), StringUtil.getText(et_remark), StringUtil.getText(et_inventory_num), StringUtil.getText(et_price), StringUtil.getText(et_code), mCompanyName, mProductType, StringUtil.getText(et_warn_num), mProductId, spinner_product_type.getText(), StringUtil.getText(et_unit), StringUtil.getText(et_product_name), mCompanyId, StringUtil.getText(et_maker_name), false, isLocal);
                        mProductInfoDaoManager.correct(companyProductInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyProductEvent(toastString));
        finish();
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_ALL_SUPPLIER_LIST:
                List<DicInfo2> allSupplierList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_SUPPLIERS)) {
                        allSupplierList.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllSuppliers(allSupplierList);
                break;
            case REQUEST_PRODUCT_TYPE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(PRODUCT_TYPE)) {
                        typeList.add(dicInfo);
                    }
                }
                getProductType(typeList);
                break;
            case REQUEST_COMPANY_PRODUCT_DETAIL:
                CompanyProductInfo companyProductInfo = null;
                for (CompanyProductInfo info : mProductInfoLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyProductInfo = info;
                    }
                }
                getCompanyProductDetail(companyProductInfo);
                break;
            case REQUEST_SAVE_COMPANY_PRODUCT:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mProductId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyProduct(uploadInfoBean, true);
                break;
        }
    }
}
