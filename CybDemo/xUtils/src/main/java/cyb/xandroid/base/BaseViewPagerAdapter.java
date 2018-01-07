package cyb.xandroid.base;

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

public class BaseViewPagerAdapter<T extends View> extends PagerAdapter {

    protected List<T> mViewList;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public BaseViewPagerAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    protected View layoutInflater(int layout) {
        return mInflater.inflate(layout, null);
    }


    @Override
    public int getCount() {
        if (mViewList != null)
            return mViewList.size();
        else
            return 0;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public T getItemView(int position) {
        return mViewList == null ? null : mViewList.get(position);
    }

    protected String getString(int resId) {
        return mContext.getString(resId);
    }

    public void setViewList(T[] list) {
        ArrayList<T> arrayList = new ArrayList<T>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setViewList(arrayList);
    }

    public void setViewList(List<T> list) {
        this.mViewList = list;
        notifyDataSetChanged();
    }

    public List<T> getViewList() {
        return mViewList;
    }

    public void addViewList(ArrayList<T> list) {
        if (mViewList == null) {
            mViewList = new ArrayList<T>();
        }

        for (int i = 0; i < list.size(); i++) {
            this.mViewList.add(list.get(i));
        }
        this.notifyDataSetChanged();
    }

    public void addViewList(T[] t) {
        if (mViewList == null) {
            mViewList = new ArrayList<T>();
        }
        for (int i = 0; i < t.length; i++) {
            this.mViewList.add(t[i]);
        }
        this.notifyDataSetChanged();
    }

    public void addView(T t) {
        if (mViewList == null) {
            mViewList = new ArrayList<T>();
        }
        this.mViewList.add(t);
        this.notifyDataSetChanged();
    }
}
