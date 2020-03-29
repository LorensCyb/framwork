package cyb.xandroid.demo;

import com.github.moduth.blockcanary.BlockCanaryContext;

public class AppBlockContext extends BlockCanaryContext {
    //实现各种上下文，包括应用标识符，用户uid ，网络类型，卡慢判断阀值，Log保存位置等内容
    //1000ms，事件处理时间的阀值，可以通过修改这个阀值来修改超时阀值
    public int provideBlockThreshold() {
        return 1000;
    }
}