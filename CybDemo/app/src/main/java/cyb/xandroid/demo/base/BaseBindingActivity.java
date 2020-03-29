package cyb.xandroid.demo.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import cyb.xandroid.util.StatusBarUtil;

/**
 * Created by chenyangbo on 2017/6/26.
 */

public class BaseBindingActivity<VB extends ViewDataBinding> extends BaseAppCompatActivity {

    protected VB mViewDataBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void startActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }

    public void setStatusBarColor(int res) {
        StatusBarUtil.setStatusBarColor(this,getResources().getColor(res));
    }

    protected VB  VB(){
        return mViewDataBinding;
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        mViewDataBinding = DataBindingUtil.bind(view);
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mViewDataBinding = DataBindingUtil.bind(view);
        super.setContentView(view, params);
    }

    public void setVariable(int variableId, @Nullable Object value) {
        if (mViewDataBinding != null) {
            mViewDataBinding.setVariable(variableId, value);
        }
    }

    /**
     * 设置Activity布局并且将布局和ViewModle绑定
     *
     * @param layoutResID
     * @param variableId
     * @param viewModle
     * @param <T>
     * @return
     */
    public <T extends BaseViewModle> T setContentView(int layoutResID, int variableId, @Nullable T viewModle) {
        setContentView(layoutResID);
        setVariable(variableId, viewModle);
        return viewModle;
    }
}
