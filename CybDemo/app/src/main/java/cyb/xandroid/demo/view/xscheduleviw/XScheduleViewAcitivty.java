package cyb.xandroid.demo.view.xscheduleviw;

import android.os.Bundle;

import cyb.xandroid.base.BaseActivity;
import cyb.xandroid.bind.BindView;
import cyb.xandroid.demo.R;
import cyb.xandroid.view.scheduleview.WeekScheduleView;

public class XScheduleViewAcitivty extends BaseActivity {

    @BindView(R.id.xscheduleview)
    private WeekScheduleView mWeekScheduleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xschedule_view);
    }


}
