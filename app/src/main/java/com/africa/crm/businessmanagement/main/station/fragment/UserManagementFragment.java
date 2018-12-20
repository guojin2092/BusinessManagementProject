package com.africa.crm.businessmanagement.main.station.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveUserEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.UserInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.activity.UserDetailActivity;
import com.africa.crm.businessmanagement.main.station.adapter.UserListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.UserManagementContract;
import com.africa.crm.businessmanagement.main.station.presenter.UserManagementPresenter;
import com.africa.crm.businessmanagement.mvp.fragment.BaseRefreshMvpFragment;
import com.africa.crm.businessmanagement.network.error.ComException;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/25 0025 15:00
 * Modification  History:
 * Why & What is modified:
 */
public class UserManagementFragment extends BaseRefreshMvpFragment<UserManagementPresenter> implements UserManagementContract.View {
    private View mRootView;

    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.titlebar_right)
    TextView titlebar_right;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.et_search_username)
    EditText et_search_username;
    @BindView(R.id.et_search_nickname)
    EditText et_search_nickname;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private UserListAdapter mUserListAdapter;
    private List<UserInfoBean> mDeleteList = new ArrayList<>();
    private List<UserInfoBean> mUserInfoBeanList = new ArrayList<>();

    private boolean mShowCheckBox = false;

    private String mSearchUserNameText = "";//查询用户名
    private String mSearchNickNameText = "";//查询昵称
    /**
     * 用户类型
     */
    @BindView(R.id.spinner_type)
    MySpinner spinner_type;

    private List<DicInfo> mSpinnerTypeList = new ArrayList<>();
    private String mType = "";
    private String mCompanyId = "";
    /**
     * 用户状态
     */
    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private List<DicInfo> mSpinnerStateList = new ArrayList<>();
    private String mState = "";

    private AlertDialog mDeleteDialog;

    public static UserManagementFragment newInstance() {
        UserManagementFragment userManagementFragment = new UserManagementFragment();
        return userManagementFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_user_management, container, false);
        return mRootView;
    }

    @Override
    protected View getFragmentProgress() {
        return mRootView;
    }

    @Override
    protected UserManagementPresenter initPresenter() {
        return new UserManagementPresenter();
    }

    @Override
    protected void requestData() {
        pullDownRefresh(page);
    }

    @Override
    public void initView() {
        titlebar_right.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        titlebar_right.setText(R.string.delete);

/*
        et_search_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    et_search_nickname.setText("");
                    spinner_type.setText("");
                    mType = "";
                    spinner_state.setText("");
                    mState = "";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
/*
        et_search_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    et_search_username.setText("");
                    spinner_type.setText("");
                    mType = "";
                    spinner_state.setText("");
                    mState = "";
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
        mSpinnerTypeList.add(new DicInfo("系统管理用户", "1"));
        mSpinnerTypeList.add(new DicInfo("企业用户", "2"));

        spinner_type.setListDatas(getBVActivity(), mSpinnerTypeList);
        mSpinnerStateList.add(new DicInfo("正常", "1"));
        mSpinnerStateList.add(new DicInfo("禁用", "2"));
        spinner_state.setListDatas(getBVActivity(), mSpinnerStateList);

        mUserListAdapter = new UserListAdapter(mUserInfoBeanList);
        recyclerView.setAdapter(mUserListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(getActivity(), R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mType = dicInfo.getCode();
/*
                if (!TextUtils.isEmpty(mType)) {
                    spinner_state.setText("");
                    mState = "";
                    et_search_username.setText("");
                    et_search_nickname.setText("");
                }
*/
            }
        });

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mState = dicInfo.getCode();
/*
                if (!TextUtils.isEmpty(mType)) {
                    spinner_type.setText("");
                    mType = "";
                    et_search_username.setText("");
                    et_search_nickname.setText("");
                }
*/
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.titlebar_back:
                getBVActivity().onBackPressed();
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
                if (mUserListAdapter != null) {
                    mUserListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                UserDetailActivity.startActivity(getBVActivity(), "");
                break;
            case R.id.tv_search:
                /*if (TextUtils.isEmpty(et_search_username.getText().toString().trim()) && TextUtils.isEmpty(et_search_nickname.getText().toString().trim()) && TextUtils.isEmpty(spinner_type.getText()) && TextUtils.isEmpty(spinner_state.getText())) {
                    toastMsg("请输入查询条件");
                    return;
                }*/
                if (mType.equals("1")) {
                    mCompanyId = "";
                } else if (mType.equals("2")) {
                    mCompanyId = UserInfoManager.getUserLoginInfo(getBVActivity()).getCompanyId();
                }
                page = 1;
                pullDownRefresh(page);
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (UserInfoBean userInfoBean : mUserInfoBeanList) {
                    if (userInfoBean.isChosen()) {
                        mDeleteList.add(userInfoBean);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                String userId = String.valueOf(UserInfoManager.getUserLoginInfo(getBVActivity()).getId());
                for (UserInfoBean userInfoBean : mDeleteList) {
                    if (userInfoBean.getId().equals(userId)) {
                        toastMsg("个人账号不能删除");
                        return;
                    }
                }
                mDeleteDialog = new AlertDialog.Builder(getBVActivity())
                        .setTitle(R.string.tips)
                        .setMessage(R.string.confirm_delete)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                mDeleteDialog.dismiss();
                            }
                        })
                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    mPresenter.deleteUser(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    protected void pullDownRefresh(int page) {
        mSearchUserNameText = et_search_username.getText().toString().trim();
        mSearchNickNameText = et_search_nickname.getText().toString().trim();
        mPresenter.getUserList(page, rows, mSearchUserNameText, mType, mCompanyId, mState, mSearchNickNameText);
    }

    @Override
    public void getUserList(UserManagementInfoBean userManagementInfoBean) {
        if (page == 1) {
            if (ListUtils.isEmpty(userManagementInfoBean.getRows())) {
                layout_network_error.setVisibility(View.GONE);
                mRefreshLayout.getLayout().setVisibility(View.GONE);
                layout_no_data.setVisibility(View.VISIBLE);
                return;
            } else {
                layout_no_data.setVisibility(View.GONE);
                layout_network_error.setVisibility(View.GONE);
                mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
            }
            mUserInfoBeanList.clear();
            recyclerView.smoothScrollToPosition(0);
        }
        if (mRefreshLayout != null) {
            if (ListUtils.isEmpty(userManagementInfoBean.getRows()) && page > 1) {
                mRefreshLayout.finishLoadmoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadmore();
            }
        }
        if (!ListUtils.isEmpty(userManagementInfoBean.getRows())) {
            mUserInfoBeanList.addAll(userManagementInfoBean.getRows());
            if (mUserListAdapter != null) {
                mUserListAdapter.notifyDataSetChanged();
            }
        }
        if (!ListUtils.isEmpty(mUserInfoBeanList)) {
            mUserListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (mShowCheckBox) {
                        CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                        mUserInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                        mUserListAdapter.notifyDataSetChanged();
                    } else {
                        UserDetailActivity.startActivity(getBVActivity(), mUserInfoBeanList.get(position).getId());
                    }
                }
            });
        }

    }

    @Override
    public void deleteUser(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mUserInfoBeanList.contains(mDeleteList.get(i))) {
                    int position = mUserInfoBeanList.indexOf(mDeleteList.get(i));
                    mUserInfoBeanList.remove(mDeleteList.get(i));
                    if (mUserListAdapter != null) {
                        mUserListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mUserInfoBeanList)) {
                titlebar_right.setText(R.string.delete);
                tv_delete.setVisibility(View.GONE);
                mShowCheckBox = false;
                layout_network_error.setVisibility(View.GONE);
                mRefreshLayout.getLayout().setVisibility(View.GONE);
                layout_no_data.setVisibility(View.VISIBLE);
            }
            mDeleteDialog.dismiss();
        }
    }

    @Subscribe
    public void Event(AddOrSaveUserEvent addOrSaveUserEvent) {
        toastMsg(addOrSaveUserEvent.getMsg());
        requestData();
    }

    @Override
    public void onTakeException(@NonNull ComException error) {
        super.onTakeException(error);
        layout_no_data.setVisibility(View.GONE);
        mRefreshLayout.getLayout().setVisibility(View.GONE);
        layout_network_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void release() {
        EventBus.getDefault().unregister(this);
    }

}
