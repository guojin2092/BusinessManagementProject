package baselibrary.library.http;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 数据库的常用操作
 *
 * @author Administrator
 */
public class DatabaseUtils {
    /**
     * 复制数据库
     *
     * @param context
     * @param databaseName
     */
    public static void copyDB(Context context, String databaseName, int databaseId) {
        String DB_PATH = "/data/data/" + context.getPackageName();

        File file = new File(DB_PATH + "/" + databaseName);
        if (!file.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                int buffer = 400000;
                // 读取数据库并保存到data/data/packagename/xx.db...
                InputStream ins = context.getResources().openRawResource(
                        databaseId);
                byte[] bts = new byte[buffer];
                // int length;
                while ((ins.read(bts)) > 0) {
                    out.write(bts, 0, bts.length);
                }
                out.close();
                ins.close();
                SQLiteDatabase.openOrCreateDatabase(DB_PATH + "/"
                        + databaseName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将数据库放入指定的目录
     *
     * @param context
     */
    public static SQLiteDatabase importInitDatabase(Context context, String databaseName, int resouseId) {
        SQLiteDatabase database;
        String packageName = context.getPackageName();

        String dirPath = "/data/data/" + packageName + "/databases";

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dbFile = new File(dir, databaseName);
        try {
            if (!dbFile.exists()) {
                // 不存在文件则新建文件,并保存在本地
                dbFile.createNewFile();
                InputStream is = context.getApplicationContext().getResources()
                        .openRawResource(resouseId);
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                fos.write(buffer);
                is.close();
                fos.close();
                database = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
            } else {
                // 否则不须新建,但是也会保存在本地
//                InputStream is = context.getApplicationContext().getResources()
//                        .openRawResource(resouseId);
//                FileOutputStream fos = new FileOutputStream(dbFile);
//                byte[] buffer = new byte[is.available()];
//                is.read(buffer);
//                fos.write(buffer);
//                is.close();
//                fos.close();
                database = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
            }

            return database;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 判断某个数据表是否存在
     *
     * @param db
     * @param tabName
     * @return
     */
    public static boolean tabIsExist(SQLiteDatabase db, String tabName) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
                    + tabName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除数据库中指定数据表
     *
     * @param db
     * @param tabName
     */
    public static void deleteTableData(SQLiteDatabase db, String tabName) {
        // 删除指定数据表的数据
        db.delete(tabName, null, null);
        // 将数据库表的索引值初始化
        ContentValues values = new ContentValues();
        values.put("seq", 0);
        db.update("sqlite_sequence", values, "name=?", new String[]{tabName});
        // db.close();
    }
}
