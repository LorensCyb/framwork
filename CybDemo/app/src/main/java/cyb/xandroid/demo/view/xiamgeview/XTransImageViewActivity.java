package cyb.xandroid.demo.view.xiamgeview;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import cyb.xandroid.demo.Data.ImageDatas;
import cyb.xandroid.demo.R;
import cyb.xandroid.view.ximage.transimageview.XBigImagePreview;

public class XTransImageViewActivity extends AppCompatActivity {

    private XBigImagePreview mPreview;
    private List<Integer> mUrlList;
    private List<Integer> mImgWidthList;
    private List<Integer> mImgHeightList;
    private int times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xtrans_image_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPreview = (XBigImagePreview) findViewById(R.id.bigImagePreview);
        mImgWidthList = new ArrayList<>();
        mImgHeightList = new ArrayList<>();

        final int[] img = ImageDatas.getImageRes();

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inSampleSize = 1;
        opts.inJustDecodeBounds = false;
        mUrlList = new ArrayList<>();
        for (int i = 0; i < img.length; i++) {
            BitmapFactory.decodeResource(getResources(), img[i], opts);
            int width = opts.outWidth;
            int height = opts.outHeight;
            mImgWidthList.add(width / 3);
            mImgHeightList.add(height / 3);
            mUrlList.add(img[i]);
        }
        mPreview.init(mUrlList, mImgWidthList, mImgHeightList,
                (int) dip2px(4), (int) dip2px(4), (int) dip2px(60));

        mPreview.setGalleryLoadMore(new XBigImagePreview.GalleryLoadMore() {
            @Override
            public void loadMore() {
                if (times > 1) {
                    mUrlList.clear();
                    mPreview.setGalleryLoadMoreData(mUrlList, mImgWidthList, mImgHeightList);
                } else {
                    mPreview.setGalleryLoadMoreData(mUrlList, mImgWidthList, mImgHeightList);
                }
                times++;
            }
        });

    }


    private float dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }


}
