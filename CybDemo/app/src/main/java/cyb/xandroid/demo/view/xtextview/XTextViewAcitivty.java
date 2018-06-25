package cyb.xandroid.demo.view.xtextview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import cyb.xandroid.base.BaseActivity;
import cyb.xandroid.bind.BindView;
import cyb.xandroid.demo.R;
import cyb.xandroid.demo.view.xtextview.decorator.MoveEffectDecorator;
import cyb.xandroid.demo.view.xtextview.decorator.OpportunityDemoDecorator;
import cyb.xandroid.demo.view.xtextview.decorator.RippleDecorator;
import cyb.xandroid.view.XTextView;

public class XTextViewAcitivty extends BaseActivity {

    @BindView(R.id.stv_17)
    private XTextView stv_17;
    @BindView(R.id.stv_18)
    private XTextView stv_18;
    @BindView(R.id.stv_19)
    private XTextView stv_19;
    @BindView(R.id.stv_20)
    private XTextView stv_20;
    @BindView(R.id.stv_21)
    private XTextView stv_21;
    @BindView(R.id.stv_22)
    private XTextView stv_22;
    @BindView(R.id.btn_next)
    private XTextView btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xtextview);
        initView();
    }

    private void initView() {
        stv_17.setAdjuster(new MoveEffectDecorator().setOpportunity(XTextView.Decorator.Opportunity.BEFORE_DRAWABLE))
                .setAutoAdjust(true)
                .startAnim();

//    stv_18.setAdjuster(new RippleAdjuster(getResources().getColor(R.color.opacity_5_a58fed)));
        stv_18.setAdjuster(new RippleDecorator(getResources().getColor(R.color.opacity_9_blue)));

        OpportunityDemoDecorator opportunityDemoAdjuster1 = new OpportunityDemoDecorator();
        opportunityDemoAdjuster1.setOpportunity(XTextView.Decorator.Opportunity.BEFORE_DRAWABLE);
        stv_19.setAdjuster(opportunityDemoAdjuster1);
        stv_19.setAutoAdjust(true);

        OpportunityDemoDecorator opportunityDemoAdjuster2 = new OpportunityDemoDecorator();
        opportunityDemoAdjuster2.setOpportunity(XTextView.Decorator.Opportunity.BEFORE_TEXT);
        stv_20.setAdjuster(opportunityDemoAdjuster2);
        stv_20.setAutoAdjust(true);

        OpportunityDemoDecorator opportunityDemoAdjuster3 = new OpportunityDemoDecorator();
        opportunityDemoAdjuster3.setOpportunity(XTextView.Decorator.Opportunity.AT_LAST);
        stv_21.addAdjuster(opportunityDemoAdjuster3);
        stv_21.setAutoAdjust(true);
        stv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stv_21.removeAdjuster(0);
            }
        });

        btn_next.setFrameRate(60);
        btn_next.setShaderStartColor(Color.RED);

//    stv_22.setShaderStartColor(Color.BLUE);
//    stv_22.setShaderEndColor(Color.YELLOW);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XTextViewAcitivty.this, XTextViewSceActivity.class));
            }
        });
    }


}
