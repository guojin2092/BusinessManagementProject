package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyProductEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteProductInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteProductInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyProductInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.ProductListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyProductManagementContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyProductPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseRefreshMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.DicUtil.PRODUCT_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PRODUCT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_PRODUCT;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_PRODUCT_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyProductManagementActivity extends BaseRefreshMvpActivity<CompanyProductPresenter> implements CompanyProductManagementContract.View {
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.et_name)
    EditText et_name;

    private WorkStationInfo mWorkStationInfo;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ProductListAdapter mProductListAdapter;
    private List<CompanyProductInfo> mDeleteList = new ArrayList<>();
    private List<CompanyProductInfo> mProductInfoBeanList = new ArrayList<>();//网络数据
    private List<CompanyProductInfo> mProductInfoLocalList = new ArrayList<>();//本地数据

    private boolean mShowCheckBox = false;
    private AlertDialog mDeleteDialog;

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private List<DicInfo> mSpinnerProductList = new ArrayList<>();
    private String mProductType = "";
    private String mCompanyId = "";

    private GreendaoManager<CompanyProductInfo, CompanyProductInfoDao> mProductInfoDaoManager;
    private GreendaoManager<CompanyDeleteProductInfo, CompanyDeleteProductInfoDao> mDeleteProductInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyProductManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_product_management);
    }

    @Override
    protected CompanyProductPresenter injectPresenter() {
        return new CompanyProductPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getProductType(PRODUCT_TYPE);
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        mPresenter.getCompanyProductList(page, rows, mCompanyId, et_name.getText().toString().trim(), mProductType);
    }

    @Override
    public void initView() {
        super.initView();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_search.setOnClickListener(this);

        //得到Dao对象管理器
        mProductInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyProductInfoDao());
        //得到Dao对象管理器
        mDeleteProductInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteProductInfoDao());
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
/*
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    spinner_type.setText("");
                    mProductType = "";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_search:
                page = 1;
                pullDownRefresh(page);
                break;
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.Delete))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_delete.setVisibility(View.VISIBLE);
                    mShowCheckBox = true;
                } else {
                    titlebar_right.setText(R.string.Delete);
                    tv_delete.setVisibility(View.GONE);
                    mShowCheckBox = false;
                }
                if (mProductListAdapter != null) {
                    mProductListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyProductDetailActivity.startActivity(CompanyProductManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyProductInfo companyProductInfo : mProductInfoBeanList) {
                    if (companyProductInfo.isChosen()) {
                        mDeleteList.add(companyProductInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg(getString(R.string.no_choose_delete));
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyProductManagementActivity.this)
                        .setTitle(R.string.tips)
                        .setMessage(R.string.confirm_delete)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    mPresenter.deleteCompanyProduct(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void initData() {
        mProductListAdapter = new ProductListAdapter(mProductInfoBeanList);
        recyclerView.setAdapter(mProductListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
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
        spinner_type.setListDatas(this, mSpinnerProductList);

        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mProductType = dicInfo.getCode();
/*
                if (!TextUtils.isEmpty(mProductType)) {
                    et_name.setText("");
                }
*/
            }
        });
    }

    @Override
    public void getCompanyProductList(CompanyProductInfoBean companyProductInfoBean) {
        if (companyProductInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyProductInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
//                    KeyboardUtil.clearInputBox(et_name);
//                    spinner_type.setText("");
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mProductInfoBeanList.clear();
                mProductInfoLocalList = mProductInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyProductInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyProductInfoBean.getRows())) {
                mProductInfoBeanList.addAll(companyProductInfoBean.getRows());
                List<CompanyProductInfo> addList = DifferentDataUtil.addProductDataToLocal(mProductInfoBeanList, mProductInfoLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyProductInfo companyInfo : addList) {
                        mProductInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mProductInfoLocalList = new ArrayList<>();
                    mProductInfoLocalList = mProductInfoDaoManager.queryAll();
                }
                for (CompanyProductInfo info : mProductInfoBeanList) {
                    for (CompanyProductInfo localInfo : mProductInfoLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mProductInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mProductListAdapter != null) {
                    mProductListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mProductInfoBeanList)) {
                mProductListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mProductInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                            mProductListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyProductDetailActivity.startActivity(CompanyProductManagementActivity.this, mProductInfoBeanList.get(position).getId(), mProductInfoBeanList.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteCompanyProduct(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mProductInfoBeanList.contains(mDeleteList.get(i))) {
                    int position = mProductInfoBeanList.indexOf(mDeleteList.get(i));
                    mProductInfoBeanList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyProductInfo companyInfo : mDeleteList) {
                            CompanyDeleteProductInfo deleteInfo = new CompanyDeleteProductInfo(companyInfo.getCreateTime(), companyInfo.getSupplierName(), companyInfo.getRemark(), companyInfo.getStockNum(), companyInfo.getUnitPrice(), companyInfo.getCode(), companyInfo.getCompanyName(), companyInfo.getType(), companyInfo.getWarnNum(), companyInfo.getId(), companyInfo.getTypeName(), companyInfo.getUnit(), companyInfo.getName(), companyInfo.getCompanyId(), companyInfo.getMakerName(), false, true);
                            mDeleteProductInfoDaoManager.insertOrReplace(deleteInfo);
                        }
                    }
                    for (CompanyProductInfo companyInfo : mProductInfoLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mProductInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mProductInfoLocalList = new ArrayList<>();
                    mProductInfoLocalList = mProductInfoDaoManager.queryAll();
                    if (mProductListAdapter != null) {
                        mProductListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mProductInfoBeanList)) {
                titlebar_right.setText(R.string.Delete);
                tv_delete.setVisibility(View.GONE);
                mShowCheckBox = false;
                layout_network_error.setVisibility(View.GONE);
                mRefreshLayout.getLayout().setVisibility(View.GONE);
                layout_no_data.setVisibility(View.VISIBLE);
            }
            mDeleteDialog.dismiss();
        } else {
            ErrorMsg.showErrorMsg(baseEntity.getReturnMsg());
        }
    }

    @Subscribe
    public void Event(AddOrSaveCompanyProductEvent addOrSaveCompanyProductEvent) {
        toastMsg(addOrSaveCompanyProductEvent.getMsg());
        page = 1;
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        mRefreshLayout.setEnableLoadmore(false);
        if (port.equals(REQUEST_PRODUCT_TYPE)) {
            List<DicInfo> stateList = new ArrayList<>();
            for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                if (dicInfo.getType().equals(PRODUCT_TYPE)) {
                    stateList.add(dicInfo);
                }
            }
            getProductType(stateList);
        } else if (port.equals(REQUEST_COMPANY_PRODUCT_LIST)) {
            List<CompanyProductInfo> rows = new ArrayList<>();
            if (!TextUtils.isEmpty(StringUtil.getText(et_name)) || !TextUtils.isEmpty(mProductType)) {
                if (!TextUtils.isEmpty(mProductType)) {
                    rows = mProductInfoDaoManager.queryBuilder().where(CompanyProductInfoDao.Properties.Name.like("%" + StringUtil.getText(et_name) + "%"), CompanyProductInfoDao.Properties.Type.eq(mProductType)).list();
                } else {
                    rows = mProductInfoDaoManager.queryBuilder().where(CompanyProductInfoDao.Properties.Name.like("%" + StringUtil.getText(et_name) + "%")).list();
                }
            } else {
                rows = mProductInfoDaoManager.queryAll();
            }
            CompanyProductInfoBean companyInfoBean = new CompanyProductInfoBean();
            companyInfoBean.setRows(rows);
            getCompanyProductList(companyInfoBean);
        } else if (port.equals(REQUEST_DELETE_COMPANY_PRODUCT)) {
            BaseEntity baseEntity = new BaseEntity();
            baseEntity.setSuccess(true);
            deleteCompanyProduct(baseEntity, true);
        }
    }
}
