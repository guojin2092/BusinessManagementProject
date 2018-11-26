package com.africa.crm.businessmanagement.network.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;


import com.africa.crm.businessmanagement.MyApplication;

import java.io.File;

/**
 * 作者：warm
 * 时间：2018-01-06 15:05
 * 描述：
 */


public class FileUtils {

    public static final String CACHE_HTTP = "cache_http";

    //网络缓存文件夹名称
    public static final String CACHE_IMAGE = "cache_image";

    //拍照文件夹
    public static final String CAMERA_IMAGE = "camera_image";

    //图片裁剪后的文件夹名称
    public static final String CROP_IMAGE = "crop_image";

    //压缩后的图片文件夹名称
    public static final String ZIP_IMAGE = "zip_image";


    //Apk下载文件夹
    public static final String APK = "apk";

    //crash收集文件夹
    public static final String CRASH = "crash";

    public static File createFileDir(String dirName) {
        return createFileDir(getExternalCacheDir(MyApplication.getInstance()), dirName);
    }

    public static File createFileDir(String parentPath, String dirPath) {
        if (!TextUtils.isEmpty(parentPath)) {
            File parentFile = new File(parentPath, dirPath);
            if (!parentFile.exists()) {
                boolean suc = parentFile.mkdirs();
                if (suc) {
                    return parentFile;
                } else {
                    return null;
                }
            } else {
                return parentFile;
            }
        } else {
            return null;
        }
    }


    /**
     * 获取拓展存储Cache的绝对路径
     *
     * @param context
     */
    public static String getExternalCacheDir(Context context) {
        StringBuilder sb = new StringBuilder();
        if (checkSdcard()) {
            File file = context.getExternalCacheDir();
            if (file != null) {
                sb.append(file.getAbsolutePath());
            } else {
                sb.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/").append(context.getPackageName())
                        .append("/cache/");
            }
        } else {
            sb.append(context.getCacheDir().getAbsolutePath());
        }
        return sb.toString();
    }

    private static boolean checkSdcard() {
        //判断sd卡情况
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.isExternalStorageRemovable();
    }

}
