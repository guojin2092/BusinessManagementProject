package com.africa.crm.businessmanagement.main.photo;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.africa.crm.businessmanagement.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

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
            sb.append(Environment.getExternalStorageDirectory().getPath()).append("/CRM");
           /* File file = context.getExternalCacheDir();
            if (file != null) {
                sb.append(file.getAbsolutePath());
            } else {
                sb.append(Environment.getExternalStorageDirectory().getPath()).append("/CRM");
            }*/
        } else {
            sb.append(context.getCacheDir().getAbsolutePath());
        }
        return sb.toString();
    }


    private static boolean checkSdcard() {
        //判断sd卡情况
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.isExternalStorageRemovable();
    }

    /**
     * 保存文件到本地
     *
     * @param body
     * @param fileDir
     * @param fileName
     */
    public static String saveFile(ResponseBody body, String fileDir, String fileName) {
        InputStream is = null;
        File file = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(fileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(dir, fileName + ".jpg");
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
        return file.getAbsolutePath();
    }

}
