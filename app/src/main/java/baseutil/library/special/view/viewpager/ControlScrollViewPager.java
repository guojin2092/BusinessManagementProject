package baseutil.library.special.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Project：OS
 * Author:  daxiongzaici
 * Version: 1.0.0
 * Description：
 * Date：15/12/1 下午6:54
 * Modification  History:
 * Why & What is modified:
 */
public class ControlScrollViewPager extends ViewPager {
    private boolean scrollable = true;

    public ControlScrollViewPager(Context context) {
        super(context);
    }

    public ControlScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean enable) {
        scrollable = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (scrollable) {
            return super.onInterceptTouchEvent(event);
        } else {
            return false;
        }
    }
}

