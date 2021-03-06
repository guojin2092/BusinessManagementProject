package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.station.adapter.CompanyInfoListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyInfoManagementContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyInfoManagementPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseRefreshMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.KeyboardUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_INFO_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_INFO;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/26 0026 16:05
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInfoManagementActivity extends BaseRefreshMvpActivity<CompanyInfoManagementPresenter> implements CompanyInfoManagementContract.View {
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private CompanyInfoListAdapter mCompanyInfoListAdapter;
    private List<CompanyInfo> mCompanyInfoList = new ArrayList<>();//网络数据
    private List<CompanyInfo> mDeleteList = new ArrayList<>();
    private boolean mShowCheckBox = false;

    private WorkStationInfo mWorkStationInfo;
    private AlertDialog mDeleteDialog;

    private GreendaoManager<CompanyInfo, CompanyInfoDao> mGreendaoManager;
    private GreendaoManager<CompanyDeleteInfo, CompanyDeleteInfoDao> mDeleteInfoGreendaoManager;

    private List<CompanyInfo> mCompanyInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyInfoManagementActivity.class);
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
        setContentView(R.layout.activity_company_info_management);
    }

    @Override
    public void initView() {
        super.initView();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        et_search.setHint(R.string.Please_fill_in_the_name);
        tv_search.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        titlebar_right.setText(R.string.Delete);
        //得到Dao对象管理器
        mGreendaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyInfoDao());
        //得到Dao对象管理器
        mDeleteInfoGreendaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteInfoDao());
    }

    @Override
    protected CompanyInfoManagementPresenter injectPresenter() {
        return new CompanyInfoManagementPresenter();
    }

    @Override
    protected void requestData() {
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        mPresenter.getCompanyInfoList(page, rows, et_search.getText().toString().trim());
    }

    @Override
    public void initData() {
        mCompanyInfoListAdapter = new CompanyInfoListAdapter(mCompanyInfoList);
        recyclerView.setAdapter(mCompanyInfoListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    page = 1;
                    pullDownRefresh(page);
                    KeyboardUtil.hideKeyboard(et_search);
                    return true;
                }
                return false;
            }
        });
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
                if (mCompanyInfoListAdapter != null) {
                    mCompanyInfoListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyInfoDetailActivity.startActivity(CompanyInfoManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyInfo companyInfo : mCompanyInfoList) {
                    if (companyInfo.isChosen()) {
                        mDeleteList.add(companyInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg(getString(R.string.no_choose_delete));
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyInfoManagementActivity.this)
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
                                    mPresenter.deleteCompanyInfo(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void getCompanyInfoList(CompanyInfoBean companyInfoBean) {
        if (companyInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
//                    KeyboardUtil.clearInputBox(et_search);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mCompanyInfoList.clear();
                mCompanyInfoLocalList = mGreendaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyInfoBean.getRows())) {
                mCompanyInfoList.addAll(companyInfoBean.getRows());
                List<CompanyInfo> addList = DifferentDataUtil.addInfoDataToLocal(mCompanyInfoList, mCompanyInfoLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyInfo companyInfo : addList) {
                        mGreendaoManager.insertOrReplace(companyInfo);
                    }
                    mCompanyInfoLocalList = new ArrayList<>();
                    mCompanyInfoLocalList = mGreendaoManager.queryAll();
                }
                for (CompanyInfo info : mCompanyInfoList) {
                    for (CompanyInfo localInfo : mCompanyInfoLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mGreendaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mCompanyInfoListAdapter != null) {
                    mCompanyInfoListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mCompanyInfoList)) {
                mCompanyInfoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mCompanyInfoList.get(position).setChosen(!cb_choose.isChecked());
                            mCompanyInfoListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyInfoDetailActivity.startActivity(CompanyInfoManagementActivity.this, mCompanyInfoList.get(position).getId(), mCompanyInfoList.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteCompanyInfo(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mCompanyInfoList.contains(mDeleteList.get(i))) {
                    int position = mCompanyInfoList.indexOf(mDeleteList.get(i));
                    mCompanyInfoList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyInfo companyInfo : mDeleteList) {
                            CompanyDeleteInfo deleteInfo = new CompanyDeleteInfo(companyInfo.getArea(), companyInfo.getProfession(), companyInfo.getCode(), companyInfo.getAddress(), companyInfo.getNumA(), companyInfo.getMid(), companyInfo.getType(), companyInfo.getTypeName(), companyInfo.getHead(), companyInfo.getCreateTime(), companyInfo.getPhone(), companyInfo.getName(), companyInfo.getId(), companyInfo.getState(), companyInfo.getStateName(), companyInfo.getEmail(), false, true);
                            mDeleteInfoGreendaoManager.insertOrReplace(deleteInfo);
                        }
                    }
                    for (CompanyInfo companyInfo : mCompanyInfoLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mGreendaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mCompanyInfoLocalList = new ArrayList<>();
                    mCompanyInfoLocalList = mGreendaoManager.queryAll();
                    if (mCompanyInfoListAdapter != null) {
                        mCompanyInfoListAdapter.notifyItemRemoved(position);
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
    public void Event(AddOrSaveCompanyEvent addOrSaveCompanyEvent) {
        toastMsg(addOrSaveCompanyEvent.getMsg());
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
        if (port.equals(REQUEST_COMPANY_INFO_LIST)) {
            List<CompanyInfo> rows = new ArrayList<>();
            if (!TextUtils.isEmpty(StringUtil.getText(et_search))) {
                rows = mGreendaoManager.queryBuilder().where(CompanyInfoDao.Properties.Name.like("%" + StringUtil.getText(et_search) + "%")).list();
            } else {
                rows = mGreendaoManager.queryAll();
            }
            CompanyInfoBean companyInfoBean = new CompanyInfoBean();
            companyInfoBean.setRows(rows);
            getCompanyInfoList(companyInfoBean);
        } else if (port.equals(REQUEST_DELETE_COMPANY_INFO)) {
            BaseEntity baseEntity = new BaseEntity();
            baseEntity.setSuccess(true);
            deleteCompanyInfo(baseEntity, true);
        }
    }
}
