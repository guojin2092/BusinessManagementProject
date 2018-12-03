package com.africa.crm.businessmanagement.baseutil.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {

	/**
	 * SaveBitmapToFile()
	 * 
	 * @param path
	 *            * @param name
	 * @param bitmap
	 **/
	public static void SaveBitmapToFile(final String path, final String name,
			Bitmap bitmap) throws IOException {

		File file = FileUtils.CreatFile(path, name);

		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存已经尺寸压缩
	 * 
	 * @param path
	 * @param bitmap
	 * @throws IOException
	 */
	public static void SaveSmallBitmapToFile(final String path, Bitmap bitmap)
			throws IOException {

		File file = new File(path);

		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (bitmap != null)
			bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fOut);

		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	// public static int calculateInSampleSize(BitmapFactory.Options options,
	// int reqWidth, int reqHeight) {
	// // Raw height and width of image
	// final int height = options.outHeight;
	// final int width = options.outWidth;
	// int inSampleSize = 1;
	//
	// if (height > reqHeight || width > reqWidth) {
	//
	// // Calculate ratios of height and width to requested height and
	// // width
	// final int heightRatio = Math.round((float) height
	// / (float) reqHeight) * 2;
	// final int widthRatio = Math.round((float) width / (float) reqWidth) * 2;
	//
	// // Choose the smallest ratio as inSampleSize value, this will
	// // guarantee
	// // a final image with both dimensions larger than or equal to the
	// // requested height and width.
	// inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	// }
	// if (inSampleSize <= 0)
	// inSampleSize = 1;
	// return inSampleSize;
	// }
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight) * 2;
			final int widthRatio = Math.round((float) width / (float) reqWidth) * 2;

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		if (inSampleSize <= 0)
			inSampleSize = 1;
		return inSampleSize;
	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String imagePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 720, 1280);
		options.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
		options.inPurgeable = true;// 同时设置才会有效
		options.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeFile(imagePath, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	// public static void SaveBitmapToPath(final String path, Bitmap bitmap)
	// throws IOException {
	//
	// File file = FileUtils.CreatPath(path);
	//
	// FileOutputStream fOut = null;
	// try {
	// fOut = new FileOutputStream(file);
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	//
	// bitmap.compress(Bitmap.CompressFormat.PNG, 70, fOut);
	//
	// try {
	// fOut.flush();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// try {
	// fOut.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * GetBitmapFromFile() * @param
	 * 
	 * @return
	 **/
	// public static Bitmap GetBitmapFromFile(final String path, int minWidth,
	// int minHeight) {
	//
	// BitmapFactory.Options opts = new BitmapFactory.Options();
	// opts.inJustDecodeBounds = true;
	// Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
	//
	// opts.inSampleSize = computeSampleSize(opts, -1, minWidth * minHeight);
	//
	// opts.inJustDecodeBounds = false;
	//
	// try {
	//
	// bitmap = BitmapFactory.decodeFile(path, opts);
	//
	// } catch (OutOfMemoryError err) {
	//
	// }
	//
	// // Bitmap bitmap = BitmapFactory.decodeFile(path);
	// return bitmap;
	// }

	/**
	 * GetBitmapFromFile() * @param
	 * 
	 * @return
	 **/
	public static Bitmap GetBitmapFromFile(final String path) {

		Bitmap bitmap = null;

		try {

			bitmap = BitmapFactory.decodeFile(path);

		} catch (OutOfMemoryError err) {

		}

		return bitmap;
	}

	public static Drawable getDrawableFromFile(final String path) {

		Bitmap bitmap = null;

		try {

			bitmap = BitmapFactory.decodeFile(path);

		} catch (OutOfMemoryError err) {

		}
		return changeBitmapToDrawable(bitmap);
	}

	public static Bitmap getBitmapFromAssets(final Context context,
			final String path) {
		try {
			return BitmapFactory.decodeStream(context.getResources()
					.getAssets().open(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getBitmapFromInputStream(final InputStream inputStream) {
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		return bitmap;
	}

	/**
	 * ChangeDrawableToBitmap()
	 * 
	 * @param drawable
	 * @return
	 **/
	public static Bitmap changeDrawableToBitmap(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bitmap = bd.getBitmap();
		return bitmap;
	}

	/**
	 * ChangeDrawableToBitmap()
	 * 
	 * @param Bitmap
	 * @return
	 **/
	@SuppressWarnings("deprecation")
	public static Drawable changeBitmapToDrawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}

	@SuppressWarnings("deprecation")
	public static Drawable getDrawableFromAssets(Context context,
			String fileNameInAssets) {
		Bitmap bitmap = null;
		BitmapDrawable drawable = null;

		try {
			bitmap = BitmapFactory.decodeStream(context.getResources()
					.getAssets().open(fileNameInAssets));
			drawable = new BitmapDrawable(bitmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return drawable;
	}

	/**
	 * 压缩指定路径图片
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	// public static Bitmap revitionImageSize(String path) throws IOException {
	// BufferedInputStream in = new BufferedInputStream(new FileInputStream(
	// new File(path)));
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true;
	// BitmapFactory.decodeStream(in, null, options);
	// in.close();
	// int i = 0;
	// Bitmap bitmap = null;
	// while (true) {
	// if ((options.outWidth >> i <= 1000)
	// && (options.outHeight >> i <= 1000)) {
	// in = new BufferedInputStream(
	// new FileInputStream(new File(path)));
	// options.inSampleSize = (int) Math.pow(2.0D, i);
	// options.inJustDecodeBounds = false;
	// bitmap = BitmapFactory.decodeStream(in, null, options);
	// break;
	// }
	// i += 1;
	// }
	// return bitmap;
	// }

	// public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
	//
	// // load the origial Bitmap
	// Bitmap BitmapOrg = bitmap;
	//
	// int width = BitmapOrg.getWidth();
	// int height = BitmapOrg.getHeight();
	// int newWidth = w;
	// int newHeight = h;
	//
	// // calculate the scale
	// float scaleWidth = ((float) newWidth) / width;
	// float scaleHeight = ((float) newHeight) / height;
	//
	// // create a matrix for the manipulation
	// Matrix matrix = new Matrix();
	// // resize the Bitmap
	// matrix.postScale(scaleWidth, scaleHeight);
	// // if you want to rotate the Bitmap
	// // matrix.postRotate(45);
	//
	// // recreate the new Bitmap
	// Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
	// height, matrix, true);
	//
	// // make a Drawable from Bitmap to allow to set the Bitmap
	// // to the ImageView, ImageButton or what ever
	// return resizedBitmap;
	//
	// }

	// public static int computeSampleSize(BitmapFactory.Options options,
	// int minSideLength, int maxNumOfPixels) {
	// int initialSize = computeInitialSampleSize(options, minSideLength,
	// maxNumOfPixels);
	// int roundedSize;
	// if (initialSize <= 8) {
	// roundedSize = 1;
	// while (roundedSize < initialSize) {
	// roundedSize <<= 1;
	// }
	// } else {
	// roundedSize = (initialSize + 7) / 8 * 8;
	// }
	// return roundedSize;
	// }

	/**
	 * 对图片压缩，压缩后图片小于100kb
	 * 
	 * @param image
	 * @return
	 */
	// 质量压缩
	// public static Bitmap compressImage(Bitmap bitmap) {
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);//
	// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
	// int options = 100;
	// while (baos.toByteArray().length / 1024 > 50) { //
	// 循环判断如果压缩后图片是否大于50kb,大于继续压缩
	// baos.reset();// 重置baos即清空baos
	// bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//
	// 这里压缩options%，把压缩后的数据存放到baos中
	// options -= 10;// 每次都减少10
	// }
	// ByteArrayInputStream isBm = new
	// ByteArrayInputStream(baos.toByteArray());//
	// 把压缩后的数据baos存放到ByteArrayInputStream中
	// return BitmapFactory.decodeStream(isBm, null, null);//
	// 把ByteArrayInputStream数据生成图片
	// }

	// public static byte[] Bitmap2Bytes(Bitmap bm) {
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
	// return baos.toByteArray();
	// }

	// private static int computeInitialSampleSize(BitmapFactory.Options
	// options,
	// int minSideLength, int maxNumOfPixels) {
	// double w = options.outWidth;
	// double h = options.outHeight;
	// int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
	// .sqrt(w * h / maxNumOfPixels));
	// int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
	// Math.floor(w / minSideLength), Math.floor(h / minSideLength));
	// if (upperBound < lowerBound) {
	// // return the larger one when there is no overlapping zone.
	// return lowerBound;
	// }
	// if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
	// return 1;
	// } else if (minSideLength == -1) {
	// return lowerBound;
	// } else {
	// return upperBound;
	// }
	// }
}
