package cyb.xandroid.demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cyb.xandroid.demo.R;
import cyb.xandroid.util.StatusBarUtil;

/**
 * Created by chenyangbo on 2017/6/26.
 */

public class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorAccent));
    }
}
