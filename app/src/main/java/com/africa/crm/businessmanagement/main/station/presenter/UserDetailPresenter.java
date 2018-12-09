package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.station.contract.UserDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/9 0009 12:44
 * Modification  History:
 * Why & What is modified:
 */
public class UserDetailPresenter extends RxPresenter<UserDetailContract.View> implements UserDetailContract.Presenter {
    @Override
    public void getUserInfo(String id) {
        addDisposable(mDataManager.getUserInfo(id)
                .compose(RxUtils.<UserInfo>ioToMain(mView))
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception {
                        mView.getUserInfo(userInfo);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveOrcreateUser(String id, String userName, String type, String roleIds, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        addDisposable(mDataManager.saveOrcreateUser(id, userName, type, roleIds, passWord, name, phone, address, email, state, companyId, head)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveOrcreateUser(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getAllCompany(String name) {
        addDisposable(mDataManager.getAllCompany(name)
                .compose(RxUtils.<List<DicInfo>>ioToMain(mView))
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getAllCompany(dicInfoList);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getAllRoles(String name) {
        addDisposable(mDataManager.getAllRoles(name)
                .compose(RxUtils.<List<RoleInfoBean>>ioToMain(mView))
                .subscribe(new Consumer<List<RoleInfoBean>>() {
                    @Override
                    public void accept(List<RoleInfoBean> roleInfoBeanList) throws Exception {
                        mView.getAllRoles(roleInfoBeanList);
                    }
                }, new ComConsumer(mView)));

    }
}
