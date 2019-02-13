package com.africa.crm.businessmanagement.main.station.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.baseutil.library.util.NetUtil;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleLimitInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleManagementInfoBean;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.RoleInfoBeanDao;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.RoleListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.RoleManagementContract;
import com.africa.crm.businessmanagement.main.station.dialog.RoleAuthLimitDialog;
import com.africa.crm.businessmanagement.main.station.dialog.RoleDetailDialog;
import com.africa.crm.businessmanagement.main.station.presenter.RoleManagementPresenter;
import com.africa.crm.businessmanagement.mvp.fragment.BaseRefreshMvpFragment;
import com.africa.crm.businessmanagement.network.error.ComException;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_SYSTEM_ROLE_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_SYSTEM_ROLE_LIST;


/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/25 0025 14:53
 * Modification  History:
 * Why & What is modified:
 */
public class RoleManagementFragment extends BaseRefreshMvpFragment<RoleManagementPresenter> implements RoleManagementContract.View {
    private View mRootView;
    @BindView(R.id.et_search_roleName)
    EditText et_search_roleName;
    @BindView(R.id.et_search_roleCode)
    EditText et_search_roleCode;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RoleListAdapter mRoleListAdapter;
    private List<RoleInfoBean> mRoleList = new ArrayList<>();
    private Long mRoleDetailLocalId = 0l;

    private RoleDetailDialog mRoleDetailDialog;
    private RoleDetailDialog mAddRoleDialog;
    private String mType = "";//1是创建 2是修改

    private RoleAuthLimitDialog mRoleAuthLimitDialog;
    private String mUserId = "";
    private List<RoleLimitInfoBean> mRoleLimitInfoBeanList = new ArrayList<>();

    private String mLimitRoleId = "";
    private String mResourceIds = "";
    private String mBtnIds = "";

    private GreendaoManager<RoleInfoBean, RoleInfoBeanDao> mRoleInfoBeanDaoManager;
    private List<RoleInfoBean> mRoleInfoBeanLocalList = new ArrayList<>();//本地数据

    public static RoleManagementFragment newInstance() {
        RoleManagementFragment roleManagementFragment = new RoleManagementFragment();
        return roleManagementFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_role_management, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(getBVActivity()).getId());
    }

    @Override
    public void initView() {
        tv_search.setOnClickListener(this);
//        ll_add.setOnClickListener(this);
        ll_add.setVisibility(View.GONE);

        //得到Dao对象管理器
        mRoleInfoBeanDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getRoleInfoBeanDao());
/*
        et_search_roleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    et_search_roleCode.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/

/*
        et_search_roleCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    et_search_roleName.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_search:
             /*   if (TextUtils.isEmpty(et_search_roleName.getText().toString().trim()) && TextUtils.isEmpty(et_search_roleCode.getText().toString().trim()) && TextUtils.isEmpty(spinner_role_type.getText())) {
                    toastMsg("请输入查询条件");
                    return;
                }*/
                page = 1;
                pullDownRefresh(page);
                break;
            case R.id.ll_add:
                mType = "1";
                mAddRoleDialog = RoleDetailDialog.getInstance(getBVActivity(), new RoleInfoBean());
                mAddRoleDialog.isCancelableOnTouchOutside(false)
                        .withDuration(300)
                        .withEffect(Effectstype.Fadein)
                        .setCancelClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mAddRoleDialog.dismiss();
                            }
                        })
                        .show();
                mAddRoleDialog.addOnSaveClickListener(new RoleDetailDialog.OnSaveClickListener() {
                    @Override
                    public void onSaveClick(RoleInfoBean roleInfoBean) {
                        if (roleInfoBean != null) {
                            if (TextUtils.isEmpty(roleInfoBean.getRoleName())) {
                                toastMsg(getString(R.string.Please_fill_in_the_role_name));
                                return;
                            }
                            if (TextUtils.isEmpty(roleInfoBean.getRoleCode())) {
                                toastMsg(getString(R.string.Please_fill_in_the_role_ID));
                                return;
                            }
                            if (TextUtils.isEmpty(roleInfoBean.getTypeName())) {
                                toastMsg(getString(R.string.Please_fill_in_the_class_name));
                                return;
                            }
                            if (TextUtils.isEmpty(roleInfoBean.getOrderNum())) {
                                toastMsg(getString(R.string.Please_fill_in_the_sequence));
                                return;
                            }
                            mPresenter.saveRoleInfo(mUserId, "", roleInfoBean.getRoleName(), roleInfoBean.getRoleCode(), roleInfoBean.getTypeName(), roleInfoBean.getOrderNum());
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected RoleManagementPresenter initPresenter() {
        return new RoleManagementPresenter();
    }

    @Override
    protected void requestData() {
        pullDownRefresh(page);
    }

    @Override
    protected void pullDownRefresh(int page) {
        mPresenter.getRoleList(page, rows, et_search_roleName.getText().toString().trim(), et_search_roleCode.getText().toString().trim(), "");
    }

    @Override
    protected View getFragmentProgress() {
        return mRootView;
    }

    @Override
    public void initData() {
        mRoleListAdapter = new RoleListAdapter(mRoleList);
        recyclerView.setAdapter(mRoleListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(getActivity(), R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void getRoleList(RoleManagementInfoBean roleManagementInfoBean) {
        if (page == 1) {
            if (ListUtils.isEmpty(roleManagementInfoBean.getRows())) {
                layout_network_error.setVisibility(View.GONE);
                mRefreshLayout.getLayout().setVisibility(View.GONE);
                layout_no_data.setVisibility(View.VISIBLE);
                return;
            } else {
                layout_no_data.setVisibility(View.GONE);
                layout_network_error.setVisibility(View.GONE);
                mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
            }
            mRoleList.clear();
            mRoleInfoBeanLocalList = mRoleInfoBeanDaoManager.queryAll();
            recyclerView.smoothScrollToPosition(0);
        }
        if (mRefreshLayout != null) {
            if (ListUtils.isEmpty(roleManagementInfoBean.getRows()) && page > 1) {
                mRefreshLayout.finishLoadmoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadmore();
            }
        }
        if (!ListUtils.isEmpty(roleManagementInfoBean.getRows())) {
            mRoleList.addAll(roleManagementInfoBean.getRows());
            List<RoleInfoBean> addList = DifferentDataUtil.addRoleDataToLocal(mRoleList, mRoleInfoBeanLocalList);
            if (!ListUtils.isEmpty(addList)) {
                for (RoleInfoBean companyInfo : addList) {
                    mRoleInfoBeanDaoManager.insertOrReplace(companyInfo);
                }
                mRoleInfoBeanLocalList = new ArrayList<>();
                mRoleInfoBeanLocalList = mRoleInfoBeanDaoManager.queryAll();
            }
            for (RoleInfoBean info : mRoleList) {
                for (RoleInfoBean localInfo : mRoleInfoBeanLocalList) {
                    if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                        if (info.getId().equals(localInfo.getId())) {
                            info.setLocalId(localInfo.getLocalId());
                            mRoleInfoBeanDaoManager.correct(info);
                        }
                    }
                }
            }
            if (mRoleListAdapter != null) {
                mRoleListAdapter.notifyDataSetChanged();
            }
        }
        mRoleListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_see_detail:
                        mType = "2";
                        mRoleDetailLocalId = mRoleList.get(position).getLocalId();
                        mPresenter.getRoleInfo(mRoleList.get(position).getId());
                        break;
                    case R.id.tv_auth_allocation:
                        if (NetUtil.isNetAvailable(getBVActivity())) {
                            mLimitRoleId = mRoleList.get(position).getId();
                            mPresenter.getRoleLimit(mLimitRoleId);
                        } else {
                            toastMsg("网络连接失败，请检查网络是否可用");
                        }
                        break;
                }
            }
        });

    }

    @Override
    public void getRoleInfo(RoleInfoBean roleInfoBean) {
        if (roleInfoBean != null) {
            mRoleDetailDialog = RoleDetailDialog.getInstance(getActivity(), roleInfoBean);
            mRoleDetailDialog.isCancelableOnTouchOutside(false)
                    .withDuration(300)
                    .withEffect(Effectstype.Fadein)
                    .setCancelClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mRoleDetailDialog.dismiss();
                        }
                    })
                    .show();
            mRoleDetailDialog.addOnSaveClickListener(new RoleDetailDialog.OnSaveClickListener() {
                @Override
                public void onSaveClick(RoleInfoBean roleInfoBean) {
                    if (roleInfoBean != null) {
                        String userId = String.valueOf(UserInfoManager.getUserLoginInfo(getBVActivity()).getId());
                        mPresenter.saveRoleInfo(userId, roleInfoBean.getId(), roleInfoBean.getRoleName(), roleInfoBean.getRoleCode(), roleInfoBean.getTypeName(), roleInfoBean.getOrderNum());
                    }
                }
            });
            for (RoleInfoBean localInfo : mRoleInfoBeanLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(roleInfoBean.getId())) {
                    if (localInfo.getId().equals(roleInfoBean.getId())) {
                        roleInfoBean.setLocalId(localInfo.getLocalId());
                        mRoleInfoBeanDaoManager.correct(roleInfoBean);
                    }
                }
            }
        }
    }

    @Override
    public void getRoleLimit(List<RoleLimitInfoBean> roleLimitInfoBeanList) {
        if (roleLimitInfoBeanList != null) {
            mRoleLimitInfoBeanList = roleLimitInfoBeanList;
            mRoleAuthLimitDialog = RoleAuthLimitDialog.getInstance(getActivity(), roleLimitInfoBeanList);
            mRoleAuthLimitDialog.isCancelableOnTouchOutside(false)
                    .withDuration(300)
                    .withEffect(Effectstype.Fadein)
                    .setCancelClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mRoleAuthLimitDialog.dismiss();
                        }
                    })
                    .setSaveClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (int i = 0; i < mRoleLimitInfoBeanList.size(); i++) {
                                if (mRoleLimitInfoBeanList.get(i).isChecked()) {
                                    mResourceIds += mRoleLimitInfoBeanList.get(i).getId() + ",";
                                }
                                for (int j = 0; j < mRoleLimitInfoBeanList.get(i).getBtns().size(); j++) {
                                    if (mRoleLimitInfoBeanList.get(i).getBtns().get(j).isChecked()) {
                                        mBtnIds += mRoleLimitInfoBeanList.get(i).getBtns().get(j).getId() + ",";
                                    }
                                }
                            }
                            if (!TextUtils.isEmpty(mResourceIds)) {
                                mResourceIds = mResourceIds.substring(0, mResourceIds.length() - 1);
                            }
                            if (!TextUtils.isEmpty(mBtnIds)) {
                                mBtnIds = mBtnIds.substring(0, mBtnIds.length() - 1);
                            }
                            mPresenter.saveRoleLimit(mLimitRoleId, mResourceIds, mBtnIds);
                        }
                    })
                    .show();
            mRoleAuthLimitDialog.getAdapter().setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    CheckBox cb_auth_type = (CheckBox) adapter.getViewByPosition(mRoleAuthLimitDialog.getRecyclerView(), position, R.id.cb_auth_type);
                    CheckBox cb_add = (CheckBox) adapter.getViewByPosition(mRoleAuthLimitDialog.getRecyclerView(), position, R.id.cb_add);
                    CheckBox cb_modify = (CheckBox) adapter.getViewByPosition(mRoleAuthLimitDialog.getRecyclerView(), position, R.id.cb_modify);
                    CheckBox cb_delete = (CheckBox) adapter.getViewByPosition(mRoleAuthLimitDialog.getRecyclerView(), position, R.id.cb_delete);
                    switch (view.getId()) {
                        case R.id.cb_auth_type:
                            mRoleLimitInfoBeanList.get(position).setChecked(cb_auth_type.isChecked());
                            break;
                        case R.id.cb_add:
                            mRoleLimitInfoBeanList.get(position).getBtns().get(0).setChecked(cb_add.isChecked());
                            break;
                        case R.id.cb_modify:
                            mRoleLimitInfoBeanList.get(position).getBtns().get(1).setChecked(cb_modify.isChecked());
                            break;
                        case R.id.cb_delete:
                            mRoleLimitInfoBeanList.get(position).getBtns().get(2).setChecked(cb_delete.isChecked());
                            break;
                    }
                    mRoleAuthLimitDialog.getAdapter().notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void saveRoleLimit(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            toastMsg(getString(R.string.Successfully_Modified));
            mResourceIds = "";
            mBtnIds = "";
            mRoleAuthLimitDialog.dismiss();
            page = 1;
            pullDownRefresh(page);
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }

    @Override
    public void saveRoleInfo(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            if (mType.equals("1")) {
                toastMsg(getString(R.string.Added_Successfully));
                mAddRoleDialog.dismiss();
            } else if (mType.equals("2")) {
                toastMsg(getString(R.string.Save_Success));
                mRoleDetailDialog.dismiss();
            }
            page = 1;
            pullDownRefresh(page);
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }


    @Override
    public void onTakeException(@NonNull ComException error) {
        super.onTakeException(error);
        if (mRoleDetailDialog != null) {
            mRoleDetailDialog.dismiss();
        }
        if (mAddRoleDialog != null) {
            mAddRoleDialog.dismiss();
        }
        if (mRoleAuthLimitDialog != null) {
            mRoleAuthLimitDialog.dismiss();
        }
    }


    @Override
    public void release() {

    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        mRefreshLayout.setEnableLoadmore(false);
        switch (port) {
            case REQUEST_COMPANY_SYSTEM_ROLE_LIST:
                List<RoleInfoBean> rows = new ArrayList<>();
                if (!TextUtils.isEmpty(StringUtil.getText(et_search_roleName)) || !TextUtils.isEmpty(StringUtil.getText(et_search_roleCode))) {
                    rows = mRoleInfoBeanDaoManager.queryBuilder().where(RoleInfoBeanDao.Properties.RoleName.like("%" + StringUtil.getText(et_search_roleName) + "%"), RoleInfoBeanDao.Properties.RoleCode.like("%" + StringUtil.getText(et_search_roleCode) + "%")).list();
                } else {
                    rows = mRoleInfoBeanDaoManager.queryAll();
                }
                RoleManagementInfoBean companyClientInfoBean = new RoleManagementInfoBean();
                companyClientInfoBean.setRows(rows);
                getRoleList(companyClientInfoBean);
                break;
            case REQUEST_COMPANY_SYSTEM_ROLE_DETAIL:
                RoleInfoBean roleInfoBean = null;
                for (RoleInfoBean info : mRoleInfoBeanLocalList) {
                    if (mRoleDetailLocalId == info.getLocalId()) {
                        roleInfoBean = info;
                    }
                }
                getRoleInfo(roleInfoBean);
                break;
        }
    }
}
