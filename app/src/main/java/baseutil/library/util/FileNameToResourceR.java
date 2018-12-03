package baseutil.library.util;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * 将固定文件名转为本地资源存在的文件，以引用
 *
 * @author Administrator
 */
public class FileNameToResourceR {
    private static Class<?> drawable = null;

    /**
     * 将固定图片文件名转为本地资源存在的图片
     *
     * @param context
     * @param paramString 文件名
     * @return
     */
    public static int drawable(Context context, String paramString) {
        if (null == drawable)
            try {
                drawable = Class.forName(context.getApplicationContext().getPackageName()
                        + ".R$drawable");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        return format(drawable, paramString);
    }

    /**
     * 将固定图片文件名转为本地资源存在的图片
     *
     * @param packageName 包名
     * @param paramString 文件名
     * @return
     */
    public static int drawable(String packageName, String paramString) {
        if (null == drawable)
            try {
                drawable = Class.forName(packageName
                        + ".R$drawable");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        return format(drawable, paramString);
    }

    /**
     * 格式化文件
     *
     * @param paramClass
     * @param paramString 与R文件对应，如R$drawable
     * @return
     */
    private static int format(Class<?> paramClass, String paramString) {
        if (paramClass == null) {
            throw new IllegalArgumentException("ResClass is not initialized.");
        }
        try {
            Field localField = paramClass.getField(paramString);
            int k = localField.getInt(paramString);
            return k;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return -1;
    }
}
