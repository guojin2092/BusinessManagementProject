package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleLimitInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.RoleManagementContract;
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
 * Date：2018/12/9 0009 15:22
 * Modification  History:
 * Why & What is modified:
 */
public class RoleManagementPresenter extends RxPresenter<RoleManagementContract.View> implements RoleManagementContract.Presenter {
    @Override
    public void getRoleList(int page, int rows, String roleName, String roleCode, String typeName) {
        addDisposable(mDataManager.getRoleList(page, rows, roleName, roleCode, typeName)
                .compose(RxUtils.<RoleManagementInfoBean>ioToMain(mView))
                .subscribe(new Consumer<RoleManagementInfoBean>() {
                    @Override
                    public void accept(RoleManagementInfoBean userManagementInfoBean) throws Exception {
                        mView.getRoleList(userManagementInfoBean);
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

    @Override
    public void getRoleInfo(String id) {
        addDisposable(mDataManager.getRoleInfo(id)
                .compose(RxUtils.<RoleInfoBean>ioToMain(mView))
                .subscribe(new Consumer<RoleInfoBean>() {
                    @Override
                    public void accept(RoleInfoBean roleInfoBean) throws Exception {
                        mView.getRoleInfo(roleInfoBean);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveRoleInfo(String userId, String id, String roleName, String roleCode, String typeName, String orderNum) {
        addDisposable(mDataManager.saveRoleInfo(userId, id, roleName, roleCode, typeName, orderNum)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveRoleInfo(baseEntity);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void getRoleLimit(String id) {
        addDisposable(mDataManager.getRoleLimit(id)
                .compose(RxUtils.<List<RoleLimitInfoBean>>ioToMain(mView))
                .subscribe(new Consumer<List<RoleLimitInfoBean>>() {
                    @Override
                    public void accept(List<RoleLimitInfoBean> roleLimitInfoBeanList) throws Exception {
                        mView.getRoleLimit(roleLimitInfoBeanList);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void saveRoleLimit(String id, String resourceIds, String btnIds) {
        addDisposable(mDataManager.saveRoleLimit(id, resourceIds, btnIds)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveRoleLimit(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
