package com.africa.crm.businessmanagement.widget;

import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
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

    /**
     * 添加字典项数据到本地数据库(去重处理)
     *
     * @param netList
     * @param localList
     * @return
     */
    public static List<CompanySupplierInfo> addSupplierDataToLocal(List<CompanySupplierInfo> netList, List<CompanySupplierInfo> localList) {
        List<CompanySupplierInfo> mCompanyInfoAddList = new ArrayList<>();//差异数据
        List<String> mNetList = new ArrayList<>();
        List<String> mLocalList = new ArrayList<>();
        for (CompanySupplierInfo companyInfo : netList) {
            mNetList.add(companyInfo.getId());
        }
        if (!ListUtils.isEmpty(localList)) {
            for (CompanySupplierInfo companyInfo : localList) {
                mLocalList.add(companyInfo.getId());
            }
            for (String string : mNetList) {
                if (!mLocalList.contains(string)) {
                    for (CompanySupplierInfo companyInfo : netList) {
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
    public static List<CompanyProductInfo> addProductDataToLocal(List<CompanyProductInfo> netList, List<CompanyProductInfo> localList) {
        List<CompanyProductInfo> mCompanyInfoAddList = new ArrayList<>();//差异数据
        List<String> mNetList = new ArrayList<>();
        List<String> mLocalList = new ArrayList<>();
        for (CompanyProductInfo companyInfo : netList) {
            mNetList.add(companyInfo.getId());
        }
        if (!ListUtils.isEmpty(localList)) {
            for (CompanyProductInfo companyInfo : localList) {
                mLocalList.add(companyInfo.getId());
            }
            for (String string : mNetList) {
                if (!mLocalList.contains(string)) {
                    for (CompanyProductInfo companyInfo : netList) {
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
    public static List<CompanyClientInfo> addClientDataToLocal(List<CompanyClientInfo> netList, List<CompanyClientInfo> localList) {
        List<CompanyClientInfo> mCompanyInfoAddList = new ArrayList<>();//差异数据
        List<String> mNetList = new ArrayList<>();
        List<String> mLocalList = new ArrayList<>();
        for (CompanyClientInfo companyInfo : netList) {
            mNetList.add(companyInfo.getId());
        }
        if (!ListUtils.isEmpty(localList)) {
            for (CompanyClientInfo companyInfo : localList) {
                mLocalList.add(companyInfo.getId());
            }
            for (String string : mNetList) {
                if (!mLocalList.contains(string)) {
                    for (CompanyClientInfo companyInfo : netList) {
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
    public static List<CompanyContactInfo> addContactDataToLocal(List<CompanyContactInfo> netList, List<CompanyContactInfo> localList) {
        List<CompanyContactInfo> mCompanyInfoAddList = new ArrayList<>();//差异数据
        List<String> mNetList = new ArrayList<>();
        List<String> mLocalList = new ArrayList<>();
        for (CompanyContactInfo companyInfo : netList) {
            mNetList.add(companyInfo.getId());
        }
        if (!ListUtils.isEmpty(localList)) {
            for (CompanyContactInfo companyInfo : localList) {
                mLocalList.add(companyInfo.getId());
            }
            for (String string : mNetList) {
                if (!mLocalList.contains(string)) {
                    for (CompanyContactInfo companyInfo : netList) {
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