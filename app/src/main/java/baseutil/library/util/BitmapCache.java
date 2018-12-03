package baseutil.library.util;

import android.graphics.Bitmap;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 图片的缓存
 * 
 * @author ww
 * 
 */
public class BitmapCache {

	// 为了保证流畅性，将当前显示的图片存储在Map中，在下一次需要获取此图时，直接从Map中调出，而不需要重新读取文件
	// 这里使用WeakReference可以在内存吃紧时释放bitmap，避免Out Of Memory
	private final static HashMap<String, WeakReference<Bitmap>> mBitmapCache = new HashMap<String, WeakReference<Bitmap>>();

	/**
	 * 根据图片名称获取图片
	 * 
	 * @param name
	 *            图片的的名称
	 * @param reqWidth
	 *            要求的图片显示宽度
	 * @param reqHeight
	 *            要求的图片显示长度
	 * @return 如果Map中有该图片，则返回该图，否则返回null
	 */
	public static Bitmap loadBitmap(String name) {

		// 如果缓存中存在图片，直接从缓存中读取
		WeakReference<Bitmap> wr = mBitmapCache.get(name);
		if (wr != null) {
			Bitmap bitmap = wr.get();
			if (null != bitmap)
				return bitmap;
			Log.i("", "bitmap has be released!");
		}
		return null;
	}

	/**
	 * 清除一个图片缓存
	 * 
	 * @param name
	 *            图片的名称
	 * @return 如果存在该项缓存并最后成功清除返回true
	 */
	public boolean clean(String name) {
		WeakReference<Bitmap> wr = mBitmapCache.get(name);
		if (wr == null)
			return false;

		Bitmap bitmap = wr.get();
		if (null != bitmap) {
			bitmap.recycle();
			bitmap = null;
		}
		mBitmapCache.remove(name);
		return true;
	}

	/**
	 * 清除全部图片缓存
	 */
	public void cleanAll() {
		Iterator<Map.Entry<String, WeakReference<Bitmap>>> iterator = mBitmapCache
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, WeakReference<Bitmap>> entry = (Map.Entry<String, WeakReference<Bitmap>>) iterator
					.next();
			WeakReference<Bitmap> wr = entry.getValue();
			Bitmap bitmap = wr.get();
			if (null != bitmap) {
				bitmap.recycle();
				bitmap = null;
			}
		}
		mBitmapCache.clear();
	}

	/**
	 * 添加一个缓存
	 * 
	 * @param name
	 * @param bitmap
	 */
	public static void add(String name, Bitmap bitmap) {
		WeakReference<Bitmap> wr = new WeakReference<Bitmap>(bitmap);
		mBitmapCache.put(name, wr);
	}

}
