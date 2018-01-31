package cyb.xandroid.demo;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cyb.xandroid.demo.base.BaseAppCompatActivity;
import cyb.xandroid.demo.base.BaseFragmentPagerAdapter;
import cyb.xandroid.demo.acitity.ActivityFragment;
import cyb.xandroid.demo.service.ServiceFragment;
import cyb.xandroid.demo.statusbar.StatusBarFragment;
import cyb.xandroid.demo.view.xiamgeview.XImageViewFragment;
import cyb.xandroid.demo.view.xrecyclerview.XRecyclerViewFragment;
import cyb.xandroid.demo.view.xtablayout.TabLayoutFragment;
import cyb.xandroid.demo.view.xviewpager.XViewPagerFragment;

public class MainActivity extends BaseAppCompatActivity {


    private List<IViewPager> mViewPagerList;

    private TabLayout mTabLyout;
    private ViewPager mViewPager;
    private BaseFragmentPagerAdapter mFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //以下代码用于去除阴影
        if (Build.VERSION.SDK_INT >= 21) {
            getSupportActionBar().setElevation(0);
        }
        setContentView(R.layout.activity_main);
        initFragment();
        initView();
    }


    private void initView() {
        mFragmentPagerAdapter = new TabLayoutPagerAdapter(getSupportFragmentManager());
        mFragmentPagerAdapter.setViewPagerList(mViewPagerList);


        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mFragmentPagerAdapter);

        mTabLyout = (TabLayout) findViewById(R.id.tablayout);
        mTabLyout.setupWithViewPager(mViewPager);
        mTabLyout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


    private void initFragment() {
        mViewPagerList = new ArrayList<>();
        mViewPagerList.add(new IViewPager("Activity", ActivityFragment.newInstance()));
        mViewPagerList.add(new IViewPager("Service", ServiceFragment.newInstance("xService", false)));
        mViewPagerList.add(new IViewPager("StatusBar", StatusBarFragment.newInstance()));
        mViewPagerList.add(new IViewPager("TabLayout", TabLayoutFragment.newInstance()));
        mViewPagerList.add(new IViewPager("xRecyclerView", XRecyclerViewFragment.newInstance("xRecyclerView", false)));
        mViewPagerList.add(new IViewPager("xImageView", XImageViewFragment.newInstance("xImageView", false)));
        mViewPagerList.add(new IViewPager("xAlbum", XImageViewFragment.newInstance("xAlbum", false)));
        mViewPagerList.add(new IViewPager("xViewPager", XViewPagerFragment.newInstance("xViewPager", false)));
        mViewPagerList.add(new IViewPager("xSwipeItemLayout", XViewPagerFragment.newInstance("xSwipeItemLayout", false)));
        mViewPagerList.add(new IViewPager("TabLayout", TabLayoutFragment.newInstance()));
        mViewPagerList.add(new IViewPager("TabLayout", TabLayoutFragment.newInstance()));
    }

}
