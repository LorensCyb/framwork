package cyb.xandroid.server;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import cyb.xandroid.aidl.Book;
import cyb.xandroid.aidl.IBookManager;
import cyb.xandroid.aidl.IOnNewBookArrivedListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MA";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private Button mButton;
    private IBookManager mRemoteBookManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.i(TAG, "receive new book :" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager = bookManager;
                List<Book> list = bookManager.getBookList();

                Log.i(TAG, "query book list , list type: " + list.getClass().getCanonicalName());
                Log.i(TAG, "query book list: " + list.toString());

                Book newBook = new Book(3, "Android 进阶");
                bookManager.addBook(newBook);

                Log.i(TAG, "add book:" + newBook);
                List<Book> newList = bookManager.getBookList();
                Log.i(TAG, "query book list:" + newList.toString());

                bookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
            Log.e(TAG, "binder died.");
        }
    };


    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArriver(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.start_server);
        mButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                Log.i(TAG, "unregister listener:" + mOnNewBookArrivedListener);
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_server:
                ComponentName componentName = new ComponentName(
                        "cyb.xandroid.server", "cyb.xandroid.server.BookManagerService");
                //启动服务
                Intent intent = new Intent();
                intent.setComponent(componentName);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.get_book_size:
                try {

                    Log.i(TAG, "get book size:" + mRemoteBookManager.getBookList().size());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


}
