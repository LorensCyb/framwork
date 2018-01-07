package cyb.xandroid.base;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class ArrayListAdapter<T> extends BaseAdapter {

	protected List<T> mList;
	protected Context mContext;
	protected ListView mListView;
	protected LayoutInflater mInflater;

	public ArrayListAdapter(Context context) {
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	protected View  layoutInflater(int layout){
		return  mInflater.inflate(layout,null);
	}


	@Override
	public int getCount() {
		if (mList != null)
			return mList.size();
		else
			return 0;
	}

	@Override
	public T getItem(int position) {
		return mList == null ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	abstract public View getView(int position, View convertView, ViewGroup parent);

	protected String getString(int resId){
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

	public ListView getListView() {
		return mListView;
	}

	public void setListView(ListView listView) {
		mListView = listView;
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
