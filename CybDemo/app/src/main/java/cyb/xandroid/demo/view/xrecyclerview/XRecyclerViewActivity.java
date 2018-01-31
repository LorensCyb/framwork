package cyb.xandroid.demo.view.xrecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import cyb.xandroid.demo.R;
import cyb.xandroid.view.xrecyclerview.ProgressStyle;
import cyb.xandroid.view.XRecyclerView;

public class XRecyclerViewActivity extends AppCompatActivity {

    private XRecyclerView xRecyclerView;
    private XRecyclerAdapter xRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrcyclere_view);
        initView();
    }

    private void initView() {
        xRecyclerAdapter = new XRecyclerAdapter(this);
        xRecyclerView = (XRecyclerView) findViewById(R.id.xrecyclerview);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setAdapter(xRecyclerAdapter);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.LineScalePulseOut);
        xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.OnlyAVLoading);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRecyclerView.refreshComplete();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRecyclerView.loadMoreComplete();
                    }
                }, 3000);
            }
        });
    }

}
