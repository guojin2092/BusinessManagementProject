package com.africa.crm.businessmanagement.widget;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2019/1/2 0002 19:08
 * Modification  History:
 * Why & What is modified:
 */
public interface DownloadListener {
    void onStart();

    void onProgress(int progress);

    void onFinish(String filePath);

    void onFailure(String errorMsg);
}
