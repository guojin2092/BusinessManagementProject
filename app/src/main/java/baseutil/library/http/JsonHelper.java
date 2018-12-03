package baseutil.library.http;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;


/**
 * 发现
 * 
 * @author Administrator
 * 
 */
public class JsonHelper extends SQLiteOpenHelper {
	private static JsonHelper jsonHelper;
	// 数据库名称
	public static final String DBNAME = "JsonCache";
	// 酒店
	public static final String TABLENAME = "jsoncache";

	// 数据库版本
	public static final int VERSION = 1;

	public static JsonHelper getInstance(Context context) {
		if (jsonHelper == null) {
			synchronized (JsonHelper.class) {
				if (jsonHelper == null) {
					jsonHelper = new JsonHelper(context);
				}
			}
		}
		return jsonHelper;
	}

	private JsonHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	private JsonHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table if not exists "
				+ TABLENAME
				+ "(id integer primary key autoincrement,keyurl TEXT,valuejson TEXT )");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * 插入
	 * 
	 * @param jsonTable
	 * @param keyUrl
	 * @param valueJson
	 */
	public void insertJson(String jsonTable, String keyUrl, String valueJson) {
		SQLiteDatabase db = getWritableDatabase();

		try {
			if (!TextUtils.isEmpty(keyUrl)) {
				ContentValues values = new ContentValues();

				values.put("valuejson", valueJson);
				if (isExistJson(jsonTable, keyUrl)) {
					db.update(jsonTable, values, "keyurl=?",
							new String[] { keyUrl });
				} else {
					values.put("keyurl", keyUrl);
					db.insert(jsonTable, null, values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 查询这个keyUrl是否存在
	 * 
	 * @param jsonTable
	 * @param keyUrl
	 * @return
	 */
	public boolean isExistJson(String jsonTable, String keyUrl) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.query(jsonTable, new String[] { "keyurl" },
					"keyurl = ? ", new String[] { keyUrl }, null, null, null);
			return cursor.getCount() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return false;
	}

	/**
	 * 查询数据库，返回
	 * 
	 *
	 * @param jsonTable
	 * @param keyUrl
	 * @return
	 */
	public String queryJsonObject(String jsonTable, String keyUrl) {
		String jsonBean = null;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.query(jsonTable,
					new String[] { "keyurl", "valuejson" }, "keyurl = ? ",
					new String[] { keyUrl }, null, null, null);

			if (cursor.getCount() > 0) {
				while (!cursor.isLast()) {
					cursor.moveToNext();
					jsonBean = cursor.getString(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			if (db != null) {
				db.close();
			}
		}
		return jsonBean;
	}

	/**
	 * 删除数据库中
	 * 
	 * @param searchTable
	 */
	public void deleteSearchData(String searchTable) {
		SQLiteDatabase db = getWritableDatabase();

		try {
			DatabaseUtils.deleteTableData(db, searchTable);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}
}
