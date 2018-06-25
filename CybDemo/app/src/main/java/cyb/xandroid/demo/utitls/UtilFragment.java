package cyb.xandroid.demo.utitls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cyb.xandroid.demo.base.BaseActivityListFragment;

/**
 * Created by chenyangbo on 2017/6/26.
 */

public class UtilFragment extends BaseActivityListFragment {
    public static BaseActivityListFragment newInstance() {
        BaseActivityListFragment fragment = new BaseActivityListFragment();
        Bundle args = new Bundle();
        args.putString(LABEL, "xUtil");
        args.putBoolean(FORCE, false);
        fragment.setArguments(args);
        return fragment;
    }
}
