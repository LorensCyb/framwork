package cyb.xandroid.demo.Data;

import java.util.ArrayList;
import java.util.List;

import cyb.xandroid.demo.R;

/**
 * Created by chenyangbo on 2017/6/26.
 */

public class ImageDatas {


    public static List<String> getImageUris() {
        List<String> list = new ArrayList<>();
        list.add("http://ww1.sinaimg.cn/large/7a8aed7bjw1f20ruz456sj20go0p0wi3.jpg");
        list.add("http://ww3.sinaimg.cn/large/7a8aed7bgw1etlw75so1hj20qo0hsgpk.jpg");
        list.add("http://ww2.sinaimg.cn/large/7a8aed7bjw1f25gtggxqjj20f00b9tb5.jpg");
        list.add("http://ww1.sinaimg.cn/large/7a8aed7bjw1f27uhoko12j20ez0miq4p.jpg");
        list.add("http://ww2.sinaimg.cn/large/610dc034jw1f27tuwswd3j20hs0qoq6q.jpg");
        return list;
    }

    public static List<Integer> getImageReses() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.a);
        list.add(R.mipmap.b);
        list.add(R.mipmap.c);
        list.add(R.mipmap.d);
        list.add(R.mipmap.e);
        list.add(R.mipmap.gank610dc034jw1f27tuwswd3j20hs0qoq6q);
        list.add(R.mipmap.gank7a8aed7bgw1etlw75so1hj20qo0hsgpk);
        list.add(R.mipmap.gank7a8aed7bjw1f25gtggxqjj20f00b9tb5);
        return list;
    }

    public static int[] getImageRes() {
        int[] mImgs = {R.mipmap.meinv1, R.mipmap.meinv2, R.mipmap.meinv3, R.mipmap.meinv4, R.mipmap.meinv5};
        return mImgs;
    }

}
