package com.africa.crm.businessmanagement.baseutil.library.special.view.ImageView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 用户头像背景
 * 
 * @author Administrator
 * 
 */
public class MaskImageView extends ImageView {

	public MaskImageView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setColorFilter(new PorterDuffColorFilter(0x66000000,
				PorterDuff.Mode.SRC_ATOP));
	}

}