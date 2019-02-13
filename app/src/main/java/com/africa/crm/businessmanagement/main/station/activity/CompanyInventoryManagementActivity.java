package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyInventoryEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyInventoryInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyQuotationInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.InventoryListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyInventoryContract;
import com.africa.crm.businessmanagement.main.station.dialog.InventoryDialog;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyInventoryPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseRefreshMvpActivity;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_PRODUCTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.STOCK_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_PRODUCTS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_INVENTORY_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_STOCK_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInventoryManagementActivity extends BaseRefreshMvpActivity<CompanyInventoryPresenter> implements CompanyInventoryContract.View {
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.spinner_product)
    MySpinner spinner_product;
    private String mProductId = "";
    @BindView(R.id.ll_add)
    LinearLayout ll_add;

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private List<DicInfo> mTypeList = new ArrayList<>();
    private String mTypeCode = "";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private InventoryListAdapter mInventoryListAdapter;
    private List<CompanyInventoryInfo> mInventoryInfoList = new ArrayList<>();

    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private WorkStationInfo mWorkStationInfo;
    private InventoryDialog mInventoryDialog;
    private String mCompanyId = "";

    private GreendaoManager<CompanyInventoryInfo, CompanyInventoryInfoDao> mInventoryInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanyInventoryInfo> mInventoryLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyInventoryManagementActivity.class);
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
        setContentView(R.layout.activity_inventory_management);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        titlebar_right.setVisibility(View.GONE);
        tv_search.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        mInventoryDialog = InventoryDialog.getInstance(this);
        initTimePicker();

        initTimePicker();
        //得到Dao对象管理器
        mInventoryInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyInventoryInfoDao());
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
    }

    private void initTimePicker() {
        pvStartTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mEndDate != null) {
                    if (mEndDate.getTime() < date.getTime()) {
                        toastMsg(getString(R.string.The_end_time_cannot_be_earlier_than_the_start_time));
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
                        toastMsg(getString(R.string.The_end_time_cannot_be_earlier_than_the_start_time));
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
    protected CompanyInventoryPresenter injectPresenter() {
        return new CompanyInventoryPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getType(STOCK_TYPE);
        mPresenter.getProductList(mCompanyId);
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        mPresenter.getInventoryList(page, rows, mCompanyId, mProductId, mTypeCode, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_start_time:
                if (pvStartTime != null)
                    pvStartTime.show();
                break;
            case R.id.tv_end_time:
                if (pvEndTime != null)
                    pvEndTime.show();
                break;
            case R.id.tv_search:
                page = 1;
                pullDownRefresh(page);
                break;
            case R.id.ll_add:
                CompanyInventoryDetailActivity.startActivity(CompanyInventoryManagementActivity.this, "", 0l);
                break;
        }
    }

    @Override
    public void initData() {
        mInventoryListAdapter = new InventoryListAdapter(mInventoryInfoList);
        recyclerView.setAdapter(mInventoryListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void getProductList(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_PRODUCTS, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_product.setListDatas(this, list);
        spinner_product.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mProductId = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getType(List<DicInfo> dicInfoList) {
        mTypeList.clear();
        mTypeList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mTypeList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(STOCK_TYPE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_type.setListDatas(this, mTypeList);
        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mTypeCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getInventoryList(CompanyInventoryInfoBean companyInventoryInfoBean) {
        if (companyInventoryInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyInventoryInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mInventoryInfoList.clear();
                mInventoryLocalList = mInventoryInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyInventoryInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyInventoryInfoBean.getRows())) {
                mInventoryInfoList.addAll(companyInventoryInfoBean.getRows());
                List<CompanyInventoryInfo> addList = DifferentDataUtil.addInventoryToLocal(mInventoryInfoList, mInventoryLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyInventoryInfo companyInfo : addList) {
                        mInventoryInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mInventoryLocalList = new ArrayList<>();
                    mInventoryLocalList = mInventoryInfoDaoManager.queryAll();
                }
                for (CompanyInventoryInfo info : mInventoryInfoList) {
                    for (CompanyInventoryInfo localInfo : mInventoryLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mInventoryInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mInventoryListAdapter != null) {
                    mInventoryListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mInventoryInfoList)) {
                mInventoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        CompanyInventoryDetailActivity.startActivity(CompanyInventoryManagementActivity.this, mInventoryInfoList.get(position).getId(), mInventoryInfoList.get(position).getLocalId());
                    }
                });
                mInventoryListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        mInventoryDialog.isCancelableOnTouchOutside(false)
                                .withDuration(300)
                                .withEffect(Effectstype.Fadein)
                                .setCancelClick(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mInventoryDialog.dismiss();
                                    }
                                })
                                .setSaveClick(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mInventoryDialog.dismiss();
                                    }
                                })
                                .show();

                    }
                });
            }
        }
    }

    @Subscribe
    public void Event(AddOrSaveCompanyInventoryEvent addOrSaveCompanyInventoryEvent) {
        toastMsg(addOrSaveCompanyInventoryEvent.getMsg());
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
        switch (port) {
            case REQUEST_ALL_PRODUCTS_LIST:
                List<DicInfo2> allProducts = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_PRODUCTS)) {
                        allProducts.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getProductList(allProducts);
                break;
            case REQUEST_STOCK_TYPE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(STOCK_TYPE)) {
                        stateList.add(dicInfo);
                    }
                }
                getType(stateList);
                break;
            case REQUEST_INVENTORY_LIST:
                List<CompanyInventoryInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(mProductId) || !TextUtils.isEmpty(mTypeCode)) {
                    if (!TextUtils.isEmpty(mProductId) && !TextUtils.isEmpty(mTypeCode)) {
                        rows = mInventoryInfoDaoManager.queryBuilder().where(CompanyInventoryInfoDao.Properties.ProductId.eq(mProductId), CompanyInventoryInfoDao.Properties.Type.eq(mTypeCode), CompanyQuotationInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyQuotationInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();

                    } else if (!TextUtils.isEmpty(mProductId) && TextUtils.isEmpty(mTypeCode)) {
                        rows = mInventoryInfoDaoManager.queryBuilder().where(CompanyInventoryInfoDao.Properties.ProductId.eq(mProductId), CompanyQuotationInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyQuotationInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();

                    } else if (TextUtils.isEmpty(mProductId) && !TextUtils.isEmpty(mTypeCode)) {
                        rows = mInventoryInfoDaoManager.queryBuilder().where(CompanyInventoryInfoDao.Properties.Type.eq(mTypeCode), CompanyQuotationInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyQuotationInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                    }
                } else {
                    rows = mInventoryInfoDaoManager.queryAll();
                }
                CompanyInventoryInfoBean companyInventoryInfoBean = new CompanyInventoryInfoBean();
                companyInventoryInfoBean.setRows(rows);
                getInventoryList(companyInventoryInfoBean);
                break;
        }
    }
}
