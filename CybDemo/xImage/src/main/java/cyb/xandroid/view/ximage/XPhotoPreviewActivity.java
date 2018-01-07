package cyb.xandroid.view.ximage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.WindowInsetsCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.request.RequestFutureTarget;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

import cyb.xandroid.view.ximage.R;
import cyb.xandroid.view.ximage.photoview.Photo;
import cyb.xandroid.view.ximage.photoview.PhotoViewAttacher;
import cyb.xandroid.view.ximage.photoview.XPair;

/**
 * Viewpager
 * Created by Porster on 17/6/9.
 */

public class XPhotoPreviewActivity extends Activity {


    private final static String NOW_POSITION = "NOW_POSITION";
    private final static String PHOTOSES = "PHOTOSES";


    private int mNowPosition;
    private List<Photo> mPhotoses;

    private List<ImageView> mViewList = new ArrayList<>();
    private ViewPager mViewpager;
    private DragFrameLayout mDragFrameLayout;


    public static void start(Activity activity, int position, XPair... XPairs) {
        Pair<View, String> pairs[] = new Pair[XPairs.length];
        List<Photo> photos = new ArrayList<>();
        int i = 0;
        for (XPair XPair : XPairs) {
            pairs[i++] = XPair.getPair();
            photos.add(XPair.getPhoto());
        }

        Intent intent = new Intent(activity, XPhotoPreviewActivity.class);
        intent.putExtra(NOW_POSITION, position);
        intent.putExtra(PHOTOSES, (Serializable) photos);

        ActivityOptionsCompat compat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, pairs);
        ActivityCompat.startActivity(activity, intent, compat.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);


        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mDragFrameLayout = (DragFrameLayout) findViewById(R.id.drag);

        setPhotoses();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (ImageView imageView : mViewList) {
                    PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
            }
        }, 500);
    }

    @Override
    public void onBackPressed() {
        int position = mViewpager.getCurrentItem();
        ViewCompat.setTransitionName(mViewList.get(position), String.valueOf(position));
        ActivityCompat.finishAfterTransition(this);
    }


    private void setPhotoses() {
        mNowPosition = getIntent().getIntExtra(NOW_POSITION, 0);
        mPhotoses = (List<Photo>) getIntent().getSerializableExtra(PHOTOSES);
        if (mPhotoses != null && mPhotoses.size() > 0) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            for (Photo photo : mPhotoses) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setAdjustViewBounds(true);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                mViewList.add(imageView);
                mViewpager.addView(imageView);
                if (!TextUtils.isEmpty(photo.getUri())) {
                    Uri uri = Uri.parse(photo.getUri());
                    Picasso.with(XPhotoPreviewActivity.this)
                            .load(uri)
                            .into(imageView);
                } else {
                    Picasso.with(XPhotoPreviewActivity.this)
                            .load(photo.getRes())
                            .into(imageView);
                }
            }
            mViewpager.setAdapter(mPagerAdapter);
            mViewpager.setCurrentItem(mNowPosition, false);
        }
        ViewCompat.setTransitionName(mViewpager, String.valueOf(mNowPosition));
    }


    private PagerAdapter mPagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (mViewList.get(position).getParent() == null) {
                container.addView(mViewList.get(position));
            }
            return mViewList.get(position);
        }
    };
}
