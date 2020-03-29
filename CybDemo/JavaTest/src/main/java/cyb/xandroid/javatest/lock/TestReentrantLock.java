package cyb.xandroid.javatest.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

    public static void main(String[] arg){
        ReentrantLock lock = new ReentrantLock();
        Future future = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });

        FutureTask futureTask = new FutureTask(new Runnable() {
            @Override
            public void run() {

            }
        },100);

    }
}
