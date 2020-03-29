package cyb.xandroid.server;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.FileDescriptor;

import cyb.xandroid.aidl.IFdAidlInterface;
import cyb.xandroid.aidl.IFdCallback;

import static android.os.MessageQueue.OnFileDescriptorEventListener.EVENT_INPUT;

public class FdService extends Service {

    private ParcelFileDescriptor mPfd;

    private IBinder mBinder = new IFdAidlInterface.Stub() {
        @Override
        public void publish(ParcelFileDescriptor fd, IFdCallback callback) throws RemoteException {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @TargetApi(23)
    private void observe(ParcelFileDescriptor pfd, IFdCallback callback){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        mPfd = pfd;

        FileDescriptor fd = pfd.getFileDescriptor();
        MessageQueue queue = Looper.getMainLooper().getQueue();
        queue.addOnFileDescriptorEventListener(fd, EVENT_INPUT, new MessageQueue.OnFileDescriptorEventListener() {
            @Override
            public int onFileDescriptorEvents(@NonNull FileDescriptor fd, int events) {
                return 0;
            }
        });

    }


}
