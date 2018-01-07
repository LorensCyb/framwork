package cyb.xandroid.demo.acitity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import cyb.xandroid.demo.Base.BaseAppCompatActivity;
import cyb.xandroid.demo.R;

public class IntenFilterActivity extends BaseAppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inten_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //以下代码用于去除阴影
        if (Build.VERSION.SDK_INT >= 21) {
            getSupportActionBar().setElevation(0);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.action:
                //必须至少匹配一个action
                intent.setAction("cyb.xandroid.demo.Action_TestIntenFilter");
                //不含有DEFAULT这个category的Activity是无法接收隐式Intent的。
                intent.addCategory("android.intent.category.DEFAULT");
                break;
            case R.id.category:
                //必须至少匹配一个action
                intent.setAction("android.intent.action.VIEW");
                //
                intent.addCategory("cyb.xandroid.demo.Category_TestIntenFilter");
                //不含有DEFAULT这个category的Activity是无法接收隐式Intent的。
                intent.addCategory("android.intent.category.DEFAULT");
                break;
            case R.id.category_no:
                //必须至少匹配一个action
                intent.setAction("android.intent.action.VIEW");
                //所有的category都必须匹配上
                //
                intent.addCategory("cyb.xandroid.demo.Category_TestIntenFilter");
                //不存在这个category
                intent.addCategory("cyb.xandroid.demo.Category_TestIntenFilter3");
                //不含有DEFAULT这个category的Activity是无法接收隐式Intent的。
                intent.addCategory("android.intent.category.DEFAULT");
                break;
            case R.id.data_1:
                //必须至少匹配一个action
                intent.setAction("android.intent.action.VIEW");
                intent.setType("test/IntenFilter");
                break;
            case R.id.data_2:
                //必须至少匹配一个action
                intent.setAction("cyb.xandroid.demo.Action_TestIntenFilter");
                intent.setData(Uri.parse("http://www.baidu.com"));
                break;
            case R.id.data_3:
                //必须至少匹配一个action
                intent.setAction("cyb.xandroid.demo.Action_TestIntenFilter");
                intent.setDataAndType(Uri.parse("http://www.sina.com.cn"),"test/IntenFilter");
                break;
            case R.id.data_4:
                //必须至少匹配一个action
                intent.setAction("cyb.xandroid.demo.Action_TestIntenFilter");
                intent.setType("test/IntenFilter2");
                break;
            case R.id.data_5:
                //必须至少匹配一个action
                intent.setAction("cyb.xandroid.demo.Action_TestIntenFilter");
                intent.setData(Uri.parse("http://www.sina.com.cn"));
                break;
        }

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
