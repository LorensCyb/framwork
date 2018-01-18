package cyb.xandroid.demo.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

import cyb.xandroid.demo.MainActivity;
import cyb.xandroid.demo.utitls.ActivityListUtil;

/**
 * Created by chenyangbo on 2017/6/26.
 */

public class BaseActivityListFragment extends ListFragment {

    protected static final String LABEL = "LABEL";
    protected static final String FORCE = "FORCE";

    private String mLabel = "";
    private boolean mForce = false;
    private List<Map<String, Object>> mTabLayoutActivityList = null;

    public BaseActivityListFragment() {
        // Required empty public constructor
    }


    public static BaseActivityListFragment newInstance(String label, boolean force) {
        BaseActivityListFragment fragment = new BaseActivityListFragment();
        Bundle args = new Bundle();
        args.putString(LABEL, label);
        args.putBoolean(FORCE, force);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mLabel = bundle.getString(LABEL, "");
            mForce = bundle.getBoolean(FORCE, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initActivityList(mLabel, mForce);
    }

    protected void initActivityList(String label, boolean force) {
        if (label == null) {
            label = "";
        }

        if (mTabLayoutActivityList == null || force) {
            mTabLayoutActivityList = ActivityListUtil.getData(getActivity(), MainActivity.class, label);
            setListAdapter(new SimpleAdapter(getActivity(),
                    mTabLayoutActivityList,
                    android.R.layout.simple_list_item_1,
                    new String[]{"title"},
                    new int[]{android.R.id.text1}));
            getListView().setTextFilterEnabled(true);
        }
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        super.onListItemClick(listView, v, position, id);
        Map<String, Object> map = (Map<String, Object>) listView.getItemAtPosition(position);
        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }


}