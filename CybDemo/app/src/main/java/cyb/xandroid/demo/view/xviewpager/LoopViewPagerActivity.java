package cyb.xandroid.demo.view.xviewpager;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import cyb.xandroid.demo.Base.BaseAppCompatActivity;
import cyb.xandroid.demo.Data.ImageDatas;
import cyb.xandroid.demo.Data.ImagesViewPager;
import cyb.xandroid.demo.R;
import cyb.xandroid.view.xviewpager.AutoLoopViewPager;
import cyb.xandroid.view.xviewpager.indicator.AnimatorCircleIndicator;
import cyb.xandroid.view.xviewpager.indicator.LinePageIndicator;
import cyb.xandroid.view.xviewpager.indicator.SimpleCircleIndicator;
import cyb.xandroid.view.xviewpager.transformer.ZoomOutPageTransformer;

public class LoopViewPagerActivity extends BaseAppCompatActivity {


    private AutoLoopViewPager mViewPager;
    private LinePageIndicator mLinePageIndicator;
    private SimpleCircleIndicator mSimpleCircleIndicator;
    private AnimatorCircleIndicator mAnimatorCircleIndicator;


    private ImagesViewPager mImagesViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //以下代码用于去除阴影
        if (Build.VERSION.SDK_INT >= 21) {
            getSupportActionBar().setElevation(0);
        }

        initView();
    }


    private void initView() {
        mViewPager = (AutoLoopViewPager) findViewById(R.id.viewPager);
        mLinePageIndicator = (LinePageIndicator) findViewById(R.id.linePageIndicator);
        mSimpleCircleIndicator = (SimpleCircleIndicator) findViewById(R.id.indicator);
        mAnimatorCircleIndicator = (AnimatorCircleIndicator) findViewById(R.id.animatorCircleIndicator);

        mImagesViewPager = new ImagesViewPager(this);
        mImagesViewPager.setList(ImageDatas.getImageReses());

        mViewPager.setAdapter(mImagesViewPager);
        mViewPager.setCurrentItem(2);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //viewPager.setAutoScrollDurationFactor(5.0);
        mViewPager.setInterval(1000);
        mViewPager.startAutoScroll();
        mViewPager.setBoundaryCaching(true);

        mLinePageIndicator.setViewPager(mViewPager);
        mSimpleCircleIndicator.setViewPager(mViewPager);
        mAnimatorCircleIndicator.setViewPager(mViewPager);
    }

}
