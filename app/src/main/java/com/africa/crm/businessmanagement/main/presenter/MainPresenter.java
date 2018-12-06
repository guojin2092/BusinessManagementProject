package com.africa.crm.businessmanagement.main.presenter;

import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
import com.africa.crm.businessmanagement.main.contract.MainContract;
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
 * Date：2018/12/6 0006 13:35
 * Modification  History:
 * Why & What is modified:
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Override
    public void getMainStationInfo(String id) {
        addDisposable(mDataManager.getMainStationInfo(id)
                .compose(RxUtils.<List<MainStationInfoBean>>ioToMain(mView))
                .subscribe(new Consumer<List<MainStationInfoBean>>() {
                    @Override
                    public void accept(List<MainStationInfoBean> mainStationInfoBeans) throws Exception {
                        mView.getMainStationInfo(mainStationInfoBeans);
                    }
                }, new ComConsumer(mView)));

    }
}
