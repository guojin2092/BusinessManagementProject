package com.infrastructure.parallax;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 嵌入式的ListView
 * 
 * @author Administrator
 * 
 */
public class NestListView extends ListView {

	public NestListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public NestListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NestListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
