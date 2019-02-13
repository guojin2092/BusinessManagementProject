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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPdfEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeletePdfInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyDeletePdfInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyPdfInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.PdfReportListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPdfReportManagementContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPdfManagementPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_USERS;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PDF_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_PDF;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPdfReportManagementActivity extends BaseRefreshMvpActivity<CompanyPdfManagementPresenter> implements CompanyPdfReportManagementContract.View {
    @BindView(R.id.et_file_name)
    EditText et_file_name;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_delete)
    TextView tv_delete;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PdfReportListAdapter mPdfReportListAdapter;
    private List<CompanyPdfInfo> mDeleteList = new ArrayList<>();
    private List<CompanyPdfInfo> mPdfInfoBeanList = new ArrayList<>();

    @BindView(R.id.spinner_user)
    MySpinner spinner_user;
    private List<DicInfo> mSpinnerCompanyUserList = new ArrayList<>();
    private String mFromUserId = "";
    private String mFromName = "";
    private String mUserId = "";

    private WorkStationInfo mWorkStationInfo;
    private AlertDialog mDeleteDialog;
    private boolean mShowCheckBox = false;
    private String mCompanyId = "";
    private String mRoleCode = "";

    private GreendaoManager<CompanyPdfInfo, CompanyPdfInfoDao> mPdfInfoDaoManager;
    private GreendaoManager<CompanyDeletePdfInfo, CompanyDeletePdfInfoDao> mDeletePdfInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanyPdfInfo> mPdfInfoLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyPdfReportManagementActivity.class);
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
        setContentView(R.layout.activity_pdf_report_management);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        ll_add.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_delete.setOnClickListener(this);

        if (mRoleCode.equals("companyRoot")) {
            spinner_user.setVisibility(View.VISIBLE);
        } else {
            spinner_user.setVisibility(View.GONE);
        }
        //得到Dao对象管理器
        mPdfInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyPdfInfoDao());
        //得到Dao对象管理器
        mDeletePdfInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeletePdfInfoDao());
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();

    }

    @Override
    protected CompanyPdfManagementPresenter injectPresenter() {
        return new CompanyPdfManagementPresenter();
    }

    @Override
    protected void requestData() {
        if ("companyRoot".equals(mRoleCode)) {
            mPresenter.getAllCompanyUsers(mCompanyId);
        }
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        if (mRoleCode.equals("companySales")) {
            mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        } else {
            mUserId = mFromUserId;
        }
        mPresenter.getCompanyPdfList(page, rows, mCompanyId, mUserId, et_file_name.getText().toString().trim());
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
                if (mPdfReportListAdapter != null) {
                    mPdfReportListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyPdfReportDetailActivity.startActivity(CompanyPdfReportManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyPdfInfo companyPdfInfo : mPdfInfoBeanList) {
                    if (companyPdfInfo.isChosen()) {
                        mDeleteList.add(companyPdfInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg(getString(R.string.no_choose_delete));
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyPdfReportManagementActivity.this)
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
                                    mPresenter.deleteCompanyPdf(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public void initData() {
        mPdfReportListAdapter = new PdfReportListAdapter(mPdfInfoBeanList);
        recyclerView.setAdapter(mPdfReportListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void getAllCompanyUsers(List<DicInfo2> dicInfo2List) {
        mSpinnerCompanyUserList.clear();
        if (!ListUtils.isEmpty(dicInfo2List)) {
            for (DicInfo2 dicInfo2 : dicInfo2List) {
                DicInfo dicInfo = new DicInfo(dicInfo2.getId(), QUERY_ALL_USERS, dicInfo2.getName(), dicInfo2.getCode());
                mSpinnerCompanyUserList.add(dicInfo);
            }
            List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerCompanyUserList, mDicInfoLocalList);
            for (DicInfo dicInfo : addList) {
                dicInfo.setType(QUERY_ALL_USERS);
                mDicInfoDaoManager.insertOrReplace(dicInfo);
            }
            spinner_user.setListDatas(getBVActivity(), mSpinnerCompanyUserList);
            spinner_user.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mFromUserId = dicInfo.getCode();
                    mFromName = dicInfo.getText();
                }
            });
        }
    }

    @Override
    public void getCompanyPdfList(CompanyPdfInfoBean companyPdfInfoBean) {
        if (companyPdfInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyPdfInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mPdfInfoBeanList.clear();
                mPdfInfoLocalList = mPdfInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyPdfInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyPdfInfoBean.getRows())) {
                mPdfInfoBeanList.addAll(companyPdfInfoBean.getRows());
                List<CompanyPdfInfo> addList = DifferentDataUtil.addPdfOrderDataToLocal(mPdfInfoBeanList, mPdfInfoLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyPdfInfo companyInfo : addList) {
                        mPdfInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mPdfInfoLocalList = new ArrayList<>();
                    mPdfInfoLocalList = mPdfInfoDaoManager.queryAll();
                }
                for (CompanyPdfInfo info : mPdfInfoBeanList) {
                    for (CompanyPdfInfo localInfo : mPdfInfoLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mPdfInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mPdfReportListAdapter != null) {
                    mPdfReportListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mPdfInfoBeanList)) {
                mPdfReportListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mPdfInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                            mPdfReportListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyPdfReportDetailActivity.startActivity(CompanyPdfReportManagementActivity.this, mPdfInfoBeanList.get(position).getId(), mPdfInfoBeanList.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteCompanyPdf(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mPdfInfoBeanList.contains(mDeleteList.get(i))) {
                    int position = mPdfInfoBeanList.indexOf(mDeleteList.get(i));
                    mPdfInfoBeanList.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyPdfInfo companyInfo : mDeleteList) {
                            CompanyDeletePdfInfo deleteDeliveryOrderInfo = new CompanyDeletePdfInfo(companyInfo.getId(), companyInfo.getCreateTime(), companyInfo.getCreateTimeDate(), companyInfo.getRemark(), companyInfo.getEditAble(), companyInfo.getName(), companyInfo.getUserId(), companyInfo.getCode(), companyInfo.getCompanyId(), companyInfo.getCompanyName(), companyInfo.getUserNickName(), false, isLocal);
                            mDeletePdfInfoDaoManager.insertOrReplace(deleteDeliveryOrderInfo);
                        }
                    }
                    for (CompanyPdfInfo companyInfo : mPdfInfoLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mPdfInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mPdfInfoLocalList = new ArrayList<>();
                    mPdfInfoLocalList = mPdfInfoDaoManager.queryAll();
                    if (mPdfReportListAdapter != null) {
                        mPdfReportListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mPdfInfoBeanList)) {
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
    public void Event(AddOrSaveCompanyPdfEvent addOrSaveCompanyPdfEvent) {
        toastMsg(addOrSaveCompanyPdfEvent.getMsg());
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
            case REQUEST_ALL_USERS_LIST:
                List<DicInfo2> allUserList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_USERS)) {
                        allUserList.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllCompanyUsers(allUserList);
                break;
            case REQUEST_COMPANY_PDF_LIST:
                List<CompanyPdfInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(StringUtil.getText(et_file_name)) || !TextUtils.isEmpty(mFromUserId)) {
                    if (!TextUtils.isEmpty(mFromUserId)) {
                        rows = mPdfInfoDaoManager.queryBuilder().where(CompanyPdfInfoDao.Properties.Name.like("%" + StringUtil.getText(et_file_name) + "%"), CompanyPdfInfoDao.Properties.UserNickName.eq(mFromName)).list();
                    } else {
                        rows = mPdfInfoDaoManager.queryBuilder().where(CompanyPdfInfoDao.Properties.Name.like("%" + StringUtil.getText(et_file_name) + "%")).list();
                    }
                } else {
                    rows = mPdfInfoDaoManager.queryAll();
                }
                CompanyPdfInfoBean companyPdfInfoBean = new CompanyPdfInfoBean();
                companyPdfInfoBean.setRows(rows);
                getCompanyPdfList(companyPdfInfoBean);
                break;
            case REQUEST_DELETE_COMPANY_PDF:
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setSuccess(true);
                deleteCompanyPdf(baseEntity, true);
                break;
        }
    }
}
