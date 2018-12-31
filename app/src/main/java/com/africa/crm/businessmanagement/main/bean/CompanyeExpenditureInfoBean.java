package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/10 0010 13:18
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyeExpenditureInfoBean implements Serializable {
    private int total;
    private int totalPage;
    private List<CompanyExpenditureInfo> rows;
    private int count;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<CompanyExpenditureInfo> getRows() {
        return rows;
    }

    public void setRows(List<CompanyExpenditureInfo> rows) {
        this.rows = rows;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
