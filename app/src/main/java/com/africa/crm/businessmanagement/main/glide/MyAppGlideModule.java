package com.africa.crm.businessmanagement.main.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.photo.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2019/1/3 0003 11:08
 * Modification  History:
 * Why & What is modified:
 */
@GlideModule
public final class MyAppGlideModule extends AppGlideModule {
    private static final String TAG = "MyAppGlideModule";
    int diskSize = 1024 * 1024 * 100;
    int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存

    @Override
    public boolean isManifestParsingEnabled() {
        return super.isManifestParsingEnabled();
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        //重新设置内存限制
        // 自定义内存和图片池大小
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (0.8 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (0.8 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
        File file = FileUtils.createFileDir(FileUtils.CACHE_IMAGE);
        if (file != null) {
            DiskCache.Factory diskCacheFactory = new DiskLruCacheFactory(file.getAbsolutePath(), diskSize);
            builder.setDiskCache(diskCacheFactory);
        }
        RequestOptions options = new RequestOptions().centerCrop()
                .error(R.drawable.iv_no_icon)
                .centerCrop()
                .encodeQuality(50)
                .format(DecodeFormat.PREFER_RGB_565);
        builder.setDefaultRequestOptions(options);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
