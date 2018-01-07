package cyb.xandroid.view.xviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyangbo on 2017/6/26.
 */

public abstract class ViewPagerAdapter<T> extends PagerAdapter {

    protected List<T> mList;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public ViewPagerAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    protected View layoutInflater(int layout) {
        return mInflater.inflate(layout, null);
    }


    /***
     *
     **/
    abstract public View getView(ViewGroup parent, View convertView, int position);

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(container, null, position);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    public T getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    protected String getString(int resId) {
        return mContext.getString(resId);
    }

    public void setList(T[] list) {
        ArrayList<T> arrayList = new ArrayList<T>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList);
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return mList;
    }

    public void addList(ArrayList<T> list) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }

        for (int i = 0; i < list.size(); i++) {
            this.mList.add(list.get(i));
        }
        this.notifyDataSetChanged();
    }

    public void addList(T[] t) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        for (int i = 0; i < t.length; i++) {
            this.mList.add(t[i]);
        }
        this.notifyDataSetChanged();
    }

    public void add(T t) {
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        this.mList.add(t);
        this.notifyDataSetChanged();
    }
}
