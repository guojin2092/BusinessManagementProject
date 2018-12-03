package baseutil.library.special.view.scrollview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;

/**
 * 左右有弹力的水平滑动一张图片
 * 
 * @author Administrator
 * 
 * 
 */
public class ElasticHorizontalScrollView extends HorizontalScrollView {

	// 移动因子, 是一个百分比, 比如手指移动了100px, 那么View就只移动50px
	// 目的是达到一个延迟的效果
	private static final float MOVE_FACTOR = 0.4f;

	// 松开手指后, 界面回到正常位置需要的动画时间
	private static final int ANIM_TIME = 300;

	// ScrollView的子View， 也是ScrollView的唯一一个子View
	private View contentView;

	// 手指按下时的X值, 用于在移动时计算移动距离
	// 如果按下时不能左拉和右拉， 会在手指移动时更新为当前手指的X值
	private float startX;

	// 用于记录正常的布局位置
	private Rect originalRect = new Rect();

	// 手指按下时记录是否可以继续左拉
	private boolean canPullLeft = false;

	// 手指按下时记录是否可以继续右拉
	private boolean canPullRight = false;

	// 在手指滑动的过程中记录是否移动了布局
	// private boolean isMoved = false;
	private int total;

	public ElasticHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ElasticHorizontalScrollView(Context context) {
		super(context);
	}

	@SuppressLint("MissingSuperCall")
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			contentView = getChildAt(0);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		if (contentView == null)
			return;

		// ScrollView中的唯一子控件的位置信息, 这个位置信息在整个控件的生命周期中保持不变
		originalRect.set(contentView.getLeft(), contentView.getTop(),
				contentView.getRight(), contentView.getBottom());
	}

	/**
	 * 在触摸事件中, 处理左拉和右拉的逻辑
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if (contentView == null) {
			return super.dispatchTouchEvent(ev);
		}

		int action = ev.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:

			// 判断是否可以左拉和右拉
			canPullLeft = isCanPullLeft();
			canPullRight = isCanPullRigth();

			// 记录按下时的X值
			startX = ev.getX();
			break;

		case MotionEvent.ACTION_UP:

			// if (!isMoved)
			// break; // 如果没有移动布局， 则跳过执行

			// 开启动画
			TranslateAnimation anim = new TranslateAnimation(
					contentView.getLeft(), originalRect.left, 0, 0);
			anim.setDuration(ANIM_TIME);
			//
			contentView.startAnimation(anim);

			// 设置回到正常的布局位置
			contentView.layout(originalRect.left, originalRect.top,
					originalRect.right, originalRect.bottom);

			// 将标志位设回false
			canPullRight = false;
			canPullLeft = false;
			// isMoved = false;
			total = 0;
			break;
		case MotionEvent.ACTION_MOVE:

			// 在移动的过程中， 既没有滚动到可以上拉的程度， 也没有滚动到可以下拉的程度
			if (!canPullLeft && !canPullRight) {
				startX = ev.getX();
				canPullLeft = isCanPullLeft();
				canPullRight = isCanPullRigth();

				break;
			}

			// 计算手指移动的距离
			float nowX = ev.getX();
			int deltaX = (int) (nowX - startX);
			// Log.i("ElasticHorizontalScrollView","nowX"+ nowX);
			// total= total+deltaX;
			// Log.i("ElasticHorizontalScrollView","total"+ total);
			// 是否应该移动布局
			boolean shouldMove = (canPullLeft && deltaX > 0) // 可以右拉， 并且手指向右移动
					|| (canPullRight && deltaX < 0) // 可以左拉， 并且手指向左移动
					|| (canPullLeft && canPullRight); // 既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）

			if (shouldMove) {
				// 计算偏移量
				int offset = (int) (deltaX * MOVE_FACTOR);

				// 随着手指的移动而移动布局
				contentView.layout(originalRect.left + offset,
						originalRect.top, originalRect.right + offset,
						originalRect.bottom);

				// isMoved = true; // 记录移动了布局
			}

			break;
		default:
			break;
		}

		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 判断是否滚动到左边
	 */
	private boolean isCanPullLeft() {
		return getScrollX() == 0
				|| contentView.getLeft() < getLeft() + getScrollX();
	}

	/**
	 * 判断是否滚动到右面
	 */
	private boolean isCanPullRigth() {
		return contentView.getRight() <= getRight() + getScrollX();
	}
}
