package cyb.xandroid.demo.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cyb.xandroid.demo.IViewPager;

/**
 * Created by chenyangbo on 2017/6/22.
 */

public class BaseFragmentPagerAdapter<T extends IViewPager> extends FragmentPagerAdapter {

    protected FragmentManager fm;
    protected List<IViewPager> mViewPagerList;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public void setViewPagerList(List<IViewPager> viewPagerList) {
        this.mViewPagerList = viewPagerList;
    }


    @Override
    public Fragment getItem(int position) {
        return mViewPagerList == null ? null : mViewPagerList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mViewPagerList == null ? 0 : mViewPagerList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mViewPagerList == null ? "" : mViewPagerList.get(position).getTitle();
    }
}
