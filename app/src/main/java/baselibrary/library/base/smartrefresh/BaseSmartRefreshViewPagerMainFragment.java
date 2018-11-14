package baselibrary.library.base.smartrefresh;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.africa.crm.businessmanagementproject.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import baselibrary.library.base.progress.BaseFragmentProgress;
import baselibrary.common.util.ListUtils;

/**
 * Project：czbz_project_new
 * Author:  guojin
 * Version: 1.0.0
 * Description：viewpager+fragment组合(主页没有搜索框)[主viewpager类继承]
 * Date：2018/1/18 13:51
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseSmartRefreshViewPagerMainFragment extends BaseFragmentProgress implements OnRefreshListener, OnRefreshLoadmoreListener, OnTabSelectListener {

    protected List<BaseSmartRefreshViewPagerChildFragment> mFragmentList = new ArrayList<>();
    protected List<String> mTitleList = new ArrayList<>();

    protected SlidingTabLayout mTabLayout;
    protected ViewPager mViewPager;
    protected SmartPagerAdapter mAdapter;

    protected RefreshLayout mRefreshLayout;

    @Override
    public void initData() {
        initSmartRefresh();
    }

    /**
     * 初始化SmartRefreshLayout
     */
    private void initSmartRefresh() {

        mRefreshLayout = (RefreshLayout) getRootView().findViewById(R.id.smart_main_refresh_layout);

        //下拉刷新监听
        mRefreshLayout.setOnRefreshListener(this);
        //加载更多监听
        mRefreshLayout.setOnRefreshLoadmoreListener(this);
        //自动加载更多
        mRefreshLayout.setEnableAutoLoadmore(true);
        //触发自动刷新
//        mRefreshLayout.autoRefresh();
        //添加头布局
//        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        //添加尾布局
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

        mViewPager = (ViewPager) getRootView().findViewById(R.id.viewPager);
        mTabLayout = (SlidingTabLayout) getTabView();
        mTabLayout.setOnTabSelectListener(this);

        mFragmentList = getFragmentList();
        mTitleList = getTitleList();

        if (!ListUtils.isEmpty(mFragmentList) && !ListUtils.isEmpty(mTitleList)) {
            mViewPager.setAdapter(mAdapter = new SmartPagerAdapter(mFragmentList, mTitleList));
            mTabLayout.setViewPager(mViewPager);
        }

        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mAdapter.getItem(mViewPager.getCurrentItem()).refreshData(mRefreshLayout);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.resetNoMoreData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mAdapter.getItem(mViewPager.getCurrentItem()).refreshData(mRefreshLayout);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mAdapter.getItem(mViewPager.getCurrentItem()).loadMore(mRefreshLayout);
    }

    /**
     * ViewPager与Fragment适配器
     */
    private class SmartPagerAdapter extends FragmentStatePagerAdapter {
        private List<String> mTitleList = new ArrayList<>();
        private List<BaseSmartRefreshViewPagerChildFragment> mFragmentList = new ArrayList<>();

        SmartPagerAdapter(List<BaseSmartRefreshViewPagerChildFragment> fragmentList, List<String> titleList) {
            super(getChildFragmentManager());
            this.mTitleList = titleList;
            this.mFragmentList = fragmentList;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

        @Override
        public BaseSmartRefreshViewPagerChildFragment getItem(int position) {
            return mFragmentList.get(position);
        }
    }

    /**
     * 获取 根View
     *
     * @return
     */
    protected abstract View getRootView();

    /**
     * 获取 Tab View
     *
     * @return
     */
    protected abstract View getTabView();

    /**
     * 获取Fragment集合
     */

    protected abstract List<BaseSmartRefreshViewPagerChildFragment> getFragmentList();


    /**
     * 获取标题集合
     */

    protected abstract List<String> getTitleList();


    @Override
    public void release() {

    }
}
