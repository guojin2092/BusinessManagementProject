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

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.infrastructure.base.BaseActivity;
import com.infrastructure.base.BaseBottomWindow;
import com.infrastructure.utils.CommonUtil;
import com.infrastructure.utils.ContactInfoUtil;
import com.infrastructure.utils.StringUtil;
import com.simple.lib.R;

/**通用编辑个人资料文本界面
 * @author Lemon
 * @use toActivity或startActivityForResult(EditTextInfoWindow.createIntent) > onActivityResult方法内data.getStringExtra(
 * SelectPictureActivity.RESULT_EDIT_TEXT_INFO)可得到输入框内容(String)
 */
public class EditTextInfoWindow extends BaseBottomWindow implements OnClickListener {
	//	private static final String TAG = "EditTextInfoWindow";

	/**
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public static Intent createIntent(Context context, String key, String value) {
		return createIntent(context, key, value, "zuo.biao.library");
	}
	/**
	 * @param context
	 * @param key
	 * @param value
	 * @param packageName
	 * @return
	 */
	public static Intent createIntent(Context context, String key, String value, String packageName) {
		return createIntent(context, 0, key, value, packageName);
	}
	/**
	 * @param context
	 * @param type
	 * @param key
	 * @param value
	 * @param packageName type == TYPE_MAILADDRESS || type == TYPE_USUALADDRESS时必须不为空
	 * @return
	 */
	public static Intent createIntent(Context context, int type, String key, String value, String packageName) {
		return new Intent(context, EditTextInfoWindow.class).
				putExtra(INTENT_TYPE, type).
				putExtra(INTENT_KEY, key).
				putExtra(INTENT_VALUE, value).
				putExtra(INTENT_PACKAGE_NAME, packageName);
	}

	@NonNull
	public BaseActivity getActivity() {
		return this;
	}

	private int MaxLen = 30;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_text_info_window);

		//必须调用<<<<<<<<<<<
		initView();
		initData();
		initListener();
		//必须调用>>>>>>>>>>

	}

	@Override
	public void initVariables() {

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView tvEditTextInfoTitle;
	private TextView tvEditTextInfoPlace;
	private EditText etEditTextInfo;
	private View ivEditTextInfoClear;
	private TextView tvEditTextInfoRemind;
	@Override
	public void initView() {//必须调用
		super.initView();

		tvEditTextInfoTitle = (TextView) findViewById(R.id.tvEditTextInfoTitle);

		tvEditTextInfoPlace = (TextView) findViewById(R.id.tvEditTextInfoPlace);
		tvEditTextInfoPlace.setVisibility(View.GONE);

		etEditTextInfo = (EditText) findViewById(R.id.etEditTextInfo);
		ivEditTextInfoClear = findViewById(R.id.ivEditTextInfoClear);
		tvEditTextInfoRemind = (TextView) findViewById(R.id.tvEditTextInfoRemind);
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	public static final String INTENT_PACKAGE_NAME = "INTENT_PACKAGE_NAME";

	public static final int TYPE_NICK = 200 + ContactInfoUtil.TYPE_NICK;
	public static final int TYPE_NAME = 200 + ContactInfoUtil.TYPE_NAME;

	public static final int TYPE_SFZ = 200 + ContactInfoUtil.TYPE_SFZ;
	public static final int TYPE_PHONE = 200 + ContactInfoUtil.TYPE_PHONE;
	public static final int TYPE_WEBSITE = 200 + ContactInfoUtil.TYPE_WEBSITE;
	public static final int TYPE_EMAIL = 200 + ContactInfoUtil.TYPE_EMAIL;
	public static final int TYPE_FAX = 200 + ContactInfoUtil.TYPE_FAX;

	public static final int TYPE_USUALADDRESS = 200 + ContactInfoUtil.TYPE_USUALADDRESS;
	public static final int TYPE_MAILADDRESS = 200 + ContactInfoUtil.TYPE_MAILADDRESS;
	public static final int TYPE_SCHOOL = 200 + ContactInfoUtil.TYPE_SCHOOL;
	public static final int TYPE_COMPANY = 200 + ContactInfoUtil.TYPE_COMPANY;

	public static final int TYPE_PROFESSION = 200 + ContactInfoUtil.TYPE_PROFESSION;
	public static final int TYPE_NOTE = 200 + ContactInfoUtil.TYPE_NOTE;
	//	public static final int TYPE_OTHER = 200 + ContactInfoUtil.TYPE_OTHER;

	public static final String INTENT_TYPE = "INTENT_TYPE";
	public static final String INTENT_KEY = "INTENT_KEY";
	public static final String INTENT_VALUE = "INTENT_VALUE";

	private String packageName;
	private int intentType = 0;
	@Override
	public void initData() {//必须调用
		super.initData();

		intent = getIntent();
		packageName = intent.getStringExtra(INTENT_PACKAGE_NAME);

		intentType = intent.getIntExtra(INTENT_TYPE, 0);
		if (StringUtil.isNotEmpty(intent.getStringExtra(INTENT_KEY), true)) {
			tvEditTextInfoTitle.setText(StringUtil.getCurrentString());
		}
		etEditTextInfo.setSingleLine(intentType != TYPE_NOTE);

		switch (intentType) {
		case TYPE_NICK:
			MaxLen = 20;
			break;
		case TYPE_SFZ:
			MaxLen = 18;
			break;
		case TYPE_PHONE:
				MaxLen = 11;
				break;
		case TYPE_EMAIL:
			etEditTextInfo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			MaxLen = 60;
			break;
		case TYPE_WEBSITE:
			etEditTextInfo.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
			MaxLen = 60;
			break;
		case TYPE_MAILADDRESS:
			MaxLen = 60;
			break;
		case TYPE_PROFESSION:
			tvEditTextInfoRemind.setText("所属行业");
			MaxLen = 15;
		case TYPE_NOTE:
			MaxLen = 100;
			break;
		default:
			MaxLen = 30;
			break;
		}
		etEditTextInfo.setMaxEms(MaxLen);
		tvEditTextInfoRemind.setText("限" + MaxLen/2 + "个字（或" + MaxLen + "个字符）");

		if (intentType == TYPE_MAILADDRESS || intentType == TYPE_USUALADDRESS) {
			tvEditTextInfoPlace.setVisibility(View.VISIBLE);

		}

	}

	@Override
	@Nullable
	protected String getTitleName() {
		return getIntent().getStringExtra(INTENT_TITLE);
	}

	private void saveAndExit() {
		String editedValue = StringUtil.getTrimedString(tvEditTextInfoPlace) + StringUtil.getTrimedString(etEditTextInfo);
		if (editedValue.equals("" + getIntent().getStringExtra(INTENT_VALUE))) {
			finish();
		} else {
			intent = new Intent();
			intent.putExtra(RESULT_TYPE, getIntent().getIntExtra(INTENT_TYPE, -1));
			intent.putExtra(RESULT_VALUE, editedValue);
			setResult(RESULT_OK, intent);
			finish();
		}		
	}

	//data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//listener事件监听区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private String inputedString;
	@Override
	public void initListener() {//必须调用
		super.initListener();

		findViewById(R.id.tvEditTextInfoReturn).setOnClickListener(this);
		findViewById(R.id.tvEditTextInfoForward).setOnClickListener(this);
		tvEditTextInfoPlace.setOnClickListener(this);

		etEditTextInfo.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				inputedString = StringUtil.getTrimedString(s);
				if (StringUtil.isNotEmpty(inputedString, true) == false) {
					ivEditTextInfoClear.setVisibility(View.GONE);
				} else {
					ivEditTextInfoClear.setVisibility(View.VISIBLE);
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		ivEditTextInfoClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				etEditTextInfo.setText("");
			}
		});

		etEditTextInfo.setText(StringUtil.getTrimedString(getIntent().getStringExtra(INTENT_VALUE)));
		etEditTextInfo.setSelection(StringUtil.getLength(etEditTextInfo, true));

	}


	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	public static final String RESULT_TYPE = "RESULT_TYPE";
	public static final String RESULT_KEY = "RESULT_KEY";
	public static final String RESULT_VALUE = "RESULT_VALUE";
	public static final String RESULT_URL = "RESULT_URL";
	public static final String RESULT_ID = "RESULT_ID";
	public static final String RESULT_IMAGE_URL = "RESULT_IMAGE_URL";
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tvEditTextInfoReturn) {
			finish();
		} else if (v.getId() == R.id.tvEditTextInfoForward) {
			saveAndExit();
		} else if (v.getId() == R.id.tvEditTextInfoPlace) {

		}
	}




	//类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final int REQUEST_TO_PLACE_PICKER = 11;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_TO_PLACE_PICKER:

				break;
			default:
				break;
			}
		}


	}


	//类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//listener事件监听区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}