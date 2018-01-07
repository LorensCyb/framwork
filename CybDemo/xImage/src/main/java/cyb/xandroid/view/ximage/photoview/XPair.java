package cyb.xandroid.view.ximage.photoview;

import android.support.v4.util.Pair;
import android.view.View;

/**
 * Created by chenyangbo on 2017/7/21.
 */

public class XPair {

    private Pair<View, String> pair;

    private Photo photo;

    public XPair(String uri, String url, View view, int position) {
        pair = Pair.create(view, String.valueOf(position));
        photo = new Photo(uri, url, position);
    }

    public XPair(int res, String url, View view, int position) {
        pair = Pair.create(view, String.valueOf(position));
        photo = new Photo(res, url, position);
    }

    public Pair<View, String> getPair() {
        return pair;
    }

    public void setPair(Pair<View, String> pair) {
        this.pair = pair;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
