package cyb.xandroid.demo.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cyb.xandroid.base.ViewPagerAdapter;

/**
 * Created by chenyangbo on 2017/6/26.
 */

public class ImagesViewPager extends ViewPagerAdapter<Integer> {


    public ImagesViewPager(Context context) {
        super(context);
    }

    @Override
    public View getView(ViewGroup parent, View convertView, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(getItem(position));
        return imageView;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
