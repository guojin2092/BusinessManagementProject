package com.infrastructure.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片的工具类
 *
 * @author Administrator
 */
public class ImageUtils {

    /**
     * Stores an image on the storage
     *
     * @param image       the image to store.
     * @param pictureFile the file in which it must be stored
     */
    public static void storeImage(Bitmap image, File pictureFile) {
        if (pictureFile == null) {
            Log.d("ImageUtils",
                    "Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("ImageUtils", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("ImageUtils", "Error accessing file: " + e.getMessage());
        }
    }

    /**
     * 针对网络图片 如果为0,返回0
     *
     * @param imgOrgWidth
     * @param imgOrgHeight
     * @param width
     * @return
     */
    public static int getImageHeight(String imgOrgWidth, String imgOrgHeight,
                                     int width) {
        int widthSize = Integer.parseInt(imgOrgWidth);
        if (widthSize <= 0)
            return 0;
        return Integer.parseInt(imgOrgHeight) * width / widthSize;
    }

    public static float getImageHeight(float imgOrgWidth, float imgOrgHeight,
                                       float width) {
        if (imgOrgWidth <= 0)
            return 0;
        return imgOrgHeight * width / imgOrgWidth;
    }

    /**
     * 针对本地图片
     *
     * @param imgOrgWidth
     * @param imgOrgHeight
     * @param width
     * @return
     */
    public static int getImageHeight(int imgOrgWidth, int imgOrgHeight,
                                     int width) {
        if (imgOrgWidth <= 0)
            return 0;
        return imgOrgHeight * width / imgOrgWidth;
    }

    /**
     * @param imgOrgHeight
     * @param imgOrgHeightIn
     * @param height
     * @return
     */
    public static int getImageHeightWithHeight(int imgOrgHeight, int imgOrgHeightIn,
                                               int height) {
        if (imgOrgHeight <= 0)
            return 0;
        return imgOrgHeightIn * height / imgOrgHeight;
    }

    public static String getImageType(String url) {
        String type = url.substring(url.lastIndexOf("") + 1);

        // if (type.equals("jpg"))
        type = "image/jpeg";
        // else if (type.equals("png"))
        // type = "image/png";

        return type;
    }

    /**
     *
     * @param imgOrgWidth
     * @param imgOrgHeight
     * @param height
     * @return
     */
    public static int getImageWidth(int imgOrgWidth, int imgOrgHeight,
                                    int height) {
        if (imgOrgHeight <= 0)
            return 0;
        return imgOrgWidth * height / imgOrgHeight;
    }

}