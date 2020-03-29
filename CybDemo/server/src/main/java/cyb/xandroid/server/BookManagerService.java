package cyb.xandroid.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import cyb.xandroid.aidl.Book;
import cyb.xandroid.aidl.IBookManager;
import cyb.xandroid.aidl.IOnNewBookArrivedListener;


/**
 * Created by chenyangbo on 2017/9/7.
 */

public class BookManagerService extends Service {

    private static final String TAG = "BMS";

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();


    private Binder mBinder = new IBookManager.Stub() {


        @Override
        public List<Book> getBookList() throws RemoteException {
            try {
                Thread.sleep(30*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);

//            if (!mListenerList.contains(listener)) {
//                mListenerList.add(listener);
//            } else {
//                Log.i(TAG, "already exists.");
//            }
//
//            Log.i(TAG, "registerListener,size:" + mListenerList.size());

        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);

//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener);
//            } else {
//                Log.i(TAG, "not found,can not unregister.");
//            }
//
//            Log.i(TAG, "unregisterListener, current size:" + mListenerList.size());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "IOS"));

        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
//        Log.i(TAG, "onNewBookArrived , notify listeners:" + mListenerList.size());
//
//        for (int i = 0; i < mListenerList.size(); i++) {
//            IOnNewBookArrivedListener listener = mListenerList.get(i);
//            Log.i(TAG, "onNewBookArrived , notify listeners:" + listener);
//            listener.onNewBookArriver(book);
//        }

        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener l = mListenerList.getBroadcastItem(i);
            if (l != null) {
                try {
                    l.onNewBookArriver(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            //
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);

                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
