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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyClientEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteClientInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyClientInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteClientInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.CompanyClientListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyClientContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyClientPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.INDUSTRY_CODE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_CLIENT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_CLIENT;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_INDUSTRY_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/14 0014 16:39
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyClientManagementActivity extends BaseRefreshMvpActivity<CompanyClientPresenter> implements CompanyClientContract.View {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_delete)
    TextView tv_delete;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CompanyClientListAdapter mCompanyClientListAdapter;
    private List<CompanyClientInfo> mDeleteList = new ArrayList<>();
    private List<CompanyClientInfo> mCompanyClientInfoList = new ArrayList<>();


    private AlertDialog mDeleteDialog;

    private WorkStationInfo mWorkStationInfo;
    private String mCompanyId = "";
    private String mUserId = "";
    private boolean mShowCheckBox = false;

    @BindView(R.id.spinner_industry)
    MySpinner spinner_industry;
    private List<DicInfo> mSpinnerCompanyIndustryList = new ArrayList<>();
    private String mIndustryType = "";

    private GreendaoManager<CompanyClientInfo, CompanyClientInfoDao> mClientInfoDaoManager;
    private GreendaoManager<CompanyDeleteClientInfo, CompanyDeleteClientInfoDao> mDeleteClientInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanyClientInfo> mCompanyClientLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyClientManagementActivity.class);
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
        setContentView(R.layout.activity_company_client_list);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        }
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_search.setOnClickListener(this);

        //得到Dao对象管理器
        mClientInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyClientInfoDao());
        //得到Dao对象管理器
        mDeleteClientInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteClientInfoDao());
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
                    spinner_industry.setText("");
                    mIndustryType = "";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
    }

    @Override
    protected CompanyClientPresenter injectPresenter() {
        return new CompanyClientPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getIndustryType(INDUSTRY_CODE);
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        mPresenter.getCompanyClientList(page, rows, mCompanyId, mUserId, et_name.getText().toString().trim(), mIndustryType);
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
                if (mCompanyClientListAdapter != null) {
                    mCompanyClientListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyClientDetailActivity.startActivity(CompanyClientManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyClientInfo companyClientInfo : mCompanyClientInfoList) {
                    if (companyClientInfo.isChosen()) {
                        mDeleteList.add(companyClientInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyClientManagementActivity.this)
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
                                    mPresenter.deleteCompanyClient(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void initData() {
        mCompanyClientListAdapter = new CompanyClientListAdapter(mCompanyClientInfoList);
        recyclerView.setAdapter(mCompanyClientListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void getIndustryType(List<DicInfo> dicInfoList) {
        mSpinnerCompanyIndustryList.clear();
        mSpinnerCompanyIndustryList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerCompanyIndustryList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(INDUSTRY_CODE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_industry.setListDatas(this, mSpinnerCompanyIndustryList);

        spinner_industry.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mIndustryType = dicInfo.getCode();
/*
                if (!TextUtils.isEmpty(mIndustryType)) {
                    et_name.setText("");
                }
*/
            }
        });
    }

    @Override
    public void getCompanyClientList(CompanyClientInfoBean companyClientInfoBean) {
        if (companyClientInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyClientInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
//                    KeyboardUtil.clearInputBox(et_name);
//                    spinner_industry.setText("");
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mCompanyClientInfoList.clear();
                mCompanyClientLocalList = mClientInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyClientInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyClientInfoBean.getRows())) {
                mCompanyClientInfoList.addAll(companyClientInfoBean.getRows());
                List<CompanyClientInfo> addList = DifferentDataUtil.addClientDataToLocal(mCompanyClientInfoList, mCompanyClientLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyClientInfo companyInfo : addList) {
                        mClientInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mCompanyClientLocalList = new ArrayList<>();
                    mCompanyClientLocalList = mClientInfoDaoManager.queryAll();
                }
                for (CompanyClientInfo info : mCompanyClientInfoList) {
                    for (CompanyClientInfo localInfo : mCompanyClientLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mClientInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mCompanyClientListAdapter != null) {
                    mCompanyClientListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mCompanyClientInfoList)) {
                mCompanyClientListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mCompanyClientInfoList.get(position).setChosen(!cb_choose.isChecked());
                            mCompanyClientListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyClientDetailActivity.startActivity(CompanyClientManagementActivity.this, mCompanyClientInfoList.get(position).getId(), mCompanyClientInfoList.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteCompanyClient(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mCompanyClientInfoList.contains(mDeleteList.get(i))) {
                    int position = mCompanyClientInfoList.indexOf(mDeleteList.get(i));
                    mCompanyClientInfoList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyClientInfo companyInfo : mDeleteList) {
                            CompanyDeleteClientInfo deleteAccountInfo = new CompanyDeleteClientInfo(companyInfo.getId(), companyInfo.getCreateTime(), companyInfo.getIndustryName(), companyInfo.getRemark(), companyInfo.getWorkerNum(), companyInfo.getTel(), companyInfo.getCompanyName(), companyInfo.getAddress(), companyInfo.getYearIncome(), companyInfo.getUserId(), companyInfo.getUserNickName(), companyInfo.getName(), companyInfo.getCompanyId(), companyInfo.getHead(), companyInfo.getIndustry(), false, true);
                            mDeleteClientInfoDaoManager.insertOrReplace(deleteAccountInfo);
                        }
                    }
                    for (CompanyClientInfo companyInfo : mCompanyClientLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mClientInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mCompanyClientLocalList = new ArrayList<>();
                    mCompanyClientLocalList = mClientInfoDaoManager.queryAll();
                    if (mCompanyClientListAdapter != null) {
                        mCompanyClientListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mCompanyClientInfoList)) {
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
    public void Event(AddOrSaveCompanyClientEvent addOrSaveCompanyClientEvent) {
        toastMsg(addOrSaveCompanyClientEvent.getMsg());
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
            case REQUEST_INDUSTRY_TYPE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(INDUSTRY_CODE)) {
                        stateList.add(dicInfo);
                    }
                }
                getIndustryType(stateList);
                break;
            case REQUEST_COMPANY_CLIENT_LIST:
                List<CompanyClientInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(StringUtil.getText(et_name)) || !TextUtils.isEmpty(mIndustryType)) {
                    if (!TextUtils.isEmpty(mIndustryType)) {
                        rows = mClientInfoDaoManager.queryBuilder().where(CompanyClientInfoDao.Properties.Name.like("%" + StringUtil.getText(et_name) + "%"), CompanyClientInfoDao.Properties.Industry.eq(mIndustryType)).list();
                    } else {
                        rows = mClientInfoDaoManager.queryBuilder().where(CompanyClientInfoDao.Properties.Name.like("%" + StringUtil.getText(et_name) + "%")).list();
                    }
                } else {
                    rows = mClientInfoDaoManager.queryAll();
                }
                CompanyClientInfoBean companyClientInfoBean = new CompanyClientInfoBean();
                companyClientInfoBean.setRows(rows);
                getCompanyClientList(companyClientInfoBean);
                break;
            case REQUEST_DELETE_COMPANY_CLIENT:
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setSuccess(true);
                deleteCompanyClient(baseEntity, true);
                break;
        }
    }
}
