package cyb.xandroid.demo.view.xlistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import cyb.xandroid.base.ArrayListAdapter;
import cyb.xandroid.demo.R;

public class ListViewAdpter extends ArrayListAdapter<String> {
    public ListViewAdpter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater(R.layout.item_xlist_view);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder{

    }
}
