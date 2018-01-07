package cyb.xandroid.view.ximage;

import android.support.v4.util.Pair;
import android.view.View;

/**
 * Created by chenyangbo on 2017/6/23.
 */

public class CompatBundle {

    private int position;

    private Pair<View, String> pair;

    private String url;

    private String uri;

    public CompatBundle(String uri, String url, View view, int position) {
        this.uri = uri;
        this.url = url;
        pair = Pair.create(view, String.valueOf(position));
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Pair<View, String> getPair() {
        return pair;
    }

    public void setPair(Pair<View, String> pair) {
        this.pair = pair;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
