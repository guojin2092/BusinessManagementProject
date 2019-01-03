package com.africa.crm.businessmanagement.main.photo.crop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.photo.FileUtils;

import java.io.File;


/**
 * 作者: 51hs_android
 * 时间: 2017/3/16
 * 简介:
 */
public class CropActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CropActivity";


    public static final String PIC_URI = "picUri";

    public static final int RESULT_CROP_FAIL = -10;
    private Uri picUri;
    private CropView cropView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        initData();
        initView();
    }

    private void initData() {

        picUri = getIntent().getParcelableExtra(PIC_URI);

        ImageView left = (ImageView) findViewById(R.id.left);
        ImageView right = (ImageView) findViewById(R.id.right);
        TextView delete = (TextView) findViewById(R.id.roadbook_delete);
        TextView sure = (TextView) findViewById(R.id.roadboos_crop_ok);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        delete.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    private void initView() {
        cropView = (CropView) findViewById(R.id.cropView);
        cropView.of(picUri)
                .asSquare()
                .withOutputSize(600, 600)
                .initialize(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                cropView.changeRotation(cropView.getChangeRotation() + 90);
                break;
            case R.id.right:
                cropView.changeRotation(cropView.getChangeRotation() - 90);
                break;
            case R.id.roadbook_delete:
                setResult(RESULT_CROP_FAIL,  getIntent());
                finish();
                break;
            case R.id.roadboos_crop_ok:
                Intent intent = getIntent();
                File parentFile = FileUtils.createFileDir(FileUtils.CROP_IMAGE);
                Bitmap crop = cropView.getOutput();
                if (parentFile != null && crop != null) {
                    File file1 = new File(parentFile, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    Uri destination = Uri.fromFile(file1);
                    CropUtil.saveOutput(this, destination, crop, 100);
                    crop.recycle();
                    intent.setData(destination);
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_CROP_FAIL, intent);
                }
                finish();
                break;
        }
    }

}
