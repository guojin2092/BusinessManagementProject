package baselibrary.library.myview;


/**
 * 自定义log
 *
 * @author Administrator
 */
public class MyLog {

    /**
     * Tag
     */
    public final static String TAG = "College";
    /**
     * LogUtil工具
     */
    private static MyLog sLogUtil;

    private MyLog() {
    }

    /**
     * 获得调试单例
     *
     * @return
     */
    public static MyLog getInstance() {
        if (sLogUtil == null) {
            synchronized (MyLog.class) {
                if (sLogUtil == null) {
                    sLogUtil = new MyLog();
                }
            }
        }
        return sLogUtil;
    }

    /**
     * 打印debug
     *
     * @param object 输出地方的类的实例，用于打印输出语句效用
     * @param msg    输出的值
     */
    public static void d(Object object, String msg) {
        android.util.Log.d(TAG, object.getClass().getSimpleName() + "--"
                + msg);
    }

    /**
     * 打印info
     *
     * @param object 输出地方的类的实例，用于打印输出语句效用
     * @param msg    输出的值
     */
    public static void i(Object object, String msg) {
        android.util.Log.i(TAG, object.getClass().getSimpleName() + "--"
                + msg);
    }

    /**
     * 打印error
     *
     * @param object 输出地方的类的实例，用于打印输出语句效用
     * @param msg    输出的值
     */
    public static void e(Object object, String msg) {
        android.util.Log.e(TAG, object.getClass().getSimpleName() + "--"
                + msg);
    }
}
