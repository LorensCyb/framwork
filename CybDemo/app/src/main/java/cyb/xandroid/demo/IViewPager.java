package cyb.xandroid.demo;

import android.support.v4.app.Fragment;

/**
 * Created by chenyangbo on 2017/6/22.
 */

public class IViewPager {

    private String title;
    private Fragment fragment;

    public IViewPager() {

    }

    public IViewPager(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
