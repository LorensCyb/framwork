package cyb.xandroid.demo.view.xrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cyb.xandroid.view.XRecyclerView;

/**
 * Created by asus on 2018/1/31.
 */

public class XRecyclerAdapter extends XRecyclerView.XAdapter<String, RecyclerView.ViewHolder> {


    public XRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new TextView(getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TextView) holder.itemView).setText("" + position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
