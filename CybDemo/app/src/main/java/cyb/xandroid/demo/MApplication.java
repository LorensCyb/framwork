package cyb.xandroid.demo;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BlockCanary.install(this, new BlockCanaryContext(){

        } ).start();
    }


}
