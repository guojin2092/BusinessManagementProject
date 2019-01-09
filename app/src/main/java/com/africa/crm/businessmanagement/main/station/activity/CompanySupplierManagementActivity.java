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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanySupplierEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteSupplierInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteSupplierInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanySupplierInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.CompanySupplierListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanySupplierContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanySupplierPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.SUPPLIER_TYPE_CODE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_SUPPLIER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_SUPPLIER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SUPPLIER_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/27 0027 16:10
 * Modification  History:
 * Why & What is modified:
 */
public class CompanySupplierManagementActivity extends BaseRefreshMvpActivity<CompanySupplierPresenter> implements CompanySupplierContract.View {
    @BindView(R.id.et_supplier_name)
    EditText et_supplier_name;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CompanySupplierListAdapter mCompanySupplierListAdapter;
    private List<CompanySupplierInfo> mDeleteList = new ArrayList<>();
    private List<CompanySupplierInfo> mCompanySupplierInfoList = new ArrayList<>();


    @BindView(R.id.spinner_supplier_type)
    MySpinner spinner_supplier_type;
    private List<DicInfo> mSpinnerSupplierTypeList = new ArrayList<>();
    private String mSupplierType = "";

    private boolean mShowCheckBox = false;
    private WorkStationInfo mWorkStationInfo;
    private AlertDialog mDeleteDialog;
    private String mCompanyId = "";

    private GreendaoManager<CompanySupplierInfo, CompanySupplierInfoDao> mSupplierInfoDaoManager;
    private GreendaoManager<CompanyDeleteSupplierInfo, CompanyDeleteSupplierInfoDao> mDeleteInfoGreendaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanySupplierInfo> mCompanySupplierLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanySupplierManagementActivity.class);
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
        setContentView(R.layout.activity_company_supplier_management);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        //得到Dao对象管理器
        mSupplierInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanySupplierInfoDao());
        //得到Dao对象管理器
        mDeleteInfoGreendaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteSupplierInfoDao());
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
    }

    @Override
    public void initData() {
        mCompanySupplierListAdapter = new CompanySupplierListAdapter(mCompanySupplierInfoList);
        recyclerView.setAdapter(mCompanySupplierListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected CompanySupplierPresenter injectPresenter() {
        return new CompanySupplierPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getSupplierType(SUPPLIER_TYPE_CODE);
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        mPresenter.getCompanySupplierList(page, rows, mCompanyId, et_supplier_name.getText().toString().trim(), mSupplierType);
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
                if (titlebar_right.getText().toString().equals(getString(R.string.delete))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_delete.setVisibility(View.VISIBLE);
                    mShowCheckBox = true;
                } else {
                    titlebar_right.setText(R.string.delete);
                    tv_delete.setVisibility(View.GONE);
                    mShowCheckBox = false;
                }
                if (mCompanySupplierListAdapter != null) {
                    mCompanySupplierListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanySupplierDetailActivity.startActivity(CompanySupplierManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanySupplierInfo companySupplierInfo : mCompanySupplierInfoList) {
                    if (companySupplierInfo.isChosen()) {
                        mDeleteList.add(companySupplierInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanySupplierManagementActivity.this)
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
                                    mPresenter.deleteCompanySupplier(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void getSupplierType(List<DicInfo> dicInfoList) {
        mSpinnerSupplierTypeList.clear();
        mSpinnerSupplierTypeList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerSupplierTypeList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(SUPPLIER_TYPE_CODE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_supplier_type.setListDatas(this, mSpinnerSupplierTypeList);

        spinner_supplier_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mSupplierType = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanySupplierList(CompanySupplierInfoBean companySupplierInfoBean) {
        if (companySupplierInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companySupplierInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
//                    KeyboardUtil.clearInputBox(et_supplier_name);
//                    spinner_supplier_type.setText("");
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mCompanySupplierInfoList.clear();
                mCompanySupplierLocalList = mSupplierInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companySupplierInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companySupplierInfoBean.getRows())) {
                mCompanySupplierInfoList.addAll(companySupplierInfoBean.getRows());
                List<CompanySupplierInfo> addList = DifferentDataUtil.addSupplierDataToLocal(mCompanySupplierInfoList, mCompanySupplierLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanySupplierInfo companyInfo : addList) {
                        mSupplierInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mCompanySupplierLocalList = new ArrayList<>();
                    mCompanySupplierLocalList = mSupplierInfoDaoManager.queryAll();
                }
                for (CompanySupplierInfo info : mCompanySupplierInfoList) {
                    for (CompanySupplierInfo localInfo : mCompanySupplierLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mSupplierInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mCompanySupplierListAdapter != null) {
                    mCompanySupplierListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mCompanySupplierInfoList)) {
                mCompanySupplierListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mCompanySupplierInfoList.get(position).setChosen(!cb_choose.isChecked());
                            mCompanySupplierListAdapter.notifyDataSetChanged();
                        } else {
                            CompanySupplierDetailActivity.startActivity(CompanySupplierManagementActivity.this, mCompanySupplierInfoList.get(position).getId(), mCompanySupplierInfoList.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteCompanySupplier(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mCompanySupplierInfoList.contains(mDeleteList.get(i))) {
                    int position = mCompanySupplierInfoList.indexOf(mDeleteList.get(i));
                    mCompanySupplierInfoList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanySupplierInfo companyInfo : mDeleteList) {
                            CompanyDeleteSupplierInfo deleteInfo = new CompanyDeleteSupplierInfo(companyInfo.getId(), companyInfo.getArea(), companyInfo.getZipCode(), companyInfo.getAddress(), companyInfo.getCompanyName(), companyInfo.getTypeName(), companyInfo.getRemark(), companyInfo.getType(), companyInfo.getHead(), companyInfo.getCompanyId(), companyInfo.getCreateTime(), companyInfo.getPhone(), companyInfo.getName(), companyInfo.getEmail(), false, true);
                            mDeleteInfoGreendaoManager.insertOrReplace(deleteInfo);
                        }
                    }
                    for (CompanySupplierInfo companyInfo : mCompanySupplierLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mSupplierInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mCompanySupplierLocalList = new ArrayList<>();
                    mCompanySupplierLocalList = mSupplierInfoDaoManager.queryAll();
                    if (mCompanySupplierListAdapter != null) {
                        mCompanySupplierListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mCompanySupplierInfoList)) {
                titlebar_right.setText(R.string.delete);
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
    public void Event(AddOrSaveCompanySupplierEvent addOrSaveCompanySupplierEvent) {
        toastMsg(addOrSaveCompanySupplierEvent.getMsg());
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
            case REQUEST_SUPPLIER_TYPE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(SUPPLIER_TYPE_CODE)) {
                        stateList.add(dicInfo);
                    }
                }
                getSupplierType(stateList);
                break;
            case REQUEST_COMPANY_SUPPLIER_LIST:
                List<CompanySupplierInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(StringUtil.getText(et_supplier_name)) || !TextUtils.isEmpty(spinner_supplier_type.getText())) {
                    rows = mSupplierInfoDaoManager.queryBuilder().where(CompanySupplierInfoDao.Properties.Name.like("%" + StringUtil.getText(et_supplier_name) + "%"), CompanySupplierInfoDao.Properties.Type.eq(mSupplierType)).list();
                } else {
                    rows = mSupplierInfoDaoManager.queryAll();
                }
                CompanySupplierInfoBean companySupplierInfoBean = new CompanySupplierInfoBean();
                companySupplierInfoBean.setRows(rows);
                getCompanySupplierList(companySupplierInfoBean);
                break;
            case REQUEST_DELETE_COMPANY_SUPPLIER:
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setSuccess(true);
                deleteCompanySupplier(baseEntity, true);
                break;
        }
    }
}
