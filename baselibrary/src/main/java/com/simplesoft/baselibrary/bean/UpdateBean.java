package com.simplesoft.baselibrary.bean;

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
