package cyb.xandroid.demo.view.xlistview;

import android.os.Bundle;

import cyb.xandroid.demo.R;
import cyb.xandroid.demo.base.BaseBindingActivity;
import cyb.xandroid.demo.databinding.ActivityXlistViewBinding;

public class XListViewActivity extends BaseBindingActivity<ActivityXlistViewBinding> {


    ListViewAdpter listViewAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xlist_view);
        listViewAdpter = new ListViewAdpter(this);
        mViewDataBinding.listview.setAdapter(listViewAdpter);
    }

    private void initView() {

    }

}
