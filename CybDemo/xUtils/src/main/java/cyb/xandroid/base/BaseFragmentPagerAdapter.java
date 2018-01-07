package cyb.xandroid.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by chenyangbo on 2017/6/22.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    protected FragmentManager fm;

    private List<Fragment> mFragementList;
    private List<String> mTitleList;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public List<String> getTitleList() {
        return mTitleList;
    }

    public void setTitleList(List<String> titles) {
        this.mTitleList = titles;
        notifyDataSetChanged();
    }

    public List<Fragment> getFragmentList() {
        return mFragementList;
    }

    public void setFragmentList(List<Fragment> fragments) {
        this.mFragementList = fragments;
        notifyDataSetChanged();
    }

    public void setPageList(List<String> titles, List<Fragment> fragments) {
        this.mTitleList = titles;
        this.mFragementList = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragementList == null ? null : mFragementList.get(position);
    }

    @Override
    public int getCount() {
        return mFragementList == null ? 0 : mFragementList.size();
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList == null ? "" : mTitleList.get(position);
    }

}
