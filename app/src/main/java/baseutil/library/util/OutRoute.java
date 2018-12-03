package baseutil.library.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Environment;

import java.io.File;

/**
 * 静态的类，用来获取当前的路径，有SD卡返回sd的位置，没有就返回内存的位置
 */
public class OutRoute {
    private static OutRoute mOutRoute = null;
    private String strRoute;// sd卡或者内存的位置

    /**
     * @param context 尽量不要使用具体的activity，因为OutRoute会保留对这个activity的引用，
     *                从而导致activity类结束却不会释放内存的现象
     * @return
     */
    public static OutRoute getInstance(Context context) {
        if (mOutRoute == null)
            mOutRoute = new OutRoute(context.getApplicationContext());
        return mOutRoute;
    }

    private OutRoute(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()))
            strRoute = getExternalCacheDir(context.getApplicationContext()) + File.separator;
        else
            strRoute = context.getFilesDir().toString();
    }

    @TargetApi(VERSION_CODES.FROYO)
    public static File getExternalCacheDir(Context context) {

        if (Build.VERSION.SDK_INT >= VERSION_CODES.FROYO) {
            File path = context.getExternalFilesDir("");

            if (path != null) {
                return path;
            }
        }

        String fileDir = "/Android/data/" + context.getPackageName()
                + "/files/";
        return new File(Environment.getExternalStorageDirectory().getPath()
                + fileDir);
    }

    public String getStrRoute() {
        return strRoute;
    }

}
