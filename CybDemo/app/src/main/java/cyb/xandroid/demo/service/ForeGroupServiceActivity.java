package cyb.xandroid.demo.service;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cyb.xandroid.demo.R;
import cyb.xandroid.demo.base.BaseBindingActivity;
import cyb.xandroid.demo.databinding.ActivityForegroundServiceBinding;

public class ForeGroupServiceActivity extends BaseBindingActivity<ActivityForegroundServiceBinding>
        implements View.OnClickListener {

    private static final String TAG = ForeGroupServiceActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_service);
        setSupportActionBar(mViewDataBinding.toolbar);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 启动服务
     *
     * @param v
     */
    public void onStartForegroundService(View v) {
        Intent intent = new Intent(this, ForegroundService.class);
        if(Build.VERSION.SDK_INT>=26){
            startForegroundService(intent);
        }else {
            startService(intent);
        }
    }
}
