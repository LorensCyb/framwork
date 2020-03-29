package cyb.xandroid.demo.view.xiamgeview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

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
        testPicasso();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void testPicasso(){
        Picasso.get().load("https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=9fefa3148ccb39dbd5cd6f04b17f6241/9922720e0cf3d7ca39e0f888ff1fbe096b63a93e.jpg").into(imageView);
    }

}
