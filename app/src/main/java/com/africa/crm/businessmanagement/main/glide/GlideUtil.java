package com.africa.crm.businessmanagement.main.glide;

import android.widget.ImageView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.network.api.ApiConfig;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2019/1/3 0003 14:01
 * Modification  History:
 * Why & What is modified:
 */
public class GlideUtil {

    public static void showImg(ImageView imageView, String code) {
        String imgUrl = "";
        if (code.contains(".jpg")) {
            imgUrl = code;
        } else {
            imgUrl = ApiConfig.IMG_URL + code;
        }
        GlideApp.with(MyApplication.getInstance())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.iv_no_icon)
                .transform(new RoundedCorners(DensityUtil.dp2px(1000)))
                .into(imageView);
    }

    public static void showNormalImg(ImageView imageView, String code) {
        String imgUrl = "";
        if (code.contains(".jpg")) {
            imgUrl = code;
        } else {
            imgUrl = ApiConfig.IMG_URL + code;
        }
        GlideApp.with(MyApplication.getInstance())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.iv_no_icon)
                .into(imageView);
    }

    public static void showLocalImg(ImageView imageView, String imgPath) {
        GlideApp.with(MyApplication.getInstance())
                .load(imgPath)
                .centerCrop()
                .error(R.drawable.iv_upload_img)
                .into(imageView);
    }
}
