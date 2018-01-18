package cyb.xandroid.demo.acitity;

import android.os.Bundle;

import cyb.xandroid.demo.base.BaseActivityListFragment;

/**
 * Created by chenyangbo on 2017/6/26.
 */

public class ActivityFragment extends BaseActivityListFragment {

    public static BaseActivityListFragment newInstance() {
        BaseActivityListFragment fragment = new BaseActivityListFragment();
        Bundle args = new Bundle();
        args.putString(LABEL, "Activity");
        args.putBoolean(FORCE, false);
        fragment.setArguments(args);
        return fragment;
    }

}
