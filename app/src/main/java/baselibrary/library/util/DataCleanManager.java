//package baselibrary.library.util;
//
///*  * 文 件 名:  DataCleanManager.java  * 描    述:  主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录  */
//
//import android.content.Context;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//
//import java.io.File;
//import java.math.BigDecimal;
//
//import baselibrary.library.myview.MyLog;
//
//import static com.tencent.tinker.loader.shareutil.SharePatchFileUtil.deleteDir;
//
///**
// * 本应用数据清除管理器
// */
//public class DataCleanManager {
//    /**
//     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context
//     */
//    public static void cleanInternalCache(Context context, Handler handler,
//                                          int index) {
//        deleteFilesByDirectory(context.getCacheDir(), handler, index);
//    }
//
//    /**
//     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context
//     */
//    public static void cleanDatabases(Context context, Handler handler,
//                                      int index) {
//        deleteFilesByDirectory(
//                new File("/data/data/" + context.getPackageName()
//                        + "/databases"), handler, index);
//    }
//
//    /**
//     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
//     * context
//     */
//    public static void cleanSharedPreference(Context context, Handler handler,
//                                             int index) {
//        deleteFilesByDirectory(
//                new File("/data/data/" + context.getPackageName()
//                        + "/shared_prefs"), handler, index);
//    }
//
//    /**
//     * 按名字清除本应用数据库 * * @param context * @param dbName
//     */
//    public static void cleanDatabaseByName(Context context, String dbName) {
//        context.deleteDatabase(dbName);
//    }
//
//    /**
//     * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context
//     */
//    public static void cleanFiles(Context context, Handler handler, int index) {
//        deleteFilesByDirectory(context.getFilesDir(), handler, index);
//    }
//
//    /**
//     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
//     * context
//     */
//    public static void cleanExternalCache(Context context, Handler handler,
//                                          int index) {
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            deleteFilesByDirectory(context.getExternalCacheDir(), handler,
//                    index);
//        }
//    }
//
//    /**
//     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath
//     */
//    public static void cleanCustomCache(String filePath, Handler handler,
//                                        int index) {
//        deleteFilesByDirectory(new File(filePath), handler, index);
//    }
//
//    /**
//     * 清除本应用所有的数据 * * @param context * @param filepath
//     */
//    public static void cleanApplicationData(Context context, Handler handler,
//                                            int index, String... filepath) {
//        cleanInternalCache(context, handler, index);
//        cleanExternalCache(context, handler, index);
//        cleanDatabases(context, handler, index);
//        cleanSharedPreference(context, handler, index);
//        cleanFiles(context, handler, index);
//        for (String filePath : filepath) {
//            cleanCustomCache(filePath, handler, index);
//        }
//    }
//
//    /**
//     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
//     */
//    private synchronized static void deleteFilesByDirectory(
//            final File directory, final Handler handler, final int index) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (directory != null && directory.exists()
//                        && directory.isDirectory()) {
//                    for (File item : directory.listFiles()) {
//                        item.delete();
//                        MyLog.e(DataCleanManager.class, item.getPath());
//                    }
//                }
//                Message msg = Message.obtain();
//                msg.what = index;
//                handler.sendMessage(msg);
//            }
//        }).start();
//    }
//
//    /**
//     * 清除缓存
//     *
//     * @param context
//     */
//    public static void clearAllCache(Context context) {
//        deleteDir(context.getCacheDir());
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            deleteDir(context.getExternalCacheDir());
//        }
//    }
//
//    /**
//     * 获取缓存
//     *
//     * @param context
//     * @return
//     * @throws Exception
//     */
//    public static String getTotalCacheSize(Context context) throws Exception {
//        long cacheSize = getFolderSize(context.getCacheDir());
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            cacheSize += getFolderSize(context.getExternalCacheDir());
//        }
//        return getFormatSize(cacheSize);
//    }
//
//    public static long getFolderSize(File file) throws Exception {
//        long size = 0;
//        try {
//            File[] fileList = file.listFiles();
//            for (int i = 0; i < fileList.length; i++) {
//                // 如果下面还有文件
//                if (fileList[i].isDirectory()) {
//                    size = size + getFolderSize(fileList[i]);
//                } else {
//                    size = size + fileList[i].length();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return size;
//    }
//
//    /**
//     * 格式化单位
//     *
//     * @param size
//     * @return
//     */
//    public static String getFormatSize(double size) {
//        double kiloByte = size / 1024;
//        if (kiloByte < 1) {
////            return size + "Byte";
//            return "";
//        }
//
//        double megaByte = kiloByte / 1024;
//        if (megaByte < 1) {
//            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
//            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
//                    .toPlainString() + "KB";
//        }
//
//        double gigaByte = megaByte / 1024;
//        if (gigaByte < 1) {
//            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
//            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
//                    .toPlainString() + "MB";
//        }
//
//        double teraBytes = gigaByte / 1024;
//        if (teraBytes < 1) {
//            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
//            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
//                    .toPlainString() + "GB";
//        }
//        BigDecimal result4 = new BigDecimal(teraBytes);
//        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
//                + "TB";
//    }
//}
