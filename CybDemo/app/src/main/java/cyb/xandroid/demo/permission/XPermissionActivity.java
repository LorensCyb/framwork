package cyb.xandroid.demo.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import cyb.xandroid.demo.R;
import cyb.xandroid.demo.base.BaseAppCompatActivity;

public class XPermissionActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int ACCESS_FINE_LOCATION_CODE = 100;
    private static final String[] mLocationPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xpermission);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        requestPermission();
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        // 判断是否申请了
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            //检测是否会弹出权限框
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //如果不弹，提示用户，或直接跳转到设置页面
                return;
            }

            // 申请权限
            ActivityCompat.requestPermissions(this, new String[]{
                    ACCESS_FINE_LOCATION
            }, ACCESS_FINE_LOCATION_CODE);
        }

        //执行相关任务....
    }

    /**
     * 权限申请返回结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_FINE_LOCATION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[i])
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //执行相关任务
                }
                //判断其他权限申请
            }
        }
    }
}
