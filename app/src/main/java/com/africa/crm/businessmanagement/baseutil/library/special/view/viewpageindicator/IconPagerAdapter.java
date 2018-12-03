package com.africa.crm.businessmanagement.baseutil.library.special.view.viewpageindicator;

public interface IconPagerAdapter {
	/**
	 * Get icon representing the page at {@code index} in the adapter.
	 */
	int getIconResId(int index);

	// From PagerAdapter
	int getCount();
}
