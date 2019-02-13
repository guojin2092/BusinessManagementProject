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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyAccountEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteAccountInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.CompanyAccountListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyAccountContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyAccountPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseRefreshMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_ACCOUNT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_ACCOUNT;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/27 0027 9:04
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyAccountManagementActivity extends BaseRefreshMvpActivity<CompanyAccountPresenter> implements CompanyAccountContract.View {
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private CompanyAccountListAdapter mCompanyAccountListAdapter;
    private List<CompanyAccountInfo> mCompanyInfoList = new ArrayList<>();
    private List<CompanyAccountInfo> mDeleteList = new ArrayList<>();
    private boolean mShowCheckBox = false;

    private WorkStationInfo mWorkStationInfo;
    private AlertDialog mDeleteDialog;

    private String mCompanyId = "";

    private GreendaoManager<CompanyAccountInfo, CompanyAccountInfoDao> mAccountInfoDaoManager;
    private GreendaoManager<CompanyDeleteAccountInfo, CompanyDeleteAccountInfoDao> mDeleteAccountInfoDaoManager;
    private List<CompanyAccountInfo> mCompanyInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyAccountManagementActivity.class);
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
        setContentView(R.layout.activity_company_account_list);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        tv_search.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);

        //得到Dao对象管理器
        mAccountInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyAccountInfoDao());
        //得到Dao对象管理器
        mDeleteAccountInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteAccountInfoDao());

/*
        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    et_nickname.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/

/*
        et_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    et_account.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/

    }

    @Override
    protected CompanyAccountPresenter injectPresenter() {
        return new CompanyAccountPresenter();
    }

    @Override
    protected void requestData() {
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        mPresenter.getCompanyAccounList(page, rows, mCompanyId, et_account.getText().toString().trim(), et_nickname.getText().toString().trim());
    }

    @Override
    public void initData() {
        mCompanyAccountListAdapter = new CompanyAccountListAdapter(mCompanyInfoList);
        recyclerView.setAdapter(mCompanyAccountListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
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
                if (mCompanyAccountListAdapter != null) {
                    mCompanyAccountListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyAccountDetailActivity.startActivity(CompanyAccountManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyAccountInfo companyAccountInfo : mCompanyInfoList) {
                    if (companyAccountInfo.isChosen()) {
                        mDeleteList.add(companyAccountInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg(getString(R.string.no_choose_delete));
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyAccountManagementActivity.this)
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
                                    mPresenter.deleteCompanyAccount(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void getCompanyAccounList(CompanyAccountInfoBean companyAccountInfoBean) {
        if (companyAccountInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyAccountInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
//                    KeyboardUtil.clearInputBox(et_account);
//                    KeyboardUtil.clearInputBox(et_nickname);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mCompanyInfoList.clear();
                mCompanyInfoLocalList = mAccountInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyAccountInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyAccountInfoBean.getRows())) {
                mCompanyInfoList.addAll(companyAccountInfoBean.getRows());
                List<CompanyAccountInfo> addList = DifferentDataUtil.addAccountDataToLocal(mCompanyInfoList, mCompanyInfoLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyAccountInfo companyInfo : addList) {
                        mAccountInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mCompanyInfoLocalList = new ArrayList<>();
                    mCompanyInfoLocalList = mAccountInfoDaoManager.queryAll();
                }
                for (CompanyAccountInfo info : mCompanyInfoList) {
                    for (CompanyAccountInfo localInfo : mCompanyInfoLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mAccountInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mCompanyAccountListAdapter != null) {
                    mCompanyAccountListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mCompanyInfoList)) {
                mCompanyAccountListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mCompanyInfoList.get(position).setChosen(!cb_choose.isChecked());
                            mCompanyAccountListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyAccountDetailActivity.startActivity(CompanyAccountManagementActivity.this, mCompanyInfoList.get(position).getId(), mCompanyInfoList.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteCompanyAccount(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mCompanyInfoList.contains(mDeleteList.get(i))) {
                    int position = mCompanyInfoList.indexOf(mDeleteList.get(i));
                    mCompanyInfoList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyAccountInfo companyInfo : mDeleteList) {
                            CompanyDeleteAccountInfo deleteAccountInfo = new CompanyDeleteAccountInfo(companyInfo.getId(), companyInfo.getCreateTime(), companyInfo.getName(), companyInfo.getUserName(), companyInfo.getRoleId(), companyInfo.getRoleName(), companyInfo.getCompanyId(), companyInfo.getHead(), companyInfo.getCompanyName(), companyInfo.getType(), companyInfo.getTypeName(), companyInfo.getState(), companyInfo.getStateName(), companyInfo.getAddress(), companyInfo.getRoleTypeName(), companyInfo.getPhone(), companyInfo.getRoleCode(), companyInfo.getEmail(), false, true);
                            mDeleteAccountInfoDaoManager.insertOrReplace(deleteAccountInfo);
                        }
                    }
                    for (CompanyAccountInfo companyInfo : mCompanyInfoLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mAccountInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mCompanyInfoLocalList = new ArrayList<>();
                    mCompanyInfoLocalList = mAccountInfoDaoManager.queryAll();
                    if (mCompanyAccountListAdapter != null) {
                        mCompanyAccountListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mCompanyInfoList)) {
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
    public void Event(AddOrSaveCompanyAccountEvent addOrSaveCompanyAccountEvent) {
        toastMsg(addOrSaveCompanyAccountEvent.getMsg());
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
        if (port.equals(REQUEST_COMPANY_ACCOUNT_LIST)) {
            List<CompanyAccountInfo> rows = new ArrayList<>();
            if (!TextUtils.isEmpty(StringUtil.getText(et_account)) || !TextUtils.isEmpty(StringUtil.getText(et_nickname))) {
                WhereCondition condition = mAccountInfoDaoManager.queryBuilder().and(CompanyAccountInfoDao.Properties.UserName.like("%" + StringUtil.getText(et_account) + "%"), CompanyAccountInfoDao.Properties.Name.like("%" + StringUtil.getText(et_nickname) + "%"));
                rows = mAccountInfoDaoManager.queryBuilder().where(condition).list();
            } else {
                rows = mAccountInfoDaoManager.queryAll();
            }
            CompanyAccountInfoBean companyAccountInfoBean = new CompanyAccountInfoBean();
            companyAccountInfoBean.setRows(rows);
            getCompanyAccounList(companyAccountInfoBean);
        } else if (port.equals(REQUEST_DELETE_COMPANY_ACCOUNT)) {
            BaseEntity baseEntity = new BaseEntity();
            baseEntity.setSuccess(true);
            deleteCompanyAccount(baseEntity, true);
        }
    }
}
