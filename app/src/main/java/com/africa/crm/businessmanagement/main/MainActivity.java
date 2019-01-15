package com.africa.crm.businessmanagement.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.main.adapter.WorkStationListAdapter;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.contract.MainContract;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.presenter.MainPresenter;
import com.africa.crm.businessmanagement.main.station.activity.CompanyAccountManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyClientManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyContactManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyDeliveryOrderManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyExpenditureManagementActivityA;
import com.africa.crm.businessmanagement.main.station.activity.CompanyExpenditureManagementActivityB;
import com.africa.crm.businessmanagement.main.station.activity.CompanyInfoManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyInventoryManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyPackagingDataManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyPaymentManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyPdfReportManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyProductManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyPurchasingManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyQuotationManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanySalesOrderManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyServiceRecordManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyStatisticalFormActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanySupplierManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyTaskManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.CompanyTradingOrderManagementActivity;
import com.africa.crm.businessmanagement.main.station.activity.SettingActivity;
import com.africa.crm.businessmanagement.main.station.activity.SystemManagementActivity;
import com.africa.crm.businessmanagement.mvp.activity.BaseEasyMvpActivity;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.GridItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.DicUtil.COMPANY_TYPE_CODE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.FROM_TYPE_CODE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.INDUSTRY_CODE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.INVOICE_STATE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.PRODUCT_TYPE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.PURCHASE_STATE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_COMPANY;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_CONTACTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_CUSTOMERS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_PRODUCTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_ROLES;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_SALE_ORDER;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_SUPPLIERS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_TRADING_ORDER;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_USERS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.SALE_ORDER_STATE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.SERVICE_LEVEL;
import static com.africa.crm.businessmanagement.network.api.DicUtil.SERVICE_STATE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.SERVICE_TYPE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.STATE_CODE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.STOCK_TYPE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.SUPPLIER_TYPE_CODE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.TASK_LEVEL;
import static com.africa.crm.businessmanagement.network.api.DicUtil.TASK_STATE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.USER_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_TYPE;

public class MainActivity extends BaseEasyMvpActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.rv_work_station)
    RecyclerView rv_work_station;

    @BindView(R.id.ll_message)
    LinearLayout ll_message;
    @BindView(R.id.iv_cancel)
    ImageView iv_cancel;
    private String mTaskId = "";

    private List<WorkStationInfo> mWorkStationInfoList = new ArrayList<>();
    private WorkStationListAdapter mWorkStationListAdapter;
    private List<String> mCodeList = new ArrayList<>();

    private String mUserId = "";
    private String mCompanyId = "";
    private String mCompanyName = "";
    private long firstTime = 0;

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoGreendaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected MainPresenter injectPresenter() {
        return new MainPresenter();
    }

    @Override
    public void initView() {
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        titlebar_back.setVisibility(View.GONE);
        titlebar_name.setText(getString(R.string.work_station));
        iv_cancel.setOnClickListener(this);

        //得到Dao对象管理器
        mDicInfoDaoGreendaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoGreendaoManager.queryAll();
    }

    @Override
    protected void requestData() {
        //企业类型
        addDisposable(mDataManager.getDicByCode(COMPANY_TYPE_CODE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerCompanyTypeList = new ArrayList<>();
                        spinnerCompanyTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerCompanyTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(COMPANY_TYPE_CODE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this, REQUEST_COMPANY_TYPE)));
        //企业状态
        addDisposable(mDataManager.getDicByCode(STATE_CODE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerStateList = new ArrayList<>();
                        spinnerStateList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerStateList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(STATE_CODE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this, REQUEST_COMPANY_STATE)));
        //所有用户角色
        addDisposable(mDataManager.getAllRoles()
                .compose(RxUtils.<List<RoleInfoBean>>ioToMain(this))
                .subscribe(new Consumer<List<RoleInfoBean>>() {
                    @Override
                    public void accept(List<RoleInfoBean> roleInfoBeanList) throws Exception {
                        List<DicInfo> spinnerRoleList = new ArrayList<>();
                        if (roleInfoBeanList.size() == 3) {
                            spinnerRoleList.add(new DicInfo(roleInfoBeanList.get(0).getId(), QUERY_ALL_ROLES, roleInfoBeanList.get(0).getRoleName(), roleInfoBeanList.get(0).getRoleCode()));
                            spinnerRoleList.add(new DicInfo(roleInfoBeanList.get(1).getId(), QUERY_ALL_ROLES, roleInfoBeanList.get(1).getRoleName(), roleInfoBeanList.get(1).getRoleCode()));
                        } else {
                            if (!ListUtils.isEmpty(roleInfoBeanList)) {
                                for (RoleInfoBean roleInfoBean : roleInfoBeanList) {
                                    DicInfo dicInfo = new DicInfo(roleInfoBean.getId(), QUERY_ALL_ROLES, roleInfoBean.getRoleName(), roleInfoBean.getRoleCode());
                                    spinnerRoleList.add(dicInfo);
                                }
                            }
                        }
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerRoleList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //供应商分类
        addDisposable(mDataManager.getDicByCode(SUPPLIER_TYPE_CODE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(SUPPLIER_TYPE_CODE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //所有供应商列表
        addDisposable(mDataManager.getAllSuppliers(mCompanyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        List<DicInfo> list = new ArrayList<>();
                        for (DicInfo2 dicInfo2 : dicInfoList) {
                            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_SUPPLIERS, dicInfo2.getName(), dicInfo2.getCode()));
                        }
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //产品分类
        addDisposable(mDataManager.getDicByCode(PRODUCT_TYPE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> productList = new ArrayList<>();
                        productList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(productList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(PRODUCT_TYPE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //所有所属用户
        addDisposable(mDataManager.getAllCompanyUser(mCompanyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        List<DicInfo> list = new ArrayList<>();
                        for (DicInfo2 dicInfo2 : dicInfoList) {
                            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_USERS, dicInfo2.getName(), dicInfo2.getCode()));
                        }
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //行业分类
        addDisposable(mDataManager.getDicByCode(INDUSTRY_CODE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(INDUSTRY_CODE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //联系人来源分类
        addDisposable(mDataManager.getDicByCode(FROM_TYPE_CODE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(FROM_TYPE_CODE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //所有联系人
        addDisposable(mDataManager.getAllContact(mCompanyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        List<DicInfo> list = new ArrayList<>();
                        for (DicInfo2 dicInfo2 : dicInfoList) {
                            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_CONTACTS, dicInfo2.getName(), dicInfo2.getCode()));
                        }
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //所有客户
        addDisposable(mDataManager.getAllCustomers(mCompanyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        List<DicInfo> list = new ArrayList<>();
                        for (DicInfo2 dicInfo2 : dicInfoList) {
                            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_CUSTOMERS, dicInfo2.getName(), dicInfo2.getCode()));
                        }
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //所有产品
        addDisposable(mDataManager.getAllProducts(mCompanyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        List<DicInfo> list = new ArrayList<>();
                        for (DicInfo2 dicInfo2 : dicInfoList) {
                            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_PRODUCTS, dicInfo2.getName(), dicInfo2.getCode()));
                        }
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //销售单状态
        addDisposable(mDataManager.getDicByCode(SALE_ORDER_STATE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(SALE_ORDER_STATE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //发货单状态
        addDisposable(mDataManager.getDicByCode(INVOICE_STATE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(INVOICE_STATE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //所有销售单
        addDisposable(mDataManager.getAllSaleOrders(mCompanyId, mUserId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        List<DicInfo> list = new ArrayList<>();
                        for (DicInfo2 dicInfo2 : dicInfoList) {
                            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_SALE_ORDER, dicInfo2.getName(), dicInfo2.getCode()));
                        }
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //所有交易单
        addDisposable(mDataManager.getAllTradingOrders(mCompanyId, mUserId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        List<DicInfo> list = new ArrayList<>();
                        for (DicInfo2 dicInfo2 : dicInfoList) {
                            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_TRADING_ORDER, dicInfo2.getName(), dicInfo2.getCode()));
                        }
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //服务状态
        addDisposable(mDataManager.getDicByCode(SERVICE_STATE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(SERVICE_STATE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //服务类型
        addDisposable(mDataManager.getDicByCode(SERVICE_TYPE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(SERVICE_TYPE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //服务优先级
        addDisposable(mDataManager.getDicByCode(SERVICE_LEVEL)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(SERVICE_LEVEL);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //库存类型
        addDisposable(mDataManager.getDicByCode(STOCK_TYPE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(STOCK_TYPE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //采购单状态
        addDisposable(mDataManager.getDicByCode(PURCHASE_STATE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(PURCHASE_STATE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //任务等级
        addDisposable(mDataManager.getDicByCode(TASK_LEVEL)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(TASK_LEVEL);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //任务状态
        addDisposable(mDataManager.getDicByCode(TASK_STATE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(TASK_STATE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //用户类型
        addDisposable(mDataManager.getDicByCode(USER_TYPE)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        List<DicInfo> spinnerSupplierTypeList = new ArrayList<>();
                        spinnerSupplierTypeList.addAll(dicInfoList);
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(spinnerSupplierTypeList, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            dicInfo.setType(USER_TYPE);
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));
        //所有公司
        addDisposable(mDataManager.getAllCompany(mCompanyName)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        List<DicInfo> list = new ArrayList<>();
                        for (DicInfo2 dicInfo2 : dicInfoList) {
                            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_COMPANY, dicInfo2.getName(), dicInfo2.getCode()));
                        }
                        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
                        for (DicInfo dicInfo : addList) {
                            mDicInfoDaoGreendaoManager.insertOrReplace(dicInfo);
                        }
                    }
                }, new ComConsumer(this)));

        if (!TextUtils.isEmpty(mUserId)) {
            mPresenter.getMainStationInfo(mUserId);
            mPresenter.getRecentTask(mUserId);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_cancel:
                mPresenter.setTaskRead(mTaskId);
                break;
        }
    }

    @Override
    public void initData() {
     /*   WorkStationInfo workStationInfo = new WorkStationInfo();
        workStationInfo.setWork_type("1");
        workStationInfo.setWork_name(getString(R.string.enterprise_information_management));
        mWorkStationInfoList.add(workStationInfo);
        WorkStationInfo workStationInfo2 = new WorkStationInfo();
        workStationInfo2.setWork_type("2");
        workStationInfo2.setWork_name(getString(R.string.enterprise_account_management));
        mWorkStationInfoList.add(workStationInfo2);
        WorkStationInfo workStationInfo3 = new WorkStationInfo();
        workStationInfo3.setWork_type("3");
        workStationInfo3.setWork_name(getString(R.string.supplier_management));
        mWorkStationInfoList.add(workStationInfo3);
        WorkStationInfo workStationInfo4 = new WorkStationInfo();
        workStationInfo4.setWork_type("4");
        workStationInfo4.setWork_name(getString(R.string.product_management));
        mWorkStationInfoList.add(workStationInfo4);
        WorkStationInfo workStationInfo5 = new WorkStationInfo();
        workStationInfo5.setWork_type("5");
        workStationInfo5.setWork_name(getString(R.string.customer_management));
        mWorkStationInfoList.add(workStationInfo5);
        WorkStationInfo workStationInfo6 = new WorkStationInfo();
        workStationInfo6.setWork_type("6");
        workStationInfo6.setWork_name(getString(R.string.contact_management));
        mWorkStationInfoList.add(workStationInfo6);
        WorkStationInfo workStationInfo7 = new WorkStationInfo();
        workStationInfo7.setWork_type("7");
        workStationInfo7.setWork_name(getString(R.string.trading_order_management));
        mWorkStationInfoList.add(workStationInfo7);
        WorkStationInfo workStationInfo8 = new WorkStationInfo();
        workStationInfo8.setWork_type("8");
        workStationInfo8.setWork_name(getString(R.string.quotation_management));
        mWorkStationInfoList.add(workStationInfo8);
        WorkStationInfo workStationInfo9 = new WorkStationInfo();
        workStationInfo9.setWork_type("9");
        workStationInfo9.setWork_name(getString(R.string.sales_order_management));
        mWorkStationInfoList.add(workStationInfo9);
        WorkStationInfo workStationInfo10 = new WorkStationInfo();
        workStationInfo10.setWork_type("10");
        workStationInfo10.setWork_name(getString(R.string.delivery_order_management));
        mWorkStationInfoList.add(workStationInfo10);
        WorkStationInfo workStationInfo11 = new WorkStationInfo();
        workStationInfo11.setWork_type("11");
        workStationInfo11.setWork_name(getString(R.string.payment_management));
        mWorkStationInfoList.add(workStationInfo11);
        WorkStationInfo workStationInfo12 = new WorkStationInfo();
        workStationInfo12.setWork_type("12");
        workStationInfo12.setWork_name(getString(R.string.service_record_management));
        mWorkStationInfoList.add(workStationInfo12);
        WorkStationInfo workStationInfo13 = new WorkStationInfo();
        workStationInfo13.setWork_type("13");
        workStationInfo13.setWork_name(getString(R.string.inventory_management));
        mWorkStationInfoList.add(workStationInfo13);
        WorkStationInfo workStationInfo14 = new WorkStationInfo();
        workStationInfo14.setWork_type("14");
        workStationInfo14.setWork_name(getString(R.string.purchasing_management));
        mWorkStationInfoList.add(workStationInfo14);
        WorkStationInfo workStationInfo15 = new WorkStationInfo();
        workStationInfo15.setWork_type("15");
        workStationInfo15.setWork_name(getString(R.string.task_management));
        mWorkStationInfoList.add(workStationInfo15);
        WorkStationInfo workStationInfo16 = new WorkStationInfo();
        workStationInfo16.setWork_type("16");
        workStationInfo16.setWork_name(getString(R.string.pdf_report_management));
        mWorkStationInfoList.add(workStationInfo16);
        WorkStationInfo workStationInfo17 = new WorkStationInfo();
        workStationInfo17.setWork_type("17");
        workStationInfo17.setWork_name(getString(R.string.packaging_data_management));
        mWorkStationInfoList.add(workStationInfo17);
        WorkStationInfo workStationInfo18 = new WorkStationInfo();
        workStationInfo18.setWork_type("18");
        workStationInfo18.setWork_name(getString(R.string.statistical_report_forms));
        mWorkStationInfoList.add(workStationInfo18);
        WorkStationInfo workStationInfo19 = new WorkStationInfo();
        workStationInfo19.setWork_type("19");
        workStationInfo19.setWork_name(getString(R.string.enterprise_expenditure_management));
        mWorkStationInfoList.add(workStationInfo19);
        WorkStationInfo workStationInfo20 = new WorkStationInfo();
        workStationInfo20.setWork_type("20");
        workStationInfo20.setWork_name(getString(R.string.system_management));
        mWorkStationInfoList.add(workStationInfo20);
        WorkStationInfo workStationInfo21 = new WorkStationInfo();
        workStationInfo21.setWork_type("21");
        workStationInfo21.setWork_name(getString(R.string.setting));
        mWorkStationInfoList.add(workStationInfo21);
        setWorkStationDatas(mWorkStationInfoList);*/
    }

    @Override
    public void getRecentTask(CompanyTaskInfo companyTaskInfo) {
        if (!TextUtils.isEmpty(companyTaskInfo.getId())) {
            mTaskId = companyTaskInfo.getId();
            TranslateAnimation startAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            startAnim.setDuration(300);
            ll_message.startAnimation(startAnim);
            ll_message.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTaskRead(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            hideAnim.setDuration(300);
            ll_message.startAnimation(hideAnim);
            ll_message.setVisibility(View.GONE);
        } else {
            toastMsg(baseEntity.getReturnMsg());
        }
    }

    @Override
    public void getMainStationInfo(List<MainStationInfoBean> mainStationInfoBeanList) {
        if (!ListUtils.isEmpty(mainStationInfoBeanList)) {
            for (MainStationInfoBean mainStationInfoBean : mainStationInfoBeanList) {
                mCodeList.add(mainStationInfoBean.getResCode());
            }
            for (String code : mCodeList) {
                mWorkStationInfoList.add(setWorkInfo(code));
            }
            WorkStationInfo workStationInfo21 = new WorkStationInfo();
            workStationInfo21.setWork_type("21");
            workStationInfo21.setWork_name(getString(R.string.setting));
            mWorkStationInfoList.add(workStationInfo21);
            setWorkStationDatas(mWorkStationInfoList);
        }
    }

    /**
     * 匹配工作台图标
     *
     * @param code
     * @return
     */
    private WorkStationInfo setWorkInfo(String code) {
        WorkStationInfo workInfo = null;
        switch (code) {
            case "COMPANYMANAGE":
                WorkStationInfo workStationInfo = new WorkStationInfo();
                workStationInfo.setWork_type("1");
                workStationInfo.setWork_name(getString(R.string.enterprise_information_management));
                workInfo = workStationInfo;
                break;
            case "COMPANYUSERMANAGE":
                WorkStationInfo workStationInfo2 = new WorkStationInfo();
                workStationInfo2.setWork_type("2");
                workStationInfo2.setWork_name(getString(R.string.enterprise_account_management));
                workInfo = workStationInfo2;
                break;
            case "SUPPLIERMANAGE":
                WorkStationInfo workStationInfo3 = new WorkStationInfo();
                workStationInfo3.setWork_type("3");
                workStationInfo3.setWork_name(getString(R.string.supplier_management));
                workInfo = workStationInfo3;
                break;
            case "PRODUCTMANAGE":
                WorkStationInfo workStationInfo4 = new WorkStationInfo();
                workStationInfo4.setWork_type("4");
                workStationInfo4.setWork_name(getString(R.string.product_management));
                workInfo = workStationInfo4;
                break;
            case "CUSTOMERMANAGE":
                WorkStationInfo workStationInfo5 = new WorkStationInfo();
                workStationInfo5.setWork_type("5");
                workStationInfo5.setWork_name(getString(R.string.customer_management));
                workInfo = workStationInfo5;
                break;
            case "CONTACTMANAGE":
                WorkStationInfo workStationInfo6 = new WorkStationInfo();
                workStationInfo6.setWork_type("6");
                workStationInfo6.setWork_name(getString(R.string.contact_management));
                workInfo = workStationInfo6;
                break;
            case "TRADINGORDER":
                WorkStationInfo workStationInfo7 = new WorkStationInfo();
                workStationInfo7.setWork_type("7");
                workStationInfo7.setWork_name(getString(R.string.trading_order_management));
                workInfo = workStationInfo7;
                break;
            case "QUOTATIONORDER":
                WorkStationInfo workStationInfo8 = new WorkStationInfo();
                workStationInfo8.setWork_type("8");
                workStationInfo8.setWork_name(getString(R.string.quotation_management));
                workInfo = workStationInfo8;
                break;
            case "SALESORDER":
                WorkStationInfo workStationInfo9 = new WorkStationInfo();
                workStationInfo9.setWork_type("9");
                workStationInfo9.setWork_name(getString(R.string.sales_order_management));
                workInfo = workStationInfo9;
                break;
            case "INVOICEORDER":
                WorkStationInfo workStationInfo10 = new WorkStationInfo();
                workStationInfo10.setWork_type("10");
                workStationInfo10.setWork_name(getString(R.string.delivery_order_management));
                workInfo = workStationInfo10;
                break;
            case "PAYMENTORDER":
                WorkStationInfo workStationInfo11 = new WorkStationInfo();
                workStationInfo11.setWork_type("11");
                workStationInfo11.setWork_name(getString(R.string.payment_management));
                workInfo = workStationInfo11;
                break;
            case "SERVICERECORD":
                WorkStationInfo workStationInfo12 = new WorkStationInfo();
                workStationInfo12.setWork_type("12");
                workStationInfo12.setWork_name(getString(R.string.service_record_management));
                workInfo = workStationInfo12;
                break;
            case "STOCKRECORD":
                WorkStationInfo workStationInfo13 = new WorkStationInfo();
                workStationInfo13.setWork_type("13");
                workStationInfo13.setWork_name(getString(R.string.inventory_management));
                workInfo = workStationInfo13;
                break;
            case "PURCHASEORDER":
                WorkStationInfo workStationInfo14 = new WorkStationInfo();
                workStationInfo14.setWork_type("14");
                workStationInfo14.setWork_name(getString(R.string.purchasing_management));
                workInfo = workStationInfo14;
                break;
            case "TASKRECORD":
                WorkStationInfo workStationInfo15 = new WorkStationInfo();
                workStationInfo15.setWork_type("15");
                workStationInfo15.setWork_name(getString(R.string.task_management));
                workInfo = workStationInfo15;
                break;
            case "PDFFILES":
                WorkStationInfo workStationInfo16 = new WorkStationInfo();
                workStationInfo16.setWork_type("16");
                workStationInfo16.setWork_name(getString(R.string.pdf_report_management));
                workInfo = workStationInfo16;
                break;
            case "PACKAGERECORD":
                WorkStationInfo workStationInfo17 = new WorkStationInfo();
                workStationInfo17.setWork_type("17");
                workStationInfo17.setWork_name(getString(R.string.packaging_data_management));
                workInfo = workStationInfo17;
                break;
            case "REPORTS":
                WorkStationInfo workStationInfo18 = new WorkStationInfo();
                workStationInfo18.setWork_type("18");
                workStationInfo18.setWork_name(getString(R.string.statistical_report_forms));
                workInfo = workStationInfo18;
                break;
            case "PAYESTIMATE":
                WorkStationInfo workStationInfo19 = new WorkStationInfo();
                workStationInfo19.setWork_type("19");
                workStationInfo19.setWork_name(getString(R.string.enterprise_expenditure_management));
                workInfo = workStationInfo19;
                break;
            case "SYSTEMMANAGE":
                WorkStationInfo workStationInfo20 = new WorkStationInfo();
                workStationInfo20.setWork_type("20");
                workStationInfo20.setWork_name(getString(R.string.system_management));
                workInfo = workStationInfo20;
                break;
        }
        return workInfo;
    }

    /**
     * 设置工作台数据
     *
     * @param workStationInfoList
     */


    private void setWorkStationDatas(final List<WorkStationInfo> workStationInfoList) {
        if (!ListUtils.isEmpty(workStationInfoList)) {
            mWorkStationListAdapter = new WorkStationListAdapter(workStationInfoList);
            rv_work_station.setAdapter(mWorkStationListAdapter);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            rv_work_station.setLayoutManager(layoutManager);
            rv_work_station.addItemDecoration(new GridItemDecoration(0, 3));
            rv_work_station.setHasFixedSize(true);
            rv_work_station.setNestedScrollingEnabled(false);

            mWorkStationListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    WorkStationInfo workStationInfo = workStationInfoList.get(position);
                    String type = workStationInfoList.get(position).getWork_type();
                    switch (type) {
                        case "1":
                            CompanyInfoManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "2":
                            CompanyAccountManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "3":
                            CompanySupplierManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "4":
                            CompanyProductManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "5":
                            CompanyClientManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "6":
                            CompanyContactManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "7":
                            CompanyTradingOrderManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "8":
                            CompanyQuotationManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "9":
                            CompanySalesOrderManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "10":
                            CompanyDeliveryOrderManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "11":
                            CompanyPaymentManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "12":
                            CompanyServiceRecordManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "13":
                            CompanyInventoryManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "14":
                            CompanyPurchasingManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "15":
                            CompanyTaskManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "16":
                            CompanyPdfReportManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "17":
                            CompanyPackagingDataManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "18":
                            CompanyStatisticalFormActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "19":
                            String roleCode = UserInfoManager.getUserLoginInfo(MainActivity.this).getRoleCode();
                            if (roleCode.equals("companyRoot")) {
                                CompanyExpenditureManagementActivityA.startActivity(MainActivity.this, workStationInfo);
                            } else if (roleCode.equals("companySales")) {
                                CompanyExpenditureManagementActivityB.startActivity(MainActivity.this, workStationInfo);
                            }
                            break;
                        case "20":
                            SystemManagementActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                        case "21":
                            SettingActivity.startActivity(MainActivity.this, workStationInfo);
                            break;
                    }
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, R.string.exit_app, Toast.LENGTH_SHORT).show();
                firstTime = secondTime;//更新firstTime  
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
