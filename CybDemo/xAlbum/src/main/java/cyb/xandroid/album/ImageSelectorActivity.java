package cyb.xandroid.album;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cyb.xandroid.album.adapter.ImageFolderAdapter;
import cyb.xandroid.album.adapter.ImageListAdapter;
import cyb.xandroid.album.model.LocalMedia;
import cyb.xandroid.album.model.LocalMediaFolder;
import cyb.xandroid.album.utils.FileUtils;
import cyb.xandroid.album.utils.GridSpacingItemDecoration;
import cyb.xandroid.album.utils.LocalMediaLoader;
import cyb.xandroid.album.utils.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dee on 15/11/19.
 */
public class ImageSelectorActivity extends AppCompatActivity {
    public final static int REQUEST_IMAGE = 66;
    public final static int REQUEST_CAMERA = 67;


    private static final String TAG = "xAlum";
    private static final int READ_EXTERNAL_STORAGE_CODE = 1;
    private static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    private static final String SELECTED_PARAM = "SelectParam";


    private Toolbar toolbar;
    private TextView doneText;
    private TextView previewText;

    private int spanCount = 3;
    private RecyclerView recyclerView;
    private ImageListAdapter imageAdapter;

    private LinearLayout folderLayout;
    private TextView folderName;
    private FolderWindow folderWindow;

    private String cameraPath;
    private SelectParam mSelectParam;

    public static void start(Activity activity, SelectParam selectParam) {
        Intent intent = new Intent(activity, ImageSelectorActivity.class);
        intent.putExtra(SELECTED_PARAM, selectParam);
        activity.startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageselector);
        mSelectParam = (SelectParam) getIntent().getSerializableExtra(SELECTED_PARAM);

        if (mSelectParam.selectMode == SelectParam.MODE_MULTIPLE) {
            mSelectParam.cropEnable = false;
        } else {
            mSelectParam.cropEnable = false;
        }
        if (savedInstanceState != null) {
            cameraPath = savedInstanceState.getString(SelectParam.BUNDLE_CAMERA_PATH);
        }
        initView();
        registerListener();
        requestPermission();
    }

    public void initView() {
        folderWindow = new FolderWindow(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.picture);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);

        doneText = (TextView) findViewById(R.id.done_text);
        doneText.setVisibility(mSelectParam.selectMode == SelectParam.MODE_MULTIPLE ? View.VISIBLE : View.GONE);

        previewText = (TextView) findViewById(R.id.preview_text);
        previewText.setVisibility(mSelectParam.previewEnable ? View.VISIBLE : View.GONE);

        folderLayout = (LinearLayout) findViewById(R.id.folder_layout);
        folderName = (TextView) findViewById(R.id.folder_name);

        recyclerView = (RecyclerView) findViewById(R.id.folder_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, ScreenUtils.dip2px(this, 2), false));
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));

        imageAdapter = new ImageListAdapter(this, mSelectParam);
        recyclerView.setAdapter(imageAdapter);

    }

    public void registerListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        folderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folderWindow.isShowing()) {
                    folderWindow.dismiss();
                } else {
                    folderWindow.showAsDropDown(toolbar);
                }
            }
        });

        imageAdapter.setOnImageSelectChangedListener(new ImageListAdapter.OnImageSelectChangedListener() {
            @Override
            public void onChange(List<LocalMedia> selectImages) {
                final int count = selectImages.size();
                boolean enable = count > 0;
                doneText.setEnabled(enable);
                previewText.setEnabled(enable);
                if (enable) {
                    doneText.setText(getString(R.string.done_num, count, mSelectParam.maxSelectNum));
                    previewText.setText(getString(R.string.preview_num, count));
                } else {
                    doneText.setText(R.string.done);
                    previewText.setText(R.string.preview);
                }
            }

            @Override
            public void onTakePhoto() {
                startCamera();
            }

            @Override
            public void onPictureClick(LocalMedia media, int position) {
                if (mSelectParam.previewEnable) {
                    startPreview(imageAdapter.getmImageList(), position);
                } else if (mSelectParam.cropEnable) {
                    startCrop(media.getPath());
                } else {
                    onSelectDone(media.getPath());
                }
            }
        });
        doneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectDone(imageAdapter.getSelectedImages());
            }
        });
        folderWindow.setOnItemClickListener(new ImageFolderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, List<LocalMedia> images) {
                folderWindow.dismiss();
                imageAdapter.bindImages(images);
                folderName.setText(name);
            }
        });
        previewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPreview(imageAdapter.getSelectedImages(), 0);
            }
        });
    }

    private void loadImages() {
        new LocalMediaLoader(this, LocalMediaLoader.TYPE_IMAGE).loadAllImage(new LocalMediaLoader.LocalMediaLoadListener() {

            @Override
            public void loadComplete(List<LocalMediaFolder> folders) {
                folderWindow.bindFolder(folders);
                imageAdapter.bindImages(folders.get(0).getImages());
            }
        });
    }

    private void requestPermission() {
        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限(如下图，在安装的时候，将一些权限关闭了)，ActivityCompat.checkSelfPermission()则可能会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。permissionGrant.onPermissionGranted(requestCode);
        try {
            if (Build.VERSION.SDK_INT < 23) {
                if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_CODE);
                }
                return;
            }
            loadImages();
        } catch (RuntimeException e) {
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_CODE) {
            if (grantResults != null && grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImages();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // on take photo success
            if (requestCode == REQUEST_CAMERA) {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(cameraPath))));
                if (mSelectParam.cropEnable) {
                    startCrop(cameraPath);
                } else {
                    onSelectDone(cameraPath);
                }
            }
            //on preview select change
            else if (requestCode == ImagePreviewActivity.REQUEST_PREVIEW) {
                boolean isDone = data.getBooleanExtra(ImagePreviewActivity.OUTPUT_ISDONE, false);
                List<LocalMedia> images = (List<LocalMedia>) data.getSerializableExtra(ImagePreviewActivity.OUTPUT_LIST);
                if (isDone) {
                    onSelectDone(images);
                } else {
                    imageAdapter.bindSelectImages(images);
                }
            }
            // on crop success
            else if (requestCode == ImageCropActivity.REQUEST_CROP) {
                String path = data.getStringExtra(ImageCropActivity.OUTPUT_PATH);
                onSelectDone(path);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SelectParam.BUNDLE_CAMERA_PATH, cameraPath);
    }

    /**
     * start to camera、preview、crop
     */
    public void startCamera() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
//            File cameraFile = FileUtils.createTmpFile(this);
//            cameraPath = cameraFile.getAbsolutePath();
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
//            startActivityForResult(cameraIntent, REQUEST_CAMERA);
//        }

//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    getString(R.string.mis_permission_rationale_write_storage),
//                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
//        } else {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//                try {
//                    mTmpFile = FileUtils.createTmpFile(getActivity());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (mTmpFile != null && mTmpFile.exists()) {
//                 /*获取当前系统的android版本号*/
//                    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//                    Log.e("currentapiVersion","currentapiVersion====>"+currentapiVersion);
//                    if (currentapiVersion<24){
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
//                        startActivityForResult(intent, REQUEST_CAMERA);
//                    }else {
//                        ContentValues contentValues = new ContentValues(1);
//                        contentValues.put(MediaStore.Images.Media.DATA, mTmpFile.getAbsolutePath());
//                        Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent, REQUEST_CAMERA);
//                    }
//                } else {
//                    Toast.makeText(this, R.string.mis_error_image_not_exist, Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(this, R.string.mis_msg_no_camera, Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    public void startPreview(List<LocalMedia> previewImages, int position) {
        ImagePreviewActivity.startPreview(this, previewImages, imageAdapter.getSelectedImages(), mSelectParam.maxSelectNum, position);
    }

    public void startCrop(String path) {
        ImageCropActivity.startCrop(this, path);
    }

    /**
     * on select done
     *
     * @param medias
     */
    public void onSelectDone(List<LocalMedia> medias) {
        ArrayList<String> images = new ArrayList<>();
        for (LocalMedia media : medias) {
            images.add(media.getPath());
        }
        onResult(images);
    }

    public void onSelectDone(String path) {
        ArrayList<String> images = new ArrayList<>();
        images.add(path);
        onResult(images);
    }

    public void onResult(ArrayList<String> images) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(SelectParam.REQUEST_OUTPUT, images);
        intent.putExtra(SelectParam.REQUEST_OUTPUT, mSelectParam);
        setResult(RESULT_OK, intent);
        finish();
    }
}
