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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyContactEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteContactInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyContactInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteContactInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.ContactListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.ContactManagementContract;
import com.africa.crm.businessmanagement.main.station.presenter.ContactManagementPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.FROM_TYPE_CODE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_CONTACT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_CONTACT_FROM_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_CONTACT;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyContactManagementActivity extends BaseRefreshMvpActivity<ContactManagementPresenter> implements ContactManagementContract.View {
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
    private ContactListAdapter mContactListAdapter;
    private List<CompanyContactInfo> mDeleteList = new ArrayList<>();
    private List<CompanyContactInfo> mContactListDatas = new ArrayList<>();


    @BindView(R.id.spinner_from_type)
    MySpinner spinner_from_type;
    private List<DicInfo> mFromTypeList = new ArrayList<>();
    private String mFromType = "";

    private WorkStationInfo mWorkStationInfo;
    private String mCompanyId = "";
    private AlertDialog mDeleteDialog;
    private boolean mShowCheckBox = false;

    private GreendaoManager<CompanyContactInfo, CompanyContactInfoDao> mContactInfoDaoManager;
    private GreendaoManager<CompanyDeleteContactInfo, CompanyDeleteContactInfoDao> mDeleteContactInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanyContactInfo> mCompanyContactLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    private String mRoleCode = "";//角色code

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyContactManagementActivity.class);
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
        setContentView(R.layout.activity_company_contact_list);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (mRoleCode.equals("companySales")) {
            ll_add.setVisibility(View.VISIBLE);
            titlebar_right.setVisibility(View.VISIBLE);
        } else {
            ll_add.setVisibility(View.GONE);
            titlebar_right.setVisibility(View.GONE);
        }
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_search.setOnClickListener(this);

        //得到Dao对象管理器
        mContactInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyContactInfoDao());
        //得到Dao对象管理器
        mDeleteContactInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeleteContactInfoDao());
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
                    spinner_from_type.setText("");
                    mFromType = "";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
    }

    @Override
    public void initData() {
        mContactListAdapter = new ContactListAdapter(mContactListDatas);
        recyclerView.setAdapter(mContactListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    protected ContactManagementPresenter injectPresenter() {
        return new ContactManagementPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getFromType(FROM_TYPE_CODE);
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        String userId = "";
        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            userId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        }
        mPresenter.getCompanyContactList(page, rows, mCompanyId, userId, et_name.getText().toString().trim(), mFromType);
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
                if (mContactListAdapter != null) {
                    mContactListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyContactDetailActivity.startActivity(CompanyContactManagementActivity.this, "", 0l);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyContactInfo companyContactInfo : mContactListDatas) {
                    if (companyContactInfo.isChosen()) {
                        mDeleteList.add(companyContactInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg(getString(R.string.no_choose_delete));
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyContactManagementActivity.this)
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
                                    mPresenter.deleteCompanyContact(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public void getFromType(List<DicInfo> dicInfoList) {
        mFromTypeList.clear();
        mFromTypeList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mFromTypeList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(FROM_TYPE_CODE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_from_type.setListDatas(this, mFromTypeList);

        spinner_from_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mFromType = dicInfo.getCode();
/*
                if (!TextUtils.isEmpty(mFromType)) {
                    et_name.setText("");
                }
*/
            }
        });
    }

    @Override
    public void getCompanyContactList(CompanyContactInfoBean companyContactInfoBean) {
        if (companyContactInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyContactInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
//                    KeyboardUtil.clearInputBox(et_name);
//                    spinner_from_type.setText("");
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mContactListDatas.clear();
                mCompanyContactLocalList = mContactInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyContactInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyContactInfoBean.getRows())) {
                mContactListDatas.addAll(companyContactInfoBean.getRows());
                List<CompanyContactInfo> addList = DifferentDataUtil.addContactDataToLocal(mContactListDatas, mCompanyContactLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyContactInfo companyInfo : addList) {
                        mContactInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mCompanyContactLocalList = new ArrayList<>();
                    mCompanyContactLocalList = mContactInfoDaoManager.queryAll();
                }
                for (CompanyContactInfo info : mContactListDatas) {
                    for (CompanyContactInfo localInfo : mCompanyContactLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mContactInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mContactListAdapter != null) {
                    mContactListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mContactListDatas)) {
                mContactListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mContactListDatas.get(position).setChosen(!cb_choose.isChecked());
                            mContactListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyContactDetailActivity.startActivity(CompanyContactManagementActivity.this, mContactListDatas.get(position).getId(), mContactListDatas.get(position).getLocalId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteCompanyContact(BaseEntity baseEntity, boolean isLocal) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mContactListDatas.contains(mDeleteList.get(i))) {
                    int position = mContactListDatas.indexOf(mDeleteList.get(i));
                    mContactListDatas.remove(mDeleteList.get(i));
                    if (isLocal) {
                        for (CompanyContactInfo companyInfo : mDeleteList) {
                            CompanyDeleteContactInfo deleteAccountInfo = new CompanyDeleteContactInfo(companyInfo.getId(), companyInfo.getCreateTime(), companyInfo.getAddress(), companyInfo.getCompanyName(), companyInfo.getMailAddress(), companyInfo.getRemark(), companyInfo.getUserId(), companyInfo.getFromTypeName(), companyInfo.getHead(), companyInfo.getCompanyId(), companyInfo.getFromType(), companyInfo.getPhone(), companyInfo.getName(), companyInfo.getTel(), companyInfo.getUserNickName(), companyInfo.getJob(), companyInfo.getEmail(), false, isLocal);
                            mDeleteContactInfoDaoManager.insertOrReplace(deleteAccountInfo);
                        }
                    }
                    for (CompanyContactInfo companyInfo : mCompanyContactLocalList) {
                        if (mDeleteList.get(i).getLocalId().equals(companyInfo.getLocalId())) {
                            mContactInfoDaoManager.delete(companyInfo.getLocalId());
                        }
                    }
                    mCompanyContactLocalList = new ArrayList<>();
                    mCompanyContactLocalList = mContactInfoDaoManager.queryAll();
                    if (mContactListAdapter != null) {
                        mContactListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mContactListDatas)) {
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
    public void Event(AddOrSaveCompanyContactEvent addOrSaveCompanyContactEvent) {
        toastMsg(addOrSaveCompanyContactEvent.getMsg());
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
            case REQUEST_CONTACT_FROM_TYPE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(FROM_TYPE_CODE)) {
                        stateList.add(dicInfo);
                    }
                }
                getFromType(stateList);
                break;
            case REQUEST_COMPANY_CONTACT_LIST:
                List<CompanyContactInfo> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(StringUtil.getText(et_name)) || !TextUtils.isEmpty(mFromType)) {
                    if (!TextUtils.isEmpty(mFromType)) {
                        rows = mContactInfoDaoManager.queryBuilder().where(CompanyContactInfoDao.Properties.Name.like("%" + StringUtil.getText(et_name) + "%"), CompanyContactInfoDao.Properties.FromType.eq(mFromType)).list();
                    } else {
                        rows = mContactInfoDaoManager.queryBuilder().where(CompanyContactInfoDao.Properties.Name.like("%" + StringUtil.getText(et_name) + "%")).list();
                    }
                } else {
                    rows = mContactInfoDaoManager.queryAll();
                }
                CompanyContactInfoBean companyContactInfoBean = new CompanyContactInfoBean();
                companyContactInfoBean.setRows(rows);
                getCompanyContactList(companyContactInfoBean);
                break;
            case REQUEST_DELETE_COMPANY_CONTACT:
                BaseEntity baseEntity = new BaseEntity();
                baseEntity.setSuccess(true);
                deleteCompanyContact(baseEntity, true);
                break;
        }
    }
}
