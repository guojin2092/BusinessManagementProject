package com.africa.crm.businessmanagement.station;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.station.fragment.RoleManagementFragment;
import com.africa.crm.businessmanagement.station.fragment.UserManagementFragment;

import baselibrary.library.base.BaseActivity;
import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/25 0025 11:15
 * Modification  History:
 * Why & What is modified:
 */
public class RoleManagementActivity extends BaseActivity {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;

    @BindView(R.id.rg_management)
    RadioGroup rg_management;
    @BindView(R.id.rb_role_management)
    RadioButton rb_role_management;
    @BindView(R.id.rb_user_management)
    RadioButton rb_user_management;

    @BindView(R.id.fl_main)
    FrameLayout fl_main;
    private RoleManagementFragment mRoleManagementFragment;
    private UserManagementFragment mUserManagementFragment;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, RoleManagementActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_role_management);
    }

    @Override
    public void initView() {
        titlebar_back.setOnClickListener(this);
        titlebar_name.setText(getString(R.string.system_management));
        rb_role_management.setOnClickListener(this);
        rb_user_management.setOnClickListener(this);
        chosenRadioButton(rb_role_management);
        unChosenRadioButton(rb_user_management);
        if (mRoleManagementFragment == null || mUserManagementFragment == null) {
            mRoleManagementFragment = RoleManagementFragment.newInstance();
            mUserManagementFragment = UserManagementFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_main, mRoleManagementFragment, RoleManagementFragment.class.getSimpleName())
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_main, mUserManagementFragment, UserManagementFragment.class.getSimpleName())
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(mRoleManagementFragment)
                    .hide(mUserManagementFragment)
                    .commit();
        } else {
            mRoleManagementFragment = (RoleManagementFragment) getSupportFragmentManager().findFragmentByTag(RoleManagementFragment.class.getSimpleName());
            mUserManagementFragment = (UserManagementFragment) getSupportFragmentManager().findFragmentByTag(UserManagementFragment.class.getSimpleName());
            switchFragment();
        }

    }

    /**
     * 被选中的
     *
     * @param radioButton
     */
    private void chosenRadioButton(RadioButton radioButton) {
        radioButton.setBackground(ContextCompat.getDrawable(this, R.drawable.rb_background_chosen));
        radioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(RoleManagementActivity.this, R.drawable.iv_arrow_white), null);
        radioButton.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    /**
     * 未被选中的
     *
     * @param radioButton
     */
    private void unChosenRadioButton(RadioButton radioButton) {
        radioButton.setBackground(ContextCompat.getDrawable(this, R.drawable.rb_background_unchosen));
        radioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(RoleManagementActivity.this, R.drawable.iv_arrow_gray), null);
        radioButton.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    /**
     * fragment切换
     */
    private void switchFragment() {
        if (mRoleManagementFragment.isHidden()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(mRoleManagementFragment)
                    .hide(mUserManagementFragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(mUserManagementFragment)
                    .hide(mRoleManagementFragment)
                    .commit();
        }
    }

    /**
     * fragment切换
     */
    private void showFragment(boolean showRoleFragment) {
        if (showRoleFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(mRoleManagementFragment)
                    .hide(mUserManagementFragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(mUserManagementFragment)
                    .hide(mRoleManagementFragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
            case R.id.rb_role_management:
                chosenRadioButton(rb_role_management);
                unChosenRadioButton(rb_user_management);
                showFragment(true);
                break;
            case R.id.rb_user_management:
                chosenRadioButton(rb_user_management);
                unChosenRadioButton(rb_role_management);
                showFragment(false);
                break;
        }
    }

    @Override
    public void initData() {

    }
}
