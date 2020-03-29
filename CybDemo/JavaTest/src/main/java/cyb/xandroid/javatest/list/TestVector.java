package cyb.xandroid.javatest.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class TestVector {

    public static void main(String[] arg) {
        // 初始化一个list，放入5个元素
        testVectorFor(Collections.synchronizedList(initList()));
//        testVectorForeach(initList());
    }

    private static List<Integer> initList() {
        final List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        return list;
    }

    private static void testVectorFor(final List<Integer> list) {
        // 线程一：通过Iterator遍历List
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int item : list) {
                    System.out.println("遍历item ：" + item);
                    // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        // 线程二：remove一个元素
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                list.remove(4);
                System.out.println("list.remove(4)");
            }
        }).start();
    }

    private static void testVectorForeach(final List<Integer> list) {
        // 线程一：通过Iterator遍历List
        new Thread(new Runnable() {
            @Override
            public void run() {
                list.forEach(item -> {
                    System.out.println("遍历元素：" + item);
                    // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();

        // 线程二：remove一个元素
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 由于程序跑的太快，这里sleep了1秒来调慢程序的运行速度
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                list.remove(4);
                System.out.println("list.remove(4)");
            }
        }).start();
    }
}
