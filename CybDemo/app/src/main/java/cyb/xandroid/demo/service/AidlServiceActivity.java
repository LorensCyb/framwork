package cyb.xandroid.demo.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import cyb.xandroid.aidl.Book;
import cyb.xandroid.aidl.IBookManager;
import cyb.xandroid.demo.R;

public class AidlServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ASA";

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mButton = (Button) findViewById(R.id.start_server);
        mButton.setOnClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mButton.setText("OK");
            }
        }).start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mButton.setVisibility(View.GONE);
                mButton.setText("StartService");
            }
        }).start();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(AidlServiceActivity.this,"Service Connectioned",Toast.LENGTH_SHORT).show();
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list = bookManager.getBookList();

                Log.i(TAG, "query book list , list type: " + list.getClass().getCanonicalName());

                Log.i(TAG, "query book list: " + list.toString());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


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
        }
    }

    public void test(){

    }
}
