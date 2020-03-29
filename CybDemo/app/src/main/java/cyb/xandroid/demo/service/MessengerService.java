package cyb.xandroid.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class MessengerService extends Service {

    private static final String TAG = MessengerService.class.getSimpleName();

    private Messenger mMessenger = new Messenger(new ServiceHandler());

    /**
     *
     */
    class ServiceHandler extends Handler {

        public MessengerService getService() {
            return MessengerService.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "BindService onCreate()");
    }

    /**
     * 该方法只有调用startService()方法启动时才会被调用，
     * 调用bindService()回调时不会调用此方法。
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "BindService onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "BindService onBind()");
        return mMessenger.getBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "BindService onBind()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "BindService onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "BindService onDestroy()");
    }

    public void ToastOk() {
        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
    }

}
