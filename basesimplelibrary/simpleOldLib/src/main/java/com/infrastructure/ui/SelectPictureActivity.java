/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.infrastructure.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.infrastructure.base.BaseActivity;
import com.infrastructure.utils.CommonUtil;
import com.infrastructure.utils.DataKeeper;
import com.simple.lib.R;

import java.io.File;

/**通用选择单张照片Activity,已自带选择弹窗
 * @author Lemon
 * @use toActivity或startActivityForResult(SelectPictureActivity.createIntent) > onActivityResult方法内data.getStringExtra(
 * SelectPictureActivity.RESULT_PICTURE_PATH)可得到图片存储路径(String)
 */
public class SelectPictureActivity extends BaseActivity implements OnClickListener {
	@SuppressWarnings("unused")
	private static final String TAG = "SelectPictureActivity";


	public static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 200;
	public static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 300;
	/**
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, SelectPictureActivity.class);
	}
	
	@Override
	public void initVariables() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 跳转到个人信息
	 *
	 * @param activity
	 */
	public static void startActivityForResult(Activity activity,int request) {
		Intent intent = new Intent(activity, SelectPictureActivity.class);
		activity.startActivityForResult(intent, request);
		activity.overridePendingTransition(R.anim.right_push_in, R.anim.hold);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_picture_activity);
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		context = this;
		isAlive = true;
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		//功能归类分区方法，必须调用<<<<<<<<<<
		initVariables();
		initView();
		initData();
		initListener();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initView() {//必须调用
		//TODO
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private String picturePath = "";
	@Override
	public void initData() {//必须调用

	}

	private File cameraFile;
	/**
	 * 照相获取图片
	 */
	public void selectPicFromCamera() {
		if (!CommonUtil.isExitsSdcard()) {
			showShortToast("SD卡不存在，不能拍照");
			return;
		}

		intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后照片的储存路径
		cameraFile = new File(DataKeeper.fileRootPath + DataKeeper.accountPath, "photo" + System.currentTimeMillis() + ".jpg");
		cameraFile.getParentFile().mkdirs();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
		toActivity(intent, REQUEST_CODE_CAMERA);
	}


	/**
	 * 从图库获取图片
	 */
	public void selectPicFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
		} else {
			intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		toActivity(intent, REQUEST_CODE_LOCAL);
	}

	public static final String RESULT_PICTURE_PATH = "RESULT_PICTURE_PATH";
	/**根据图库图片uri发送图片
	 * @param selectedImage
	 */
	private void sendPicByUri(Uri selectedImage) {
		Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex("_data");
			picturePath = cursor.getString(columnIndex);
			cursor.close();
			cursor = null;

			if (picturePath == null || picturePath.equals("null")) {
				Toast toast = Toast.makeText(this, "找不到图片", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
		} else {
			File file = new File(selectedImage.getPath());
			if (!file.exists()) {
				Toast toast = Toast.makeText(this, "找不到图片", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;

			}
			picturePath = file.getAbsolutePath();
		}
		setResult(RESULT_OK, new Intent().putExtra(RESULT_PICTURE_PATH, picturePath));
	}

	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initListener() {//必须调用

		findViewById(R.id.llSelectPictureBg).setOnClickListener(this);

		toActivity(new Intent(context, BottomMenuWindow.class)
		.putExtra(BottomMenuWindow.INTENT_TITLE, "选择图片")
		.putExtra(BottomMenuWindow.INTENT_ITEMS, new String[]{"拍照", "图库"})
		, REQUEST_TO_BOTTOM_MENU, false);
	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.llSelectPictureBg) {
			finish();
		}
	}

	private void requestCameraPermission(){
		if(ContextCompat.checkSelfPermission(context,
				Manifest.permission.CAMERA)
				!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context,
				Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(context,
					new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},
					MY_PERMISSIONS_REQUEST_USE_CAMERA);
		}else{
			selectPicFromCamera();//照相
		}
	}

	private void requestReadStoragePermission(){
		if(ContextCompat.checkSelfPermission(context,
				Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(context,
					new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
					MY_PERMISSIONS_REQUEST_READ_STORAGE);
		}else{
			selectPicFromLocal();
		}
	}

	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final int REQUEST_TO_BOTTOM_MENU = 10;
	public static final int REQUEST_CODE_CAMERA = 18;
	public static final int REQUEST_CODE_LOCAL = 19;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_TO_BOTTOM_MENU:
				if (data != null) {
					switch (data.getIntExtra(BottomMenuWindow.RESULT_ITEM_ID, -1)) {
					case 0:
						requestCameraPermission();
						return;
					case 1:
						requestReadStoragePermission();
						return;
					default:
						break;
					}
				}
				break;
			case REQUEST_CODE_CAMERA: //发送照片
				if (cameraFile != null && cameraFile.exists()) {
					picturePath = cameraFile.getAbsolutePath();
					setResult(RESULT_OK, new Intent().putExtra(RESULT_PICTURE_PATH, picturePath));
				}
			case REQUEST_CODE_LOCAL: //发送本地图片
				if (data != null) {
					Uri selectedImage = data.getData();
					if (selectedImage != null) {
						sendPicByUri(selectedImage);
					}
				}
				break;
			default:
				break;
			}
		}

		finish();
	}

	@Override
	public void finish() {
		exitAnim = enterAnim = R.anim.null_anim;
		super.finish();
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_USE_CAMERA: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					selectPicFromCamera();
				} else {
					return;
				}
			break;
			}
			case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					selectPicFromLocal();
				} else {
					return;
				}
			}
		}
	}


	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}