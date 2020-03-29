package cyb.xandroid.demo.handle;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import cyb.xandroid.demo.R;

public class OtherHandleActivity extends AppCompatActivity {


    private static String TAG = OtherHandleActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_handle);
    }

    public void onTest1Click(View view){
        test1();
    }


    private void test1(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler(Looper.myLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG,"test1:  postDelayed 10s.");
                    }
                },10*1000);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG,"test1:  postDelayed 5s.");
                    }
                },5*1000);

                try {
                    Thread.sleep(15*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        });
        thread.start();
        Log.i(TAG,"test1:  postDelayed 5s.");
    }
}
