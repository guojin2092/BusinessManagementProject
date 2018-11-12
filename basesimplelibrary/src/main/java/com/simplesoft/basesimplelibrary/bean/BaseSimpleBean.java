package com.simplesoft.basesimplelibrary.bean;

import java.io.Serializable;

/**
 * Project：teamwork-android
 * Author:  guoj
 * Version: 1.0.0
 * Description：
 * Date：16/4/18 下午9:43
 * Modification  History:
 * Why & What is modified:
 */
public class BaseSimpleBean implements Serializable {
    public Boolean isError;//false为访问成功 true为访问失败
    public String errorType;
    public String errorMessage;
}
