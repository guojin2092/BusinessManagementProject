package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyInventoryEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyInventoryInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyInventoryDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyInventoryDetailPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_PRODUCTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.STOCK_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_PRODUCTS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_INVENTORY_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_INVENTORY;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_STOCK_TYPE;

public class CompanyInventoryDetailActivity extends BaseMvpActivity<CompanyInventoryDetailPresenter> implements CompanyInventoryDetailContract.View {
    @BindView(R.id.tv_operation_time)
    TextView tv_operation_time;
    @BindView(R.id.ll_operation_time)
    LinearLayout ll_operation_time;
    @BindView(R.id.ll_num_before)
    LinearLayout ll_num_before;
    @BindView(R.id.et_num_before)
    EditText et_num_before;
    @BindView(R.id.ll_num_after)
    LinearLayout ll_num_after;
    @BindView(R.id.et_num_after)
    EditText et_num_after;
    @BindView(R.id.spinner_product)
    MySpinner spinner_product;
    private String mProductId = "";
    private String mProductName = "";
    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private List<DicInfo> mSpinnerTypeList = new ArrayList<>();
    private String mTypeCode = "";
    private String mTypeName = "";

    @BindView(R.id.et_num)
    EditText et_num;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private String mInventoryId = "";
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mCompanyName = "";

    private GreendaoManager<CompanyInventoryInfo, CompanyInventoryInfoDao> mInventoryInfoDaoManager;
    private List<CompanyInventoryInfo> mInventoryLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoGreendaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据


    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanyInventoryDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_inventory_detail);
    }

    @Override
    public void initView() {
        super.initView();
        mInventoryId = getIntent().getStringExtra("id");
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        titlebar_right.setVisibility(View.GONE);
        titlebar_name.setText(R.string.Inventory_Details);
        tv_save.setText(R.string.Add);
        tv_save.setOnClickListener(this);
        if (TextUtils.isEmpty(mInventoryId) && mLocalId == 0l) {
            tv_save.setVisibility(View.VISIBLE);
            setEditTextInput(true);
        } else {
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        }

        //得到Dao对象管理器
        mInventoryInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyInventoryInfoDao());
        //得到本地数据
        mInventoryLocalList = mInventoryInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoGreendaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoGreendaoManager.queryAll();
    }

    @Override
    protected CompanyInventoryDetailPresenter injectPresenter() {
        return new CompanyInventoryDetailPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getAllProduct(mCompanyId);
        mPresenter.getOperationType(STOCK_TYPE);
        if (!TextUtils.isEmpty(mInventoryId) || mLocalId != 0l) {
            ll_operation_time.setVisibility(View.VISIBLE);
            ll_num_before.setVisibility(View.VISIBLE);
            ll_num_after.setVisibility(View.VISIBLE);
            mPresenter.getInventoryDetail(mInventoryId);
        } else {
            ll_operation_time.setVisibility(View.GONE);
            ll_num_before.setVisibility(View.GONE);
            ll_num_after.setVisibility(View.GONE);
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
        spinner_product.getTextView().setEnabled(canInput);
        tv_operation_time.setEnabled(canInput);
        spinner_type.getTextView().setEnabled(canInput);
        et_num.setEnabled(canInput);
        et_num_before.setEnabled(canInput);
        et_num_after.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.Edit))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_save.setVisibility(View.VISIBLE);
                } else {
                    titlebar_right.setText(R.string.Edit);
                    tv_save.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(mProductId) && mLocalId == 0l) {
                    toastMsg(getString(R.string.Please_select_a_product));
                    return;
                }
                if (TextUtils.isEmpty(mTypeCode)) {
                    toastMsg(getString(R.string.Please_select_the_type_of_operation));
                    return;
                }
                if (TextUtils.isEmpty(et_num.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_fill_in_the_quantity));
                    return;
                }
                mPresenter.saveInventory(mCompanyId, mProductId, mTypeCode, et_num.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    @Override
    public void getAllProduct(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_PRODUCTS, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
        }
        spinner_product.setListDatas(this, list);
        spinner_product.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mProductId = dicInfo.getCode();
                mProductName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getOperationType(List<DicInfo> dicInfoList) {
        mSpinnerTypeList.clear();
        mSpinnerTypeList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerTypeList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(STOCK_TYPE);
            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
        }
        spinner_type.setListDatas(this, mSpinnerTypeList);
        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mTypeCode = dicInfo.getCode();
                mTypeName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getInventoryDetail(CompanyInventoryInfo companyInventoryInfo) {
        if (companyInventoryInfo != null) {
            et_num_before.setText(companyInventoryInfo.getNumBefore());
            et_num_after.setText(companyInventoryInfo.getNumAfter());
            tv_operation_time.setText(companyInventoryInfo.getCreateTime());
            spinner_product.setText(companyInventoryInfo.getProductName());
            mProductId = companyInventoryInfo.getProductId();
            spinner_type.setText(companyInventoryInfo.getTypeName());
            mTypeCode = companyInventoryInfo.getType();
            et_num.setText(companyInventoryInfo.getNum());
            et_remark.setText(companyInventoryInfo.getRemark());
            mCompanyName = companyInventoryInfo.getCompanyName();
            for (CompanyInventoryInfo localInfo : mInventoryLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyInventoryInfo.getId())) {
                    if (localInfo.getId().equals(companyInventoryInfo.getId())) {
                        companyInventoryInfo.setLocalId(localInfo.getLocalId());
                        mInventoryInfoDaoManager.correct(companyInventoryInfo);
                    }
                }
            }
        }
    }

    @Override
    public void saveInventory(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mInventoryId) && mLocalId == 0l) {
            toastString = getString(R.string.Added_Successfully);
        } else {
            toastString = getString(R.string.Successfully_Modified);
        }
        if (isLocal) {
            CompanyInventoryInfo companyInventoryInfo = null;
            if (mLocalId == 0l) {
                companyInventoryInfo = new CompanyInventoryInfo(mTypeName, mInventoryId, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_num), StringUtil.getText(et_remark), StringUtil.getText(et_num_after), StringUtil.getText(et_num_before), mCompanyId, mTypeCode, mCompanyName, mProductName, mProductId, isLocal);
                mInventoryInfoDaoManager.insertOrReplace(companyInventoryInfo);
            } else {
                for (CompanyInventoryInfo info : mInventoryLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyInventoryInfo = new CompanyInventoryInfo(info.getLocalId(), mTypeName, mInventoryId, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_num), StringUtil.getText(et_remark), StringUtil.getText(et_num_after), StringUtil.getText(et_num_before), mCompanyId, mTypeCode, mCompanyName, mProductName, mProductId, isLocal);
                        mInventoryInfoDaoManager.correct(companyInventoryInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyInventoryEvent(toastString));
        finish();
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_ALL_PRODUCTS_LIST:
                List<DicInfo2> allProducts = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoGreendaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_PRODUCTS)) {
                        allProducts.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllProduct(allProducts);
                break;
            case REQUEST_STOCK_TYPE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoGreendaoManager.queryAll()) {
                    if (dicInfo.getType().equals(STOCK_TYPE)) {
                        typeList.add(dicInfo);
                    }
                }
                getOperationType(typeList);
                break;
            case REQUEST_COMPANY_INVENTORY_DETAIL:
                CompanyInventoryInfo companyInventoryInfo = null;
                for (CompanyInventoryInfo info : mInventoryLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyInventoryInfo = info;
                    }
                }
                getInventoryDetail(companyInventoryInfo);
                break;
            case REQUEST_SAVE_COMPANY_INVENTORY:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mInventoryId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveInventory(uploadInfoBean, true);
                break;
        }
    }
}
