package cyb.xandroid.demo.view.xalbum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import cyb.xandroid.album.ImageSelectorActivity;

import java.util.ArrayList;

import cyb.xandroid.album.SelectParam;
import cyb.xandroid.demo.R;

public class XAlbumActivity extends AppCompatActivity {

    private ImageButton minus;
    private ImageButton plus;
    private EditText selectNumText;

    private RadioGroup selectMode;
    private RadioGroup showCamera;
    private RadioGroup enablePreview;
    private RadioGroup enableCrop;

    private Button selectPicture;
    private int maxSelectNum = 9;
    private SelectParam selectParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xalbum);

        initView();
        registerListener();

    }

    public void initView() {
        minus = (ImageButton) findViewById(R.id.minus);
        plus = (ImageButton) findViewById(R.id.plus);
        selectNumText = (EditText) findViewById(R.id.select_num);

        selectMode = (RadioGroup) findViewById(R.id.select_mode);
        showCamera = (RadioGroup) findViewById(R.id.show_camera);
        enablePreview = (RadioGroup) findViewById(R.id.enable_preview);
        enableCrop = (RadioGroup) findViewById(R.id.enable_crop);

        selectPicture = (Button) findViewById(R.id.select_picture);
    }

    public void registerListener() {
        selectMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.mode_single) {
                    enableCrop.check(R.id.crop_enable);
                    findViewById(R.id.crop_enable).setEnabled(true);

                    enablePreview.check(R.id.preview_disable);
                    findViewById(R.id.preview_enable).setEnabled(false);
                } else {
                    enableCrop.check(R.id.crop_disable);
                    findViewById(R.id.crop_enable).setEnabled(false);

                    enablePreview.check(R.id.preview_enable);
                    findViewById(R.id.preview_enable).setEnabled(true);
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxSelectNum--;
                selectNumText.setText(maxSelectNum + "");
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxSelectNum++;
                selectNumText.setText(maxSelectNum + "");
            }
        });
        selectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = selectMode.getCheckedRadioButtonId() == R.id.mode_multiple ? SelectParam.MODE_MULTIPLE : SelectParam.MODE_SINGLE;
                boolean isShow = showCamera.getCheckedRadioButtonId() == R.id.camera_yes ? true : false;
                boolean isPreview = enablePreview.getCheckedRadioButtonId() == R.id.preview_enable ? true : false;
                boolean isCrop = enableCrop.getCheckedRadioButtonId() == R.id.crop_enable ? true : false;

                if (selectParam == null) {
                    selectParam = new SelectParam(maxSelectNum, mode, isShow, isPreview, isCrop, null);
                } else {
                    selectParam.maxSelectNum = maxSelectNum;
                    selectParam.takePhotoEnable = isShow;
                    selectParam.previewEnable = isPreview;
                    selectParam.cropEnable = isCrop;
                }
                /**
                 * maxSelectNum --> 可选择图片的数量
                 * mode         --> 单选 or 多选
                 * isShow       --> 是否显示拍照选项
                 * isPreview    --> 是否打开预览选项
                 * isCrop       --> 是否打开剪切选项
                 */
                ImageSelectorActivity.start(XAlbumActivity.this, selectParam);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = data.getStringArrayListExtra(SelectParam.REQUEST_OUTPUT);
            selectParam = (SelectParam) data.getSerializableExtra(SelectParam.REQUEST_OUTPUT);
            //startActivity(new Intent(this, SelectResultActivity.class).putExtra(SelectResultActivity.EXTRA_IMAGES, images));
        }
    }

}
