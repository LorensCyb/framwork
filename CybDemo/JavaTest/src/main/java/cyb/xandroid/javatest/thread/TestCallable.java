package cyb.xandroid.javatest.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestCallable {

    public static void main(String[] arg){
        System.out.println("main start");
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
         Future<?> future = threadPool.submit(new MyRunnable()) ;
//        Future<String> future = threadPool.submit(new MyCallable());
        try {
            // 这里会发生阻塞
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
        System.out.println("main end");
    }

    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            // 模拟耗时任务
            Thread.sleep(3000);
            System.out.println("MyCallable Thread：" + Thread.currentThread().getName());
            return "MyCallable" ;
        }
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            // 模拟耗时任务
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MyRunnable");
        }
    }
}
