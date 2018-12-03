package baseutil.library.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AdaMyViewPager extends PagerAdapter {
	// private String TAG = "AdaMyViewPager";
	// Context context;
	List<View> mList;

	// private DisplayImageOptions options;

	public AdaMyViewPager(List<View> mList) {
		// this.context = context;
		this.mList = mList;
		// options = new DisplayImageOptions.Builder().cacheInMemory(true)
		// .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public int getCount() {
		if (mList.size() > 1) {
			return Integer.MAX_VALUE;
		}
		return mList.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		// container.addView(mList.get(position), 0);
		// return mList.get(position);
		try {
			container.addView(mList.get(position % mList.size()), 0);
		} catch (Exception e) {
		}

		return mList.get(position % mList.size());

	}

	public void destroyItem(View container, int position, Object object) {
		// ((ViewPager) container).removeView((View) object);
		try {
			((ViewPager) container).removeView(mList.get(position
					% mList.size()));
		} catch (Exception e) {
		}
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
