package baseutil.library.special.view.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 嵌入式的GridView
 * 
 * @author Administrator
 * 
 */
public class NestGridView extends GridView {

	public NestGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public NestGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NestGridView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
