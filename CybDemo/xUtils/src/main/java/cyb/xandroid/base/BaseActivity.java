package cyb.xandroid.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cyb.xandroid.bind.XViewUtil;

/**
 * Created by asus on 2018/3/22.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        XViewUtil.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        XViewUtil.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        XViewUtil.bind(this);
    }


}
