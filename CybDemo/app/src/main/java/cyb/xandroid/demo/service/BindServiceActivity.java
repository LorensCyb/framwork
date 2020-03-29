package cyb.xandroid.demo.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import cyb.xandroid.demo.R;
import cyb.xandroid.demo.base.BaseBindingActivity;
import cyb.xandroid.demo.databinding.ActivityBindServiceBinding;

public class BindServiceActivity extends BaseBindingActivity<ActivityBindServiceBinding>
        implements View.OnClickListener {

    private static final String TAG = BindServiceActivity.class.getSimpleName();

    Messenger mMessenger= null;

    /**
     *
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "ServiceConnection  onServiceConnected() ");
            mMessenger = new Messenger(service);
            if(service != null){
//                MessengerService.ServiceBind bind = (MessengerService.ServiceBind)service;
//                bind.getService().ToastOk();
//                Log.i(TAG, "ServiceConnection  onServiceConnected() service : " + service);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "ServiceConnection  onServiceDisconnected() ");
        }

        @Override
        public void onBindingDied(ComponentName name) {

        }

        @Override
        public void onNullBinding(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_service);
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
    public void onStartBindService(View v) {
        Intent intent = new Intent(this, MessengerService.class);
//        startService(intent);
        /**
         * @param flags
         */
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

}
