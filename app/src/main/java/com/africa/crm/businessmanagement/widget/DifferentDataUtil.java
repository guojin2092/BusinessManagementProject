package com.africa.crm.businessmanagement.widget;

import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2019/1/5 0005 13:28
 * Modification  History:
 * Why & What is modified:
 */
public class DifferentDataUtil {

    /**
     * 添加字典项数据到本地数据库(去重处理)
     *
     * @param netList
     * @param localList
     * @return
     */
    public static List<DicInfo> addDataToLocal(List<DicInfo> netList, List<DicInfo> localList) {
        List<DicInfo> mCompanyTypeAddList = new ArrayList<>();//差异数据
        List<String> mNetList = new ArrayList<>();
        List<String> mLocalList = new ArrayList<>();
        for (DicInfo dicInfo : netList) {
            mNetList.add(dicInfo.getId());
        }
        if (!ListUtils.isEmpty(localList)) {
            for (DicInfo dicInfo : localList) {
                mLocalList.add(dicInfo.getId());
            }
            for (String string : mNetList) {
                if (!mLocalList.contains(string)) {
                    for (DicInfo dicInfo : netList) {
                        if (dicInfo.getId().equals(string)) {
                            mCompanyTypeAddList.add(dicInfo);
                        }
                    }
                }
            }
        } else {
            mCompanyTypeAddList.addAll(netList);
        }
        return mCompanyTypeAddList;
    }

    /**
     * 添加字典项数据到本地数据库(去重处理)
     *
     * @param netList
     * @param localList
     * @return
     */
    public static List<CompanyInfo> addInfoDataToLocal(List<CompanyInfo> netList, List<CompanyInfo> localList) {
        List<CompanyInfo> mCompanyInfoAddList = new ArrayList<>();//差异数据
        List<String> mNetList = new ArrayList<>();
        List<String> mLocalList = new ArrayList<>();
        for (CompanyInfo companyInfo : netList) {
            mNetList.add(companyInfo.getId());
        }
        if (!ListUtils.isEmpty(localList)) {
            for (CompanyInfo companyInfo : localList) {
                mLocalList.add(companyInfo.getId());
            }
            for (String string : mNetList) {
                if (!mLocalList.contains(string)) {
                    for (CompanyInfo companyInfo : netList) {
                        if (companyInfo.getId().equals(string)) {
                            mCompanyInfoAddList.add(companyInfo);
                        }
                    }
                }
            }
        } else {
            mCompanyInfoAddList.addAll(netList);
        }
        return mCompanyInfoAddList;
    }

    /**
     * 添加字典项数据到本地数据库(去重处理)
     *
     * @param netList
     * @param localList
     * @return
     */
    public static List<CompanyAccountInfo> addAccountDataToLocal(List<CompanyAccountInfo> netList, List<CompanyAccountInfo> localList) {
        List<CompanyAccountInfo> mCompanyInfoAddList = new ArrayList<>();//差异数据
        List<String> mNetList = new ArrayList<>();
        List<String> mLocalList = new ArrayList<>();
        for (CompanyAccountInfo companyInfo : netList) {
            mNetList.add(companyInfo.getId());
        }
        if (!ListUtils.isEmpty(localList)) {
            for (CompanyAccountInfo companyInfo : localList) {
                mLocalList.add(companyInfo.getId());
            }
            for (String string : mNetList) {
                if (!mLocalList.contains(string)) {
                    for (CompanyAccountInfo companyInfo : netList) {
                        if (companyInfo.getId().equals(string)) {
                            mCompanyInfoAddList.add(companyInfo);
                        }
                    }
                }
            }
        } else {
            mCompanyInfoAddList.addAll(netList);
        }
        return mCompanyInfoAddList;
    }

}
