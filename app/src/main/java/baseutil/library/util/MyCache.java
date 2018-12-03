package baseutil.library.util;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 缓存使用的文件
 * 
 * @author Administrator
 * 
 */
public class MyCache {

	private File cacheFile; // 文件缓存路径

	public MyCache(Context context) {
		this(context, "cacheName");
	}

	public MyCache(Context context, String cacheName) {
		if (!android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheFile = new File(context.getCacheDir(), cacheName);
		else
			cacheFile = new File(context.getExternalCacheDir(), cacheName);

		if (!cacheFile.exists())
			cacheFile.mkdir();
	}

	/**
	 * 保存bytes数组
	 * 
	 * @param key
	 *            保存的键名
	 * @param bs
	 *            byte数组
	 */
	public void put(String key, byte[] bs) {
		File file = newFile(key);

		if (!file.exists())
			saveBytes(file, bs);
	}

	/**
	 * 通过key读取byte数组
	 * 
	 * @param key
	 *            键名
	 * @return 返回的byte数组
	 */
	public byte[] getBytes(String key) {
		File file = newFile(key);
		return readBytes(file);
	}

	/** 清楚缓存 */
	public void clearCache() {
		for (File item : cacheFile.listFiles()) {
			item.delete();
		}

		cacheFile.delete();
	}

	/**
	 * 使用一个key创建一个文件
	 * 
	 * @param key
	 * @return 文件
	 */
	private File newFile(String key) {
		return new File(cacheFile, String.valueOf(key.hashCode()));
	}

	/**
	 * 将bytes数组保存在本地
	 * 
	 * @param savePath
	 *            路径名
	 * @param bytes
	 *            byte数组
	 */
	private void saveBytes(File savePath, byte[] bytes) {
		BufferedOutputStream stream = null;
		try {
			FileOutputStream fstream = new FileOutputStream(savePath);
			stream = new BufferedOutputStream(fstream);
			stream.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取byte数组
	 * 
	 * @param savePath
	 *            路径名
	 * @return byte数组
	 */
	private byte[] readBytes(File savePath) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(savePath);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

			byte[] temp = new byte[1024];

			int size = 0;

			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}

			byte[] bytes = out.toByteArray();

			return bytes;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}
