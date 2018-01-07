package cyb.xandroid.demo.view.xiamgeview;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cyb.xandroid.demo.R;
import cyb.xandroid.view.ximage.photoview.XPair;

/**
 * Created by chenyangbo on 2017/7/21.
 */

public class XPhotoPreviewActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        setContentView(R.layout.activity_xphoto_preview);

        Uri uri = Uri.parse("http://img2.imgtn.bdimg.com/it/u=49292017,22064401&fm=26&gp=0.jpg");
        Picasso.with(this)
                .load(uri)
                .into((ImageView) findViewById(R.id.imageview2));
    }


    @Override
    public void onClick(View v) {
        int position = 0;

        switch (v.getId()) {
            case R.id.imageview1:
                position = 0;
                break;
            case R.id.imageview2:
                position = 1;
                break;
        }

        cyb.xandroid.view.ximage.XPhotoPreviewActivity.start(this, position,
                new XPair(R.mipmap.meinv1, "http://img2.imgtn.bdimg.com/it/u=49292017,22064401&fm=26&gp=0.jpg", findViewById(R.id.imageview1), 0),
                new XPair(R.mipmap.meinv2, "http://img2.imgtn.bdimg.com/it/u=49292017,22064401&fm=26&gp=0.jpg", findViewById(R.id.imageview2), 1),
                new XPair(R.mipmap.meinv3, "http://img2.imgtn.bdimg.com/it/u=49292017,22064401&fm=26&gp=0.jpg", findViewById(R.id.imageview3), 2),
                new XPair(R.mipmap.meinv4, "http://img2.imgtn.bdimg.com/it/u=49292017,22064401&fm=26&gp=0.jpg", findViewById(R.id.imageview4), 3)
        );
    }
}
