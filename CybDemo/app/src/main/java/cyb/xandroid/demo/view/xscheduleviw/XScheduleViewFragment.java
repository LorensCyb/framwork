package cyb.xandroid.demo.view.xscheduleviw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

import cyb.xandroid.demo.MainActivity;
import cyb.xandroid.demo.base.BaseActivityListFragment;
import cyb.xandroid.demo.utitls.ActivityListUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link XScheduleViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link XScheduleViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XScheduleViewFragment extends BaseActivityListFragment {


    private List<Map<String, Object>> mTabLayoutActivityList = null;

    public XScheduleViewFragment() {
        // Required empty public constructor
    }


    public static XScheduleViewFragment newInstance() {
        XScheduleViewFragment fragment = new XScheduleViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mTabLayoutActivityList == null) {
            mTabLayoutActivityList = ActivityListUtil.getData(getActivity(), MainActivity.class, "TabLayout");
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
