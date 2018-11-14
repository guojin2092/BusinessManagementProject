package baselibrary.library.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.webkit.CookieManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件处理工具包
 *
 * @author Administrator
 */
public class FileUtil {
    private Context context;

    public FileUtil(Context context) {
        this.context = context;
    }

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     */
    public void createDir(String path) {
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }

    /**
     * 将图片url保存为bytes数组
     *
     * @param imageUrl 图片url
     * @return 返回byte数组
     */
    public byte[] getBytes(String imageUrl) {
        try {
            URL uri = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(5 * 1000);

            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStream = conn.getInputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                arrayOutputStream.write(buffer, 0, n);
            }
            return arrayOutputStream.toByteArray();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将byte数组变成文件
     *
     * @param data byte数组
     * @param path 保存到的路径
     */
    public void byteToFile(byte[] data, String path) {
        OutputStream output = null;
        try {
            File file = new File(path);
            file.createNewFile();
            output = new FileOutputStream(file);

            int size = 4 * 1024;
            int offset = 0;
            int len = size;
            while (offset < data.length) {
                len = data.length - offset;
                if (len > size)
                    len = size;
                output.write(data, offset, len);
                offset += len;
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 将assect下资源保存到缓存中
     *
     * @param path 保存路径
     */
    public void copyFileOrDir(String path) {
        AssetManager assetManager = context.getAssets();
        String assets[] = null;
        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(path);
            } else {
                String fullPath = "/data/data/" + context.getPackageName()
                        + "/" + path;
                File dir = new File(fullPath);
                if (!dir.exists()) {
                    dir.mkdir();

                    for (int i = 0; i < assets.length; ++i) {
                        copyFileOrDir(path + "/" + assets[i]);
                    }
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    /**
     * 拷贝文件
     *
     * @param filename 拷贝的文件名
     */
    private void copyFile(String filename) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String newFileName = "/data/data/" + context.getPackageName() + "/"
                    + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    /**
     * 删除webview的cache
     */
    public void clearWebViewCache() {
        File fileDb = new File("/data/data/" + context.getPackageName()
                + "/databases/" + "webview.db");
        if (fileDb.exists() && !fileDb.isDirectory())
            context.deleteDatabase("webview.db");

        context.deleteDatabase("webview.db-journal");
        context.deleteDatabase("WebViewCache.db");
        context.deleteDatabase("webviewCookiesChromium.db");
        context.deleteDatabase("webviewCookiesChromiumPrivate.db");

        CookieManager.getInstance().removeAllCookie();
        CookieManager.getInstance().removeExpiredCookie();
        CookieManager.getInstance().removeSessionCookie();
    }


}
