package cyb.xandroid.javatest.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class TestFutureTask {
    public static void main(String[] arg){
        FutureTask<Object> futureTask = new FutureTask<Object>(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });

        Executors.callable(futureTask);

        try {
            Object obj = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
