package com.africa.crm.businessmanagement.main.photo.camera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.africa.crm.businessmanagement.BuildConfig;
import com.africa.crm.businessmanagement.main.photo.FileUtils;
import com.africa.crm.businessmanagement.main.photo.crop.CropActivity;
import com.africa.crm.businessmanagement.main.photo.crop.CropUtil;
import com.africa.crm.businessmanagement.widget.LogUtil;

import java.io.File;
import java.io.IOException;

/**
 * 作者: 51hs_android
 * 时间: 2017/3/16
 * 简介:
 */

public class CameraCore {


    private final String TAG = "CameraCore";

    private final int TAKE_PHOTO = 0X110;
    private final int ALBUM = 0X111;
    private final int CROP = 0x112;

    public static final int FILE_LOSE = 0x114;
    public static final int ZIP_MISTAKE = 0x115;
    public static final int PERMISSION = 0x116;
    public static final int SDCARD_MISTAKE = 0x117;

    private final int PHOTO_PERMISSION = 0X210;

    private final int ALBUM_PERMISSION = 0X211;


    private boolean needCrop;

    private ZipInfo zipInfo;

    private Activity activity;

    private Uri photoUri;

    private CameraResult result;
    private File mParentFile;

    private CameraCore(CameraCore.Builder builder) {
        this.result = builder.result;
        this.activity = (Activity) result;
        this.needCrop = builder.needCrop;
        this.zipInfo = builder.zipInfo;

    }

    public static class Builder {
        private CameraResult result;

        private boolean needCrop;

        private ZipInfo zipInfo;

        public Builder(CameraResult result) {
            this.result = result;
        }

        public Builder setNeedCrop(boolean needCrop) {
            this.needCrop = needCrop;
            return this;
        }

        public Builder setZipInfo(ZipInfo zipInfo) {
            this.zipInfo = zipInfo;
            return this;
        }

        public CameraCore build() {
            return new CameraCore(this);
        }

    }


    public void openCamera() {

//        this.picPath = ApiConfig.EXTER_PATH+new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(System.currentTimeMillis())+".jpg";
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || activity.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE}, PHOTO_PERMISSION);
            } else {
                doOpenCamera();
            }
        } else {
            doOpenCamera();
        }
    }

    private void doOpenCamera() {
        File parentFile = FileUtils.createFileDir(FileUtils.CAMERA_IMAGE);

        if (parentFile != null) {

            photoUri = Uri.fromFile(new File(parentFile, String.valueOf(System.currentTimeMillis()) + ".jpg"));

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", new File(parentFile, String.valueOf(System.currentTimeMillis()) + ".jpg")));//将拍取的照片保存到指定URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            LogUtil.d(TAG, "doOpenCamera: " + FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", new File(parentFile, String.valueOf(System.currentTimeMillis()) + ".jpg")).getPath());

            activity.startActivityForResult(intent, TAKE_PHOTO);

        } else {
            result.fail(SDCARD_MISTAKE, "存储卡出错!");

        }
    }

    public void openAlbum() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, ALBUM_PERMISSION);
            } else {
                doOpenAlbum();
            }
        } else {
            doOpenAlbum();
        }
    }

    private void doOpenAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");//从所有图片中进行选择
        activity.startActivityForResult(intent, ALBUM);
    }

    public void openCrop(Uri uri) {
        Intent intent = new Intent(activity, CropActivity.class);
        intent.putExtra(CropActivity.PIC_URI, uri);
        activity.startActivityForResult(intent, CROP);
    }


    /**
     * 根据图片的宽高,和imageview的宽高,计算出来的压缩比例
     *
     * @param options
     * @return
     */
    private int getSampleSize(BitmapFactory.Options options, int width, int height) {
        int realWidth = options.outWidth;
        int realHeight = options.outHeight;
        int reqWidth;
        int reqHeight;
        if (realWidth > realHeight) {
            reqWidth = height;
            reqHeight = width;
        } else {
            reqWidth = width;
            reqHeight = height;

        }
        if (realWidth > reqWidth || realHeight > reqHeight) {
            //需要进行压缩;
            int widthSize = Math.round(realHeight / reqHeight);
            int heightSize = Math.round(realWidth / reqWidth);
            return widthSize > heightSize ? widthSize : heightSize;
        }
        return 1;
    }

    private int getZipQuality(long size, long targetSize) {


        int zipPro = (int) (targetSize * 1.0 / size * 100);
        Log.d(TAG, "getZipQuality: " + zipPro);
        return zipPro < 50 ? 50 : zipPro;

    }

    public void onResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            String picPath = null;
            switch (requestCode) {
                case TAKE_PHOTO:
                    if (needCrop) {
                        openCrop(photoUri);
                        return;
                    } else {
                        picPath = photoUri.getPath();
                    }
                    break;
                case ALBUM:
                    photoUri = data.getData();
                    String pic = getPathByUri(photoUri);
                    if (pic != null) {
                        if (needCrop) {
                            openCrop(photoUri);
                            return;
                        } else {
                            picPath = pic;
                        }
                    }
                    break;
                case CROP:
                    photoUri = data.getData();
                    String picCrop = photoUri.getPath();
                    if (picCrop != null) {
                        picPath = picCrop;
                    }
                    break;
            }
            if (picPath != null && picPath.length() > 0) {
                if (zipInfo != null && zipInfo.needZip && new File(picPath).length() > zipInfo.zipSize) {
                    mParentFile = FileUtils.createFileDir(FileUtils.ZIP_IMAGE);
                    new ZipTask().execute(picPath);
                } else {
                    result.success(picPath);
                }
            } else {
                result.fail(FILE_LOSE, "图片查找失败");
            }
        } else if (resultCode == CropActivity.RESULT_CROP_FAIL) {
            result.fail(resultCode, "暂不支持该图片格式，请重新选择！");
        }

    }

    //另开县城进行压缩

    private class ZipTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            return zipPhoto(new File(params[0]), zipInfo);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                result.success(s);
            } else {
                result.fail(ZIP_MISTAKE, "内存卡出错，请调整!");
            }
        }
    }

    //循环压缩图片

    public String zipPhoto(File picFile, ZipInfo zipInfo) {

        if (mParentFile != null) {

            File file1 = new File(mParentFile, String.valueOf(System.currentTimeMillis()) + ".jpg");
            if (!file1.exists()) {
                try {
                    file1.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.d(TAG, "zipPhoto: " + file1.getPath());

            //压缩和显示图片
            BitmapFactory.Options options = new BitmapFactory.Options();
            //负责加载图片但是不保存到内存中,
            options.inJustDecodeBounds = true;
            //设置图片质量
            BitmapFactory.decodeFile(picFile.getPath(), options);
            options.inSampleSize = getSampleSize(options, zipInfo.zipWidth, zipInfo.zipHeight);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(picFile.getPath(), options);

            Uri destination = Uri.fromFile(file1);
            CropUtil.saveOutput(activity.getApplicationContext(), destination, bitmap, 100);

            int quality = 90;
            while (zipInfo.zipSize < file1.length()) {
                CropUtil.saveOutput(activity.getApplicationContext(), destination, bitmap, quality);
                quality -= 10;
            }

            bitmap.recycle();
            return destination.getPath();

        } else {
            return null;
        }

    }


    public void onPermission(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHOTO_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    doOpenCamera();
                } else {
                    result.fail(PERMISSION, "需要赋予权限");
                }

                break;
            case ALBUM_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doOpenAlbum();
                } else {
                    result.fail(PERMISSION, "需要赋予权限");

                }
                break;
        }

    }

    private String getPathByUri(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        //从系统表中查询指定Uri对应的照片
        Cursor cursor = activity.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);  //获取照片路径
        cursor.close();
        return picturePath;
    }

    public interface CameraResult {
        void success(String path);

        void fail(int code, String message);

    }


    public static class ZipInfo {
        boolean needZip;
        int zipWidth;
        int zipHeight;
        int zipSize;

        public ZipInfo(boolean needZip, int zipWidth, int zipHeight, int zipSize) {
            this.needZip = needZip;
            this.zipWidth = zipWidth;
            this.zipHeight = zipHeight;
            this.zipSize = zipSize;
        }
    }

}
