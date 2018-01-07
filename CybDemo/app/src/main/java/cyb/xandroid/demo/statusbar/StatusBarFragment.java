package cyb.xandroid.demo.statusbar;

import android.os.Bundle;

import cyb.xandroid.demo.Base.BaseActivityListFragment;

/**
 * Created by chenyangbo on 2017/6/26.
 */

public class StatusBarFragment extends BaseActivityListFragment {

    public static BaseActivityListFragment newInstance() {
        BaseActivityListFragment fragment = new BaseActivityListFragment();
        Bundle args = new Bundle();
        args.putString(LABEL, "StatusBar");
        args.putBoolean(FORCE, false);
        fragment.setArguments(args);
        return fragment;
    }

}
