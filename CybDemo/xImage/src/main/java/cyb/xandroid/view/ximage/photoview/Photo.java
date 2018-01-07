package cyb.xandroid.view.ximage.photoview;

import android.text.TextUtils;

import java.io.Serializable;


/**
 * Created by chenyangbo on 2017/6/23.
 */

public class Photo implements Serializable {

    private int position;

    private String uri;

    private String url;

    private String path;

    private String thumbnail;

    private int res;

    public Photo() {
    }

    public Photo(String thumbnail, String uri, int position) {
        this.thumbnail = thumbnail;
        this.uri = uri;
        this.position = position;
    }

    public Photo(int thumbnail, String uri, int position) {
        this.res = thumbnail;
        this.uri = uri;
        this.position = position;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        if (!TextUtils.isEmpty(uri)) {
            return uri;
        }
        //
        else if (!TextUtils.isEmpty(path)) {
            return path;
        }
        //
        else {
            return url;
        }
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
