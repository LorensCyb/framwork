package cyb.xandroid.demo.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import cyb.xandroid.demo.R;

public class ForegroundService extends Service {

    private static final String TAG = ForegroundService.class.getSimpleName();
    private static final String ID="channel_1";
    private static final String NAME="前台服务";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "ForegroundService onCreate()");

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
        Log.i(TAG, "ForegroundService onStartCommand()");
        if(Build.VERSION.SDK_INT >=26){
            setForeground();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "ForegroundService onBind()");
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "ForegroundService onBind()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "ForegroundService onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ForegroundService onDestroy()");
        setForeground();
    }

    @TargetApi(26)
    private void setForeground(){
        NotificationManager manager=(NotificationManager)getSystemService (NOTIFICATION_SERVICE);
        NotificationChannel channel=new NotificationChannel (ID,NAME,NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel (channel);
        Notification notification=new Notification.Builder (this,ID)
                .setContentTitle ("收到一条重要通知")
                .setContentText ("这是重要通知")
                .setSmallIcon (R.mipmap.ic_launcher)
                .setLargeIcon (BitmapFactory.decodeResource (getResources (),R.mipmap.ic_launcher))
                .build ();
        startForeground (1,notification);
    }

}
