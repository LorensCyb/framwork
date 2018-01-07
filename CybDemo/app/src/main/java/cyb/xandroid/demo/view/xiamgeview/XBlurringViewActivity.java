package cyb.xandroid.demo.view.xiamgeview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cyb.xandroid.demo.R;
import cyb.xandroid.view.ximage.XBlurringView;

public class XBlurringViewActivity extends AppCompatActivity {

    private ImageView imageView;
    private XBlurringView xBlurringView;

    private RelativeLayout layout;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            imageView.setImageResource(R.mipmap.gank7a8aed7bgw1etlw75so1hj20qo0hsgpk);
            xBlurringView.invalidate();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xblurring_view);
        layout = (RelativeLayout) findViewById(R.id.layout);
        imageView = (ImageView) findViewById(R.id.imageview);
        xBlurringView = (XBlurringView) findViewById(R.id.xblurringView);
        xBlurringView.setBlurredView(imageView);
        xBlurringView.invalidate();
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
