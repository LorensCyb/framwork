package cyb.xandroid.demo.view.xiamgeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import cyb.xandroid.demo.R;
import cyb.xandroid.view.ximage.XClipSquareImageView;

public class XClipSquareImageViewActivity extends AppCompatActivity {


    private XClipSquareImageView xClipSquareImageView;
    private RecyclerView rccyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xclip_square_image_view);

        xClipSquareImageView = (XClipSquareImageView) findViewById(R.id.xClipSquareImageView);
        xClipSquareImageView.setImageResource(R.mipmap.gank7a8aed7bgw1etlw75so1hj20qo0hsgpk);

    }
}
