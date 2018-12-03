package com.africa.crm.businessmanagement.baseutil.library.base.bean;

import com.simplesoft.baselibrary.bean.BaseSimpleBean;

import java.io.Serializable;

/**
 * 版本更新
 */
public class UpdateBean extends BaseSimpleBean {
    public UpdateData data;

    public class UpdateData implements Serializable {
        public String version;
        public String downurl;
    }
}
