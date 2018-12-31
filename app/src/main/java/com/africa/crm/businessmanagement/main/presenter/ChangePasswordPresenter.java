package com.africa.crm.businessmanagement.main.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.LoginInfoBean;
import com.africa.crm.businessmanagement.main.contract.ChangePasswordContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import io.reactivex.functions.Consumer;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/23 0023 10:00
 * Modification  History:
 * Why & What is modified:
 */
public class ChangePasswordPresenter extends RxPresenter<ChangePasswordContract.View> implements ChangePasswordContract.Presenter {

    @Override
    public void changePassword(String id, String oldPassWord, String newPassWord) {
        addDisposable(mDataManager.changePassword(id, oldPassWord, newPassWord)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.changePassword(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
