package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/9 0009 10:31
 * Modification  History:
 * Why & What is modified:
 */
public class ProductInfo implements Serializable {
    private String name;
    private String price;
    private String num;

    private boolean chosen;

    public ProductInfo(String name, String price, String num) {
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
}
