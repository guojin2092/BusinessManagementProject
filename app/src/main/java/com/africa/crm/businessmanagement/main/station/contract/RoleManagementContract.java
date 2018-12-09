package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleLimitInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleManagementInfoBean;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/9 0009 15:21
 * Modification  History:
 * Why & What is modified:
 */
public class RoleManagementContract {
    public interface View extends BaseView {

        void getRoleList(RoleManagementInfoBean roleManagementInfoBean);

        void getAllRoles(List<RoleInfoBean> roleInfoBeanList);

        void getRoleInfo(RoleInfoBean roleInfoBean);

        void saveRoleInfo(BaseEntity baseEntity);

        void getRoleLimit(List<RoleLimitInfoBean> roleLimitInfoBeanList);

        void saveRoleLimit(BaseEntity baseEntity);
    }

    public interface Presenter extends IBasePresenter<RoleManagementContract.View> {
        void getRoleList(int page, int rows, String roleName, String roleCode, String typeName);

        void getAllRoles(String name);

        void getRoleInfo(String id);

        void saveRoleInfo(String userId, String id, String roleName, String roleCode, String typeName, String orderNum);

        void getRoleLimit(String id);

        void saveRoleLimit(String id, String resourceIds, String btnIds);

    }
}
