package baselibrary.library.util;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 截取多屏幕的view
 * 
 * @author Administrator
 * 
 */
public class ScreenShot {
	private OnJumpToActivity jumpToActivity;

	/**
	 * 保存多屏界面的图片
	 * 
	 * @param View
	 * @param imgName
	 */
	// public void shootView(View View, String imgName) {
	// try {
	// BitmapUtils.SaveBitmapToFile(
	// OutRoute.getInstance(View.getContext()).getStrRoute()
	// + ConfigData.SAVE_SHARE_PICTURE, imgName,
	// getBitmapFromView(View));
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 保存单个界面的图片
	 * 
	 * @param View
	 * @param imgName
	 */
	// public void shootSingleView(View View, String imgName) {
	// try {
	// BitmapUtils.SaveBitmapToFile(
	// OutRoute.getInstance(View.getContext()).getStrRoute()
	// + ConfigData.SAVE_SHARE_PICTURE, imgName,
	// getBitmapFromSingleView(View));
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 单个界面的截图
	 * 
	 * @param view
	 * @return
	 */
	public Bitmap getBitmapFromSingleView(View view) {
		Bitmap bitmap = null;
		view.setDrawingCacheEnabled(true);
		view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
		bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		jumpToActivity.jumpToActivity();
		return bitmap;
	}

	/**
	 * 将多屏view转为bitmap
	 * 
	 * @param view
	 * @return
	 */
	public Bitmap getBitmapFromView(View view) {
		Bitmap bitmap = null;
		// view.setDrawingCacheEnabled(true);
		// int width = View.MeasureSpec.makeMeasureSpec(0,
		// View.MeasureSpec.UNSPECIFIED);
		// int height = View.MeasureSpec.makeMeasureSpec(0,
		// View.MeasureSpec.UNSPECIFIED);
		//
		// view.measure(width, height);
		// view.layout(0, 0, view.getMinimumWidth(), view.getMeasuredHeight());
		// // 横向内容不改变
		// view.setMinimumWidth(view.getMinimumWidth());
		// view.setMinimumHeight(view.getMeasuredHeight());
		// view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
		// bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
		// view.getMeasuredHeight(), Bitmap.Config.RGB_565);
		// Canvas canvas = new Canvas(bitmap);
		// view.draw(canvas);
		// view.setDrawingCacheEnabled(false);
		// view.destroyDrawingCache();
		// jumpToActivity.jumpToActivity();
		return bitmap;
	}

	/**
	 * 截图完成后跳转activity
	 * 
	 * @author Administrator
	 * 
	 */
	public interface OnJumpToActivity {
		public void jumpToActivity();
	}

	/**
	 * 设置监听跳转
	 * 
	 * @param jumpToActivity
	 */
	public void setJumpToActivityListener(OnJumpToActivity jumpToActivity) {
		this.jumpToActivity = jumpToActivity;
	}
}