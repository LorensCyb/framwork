package cyb.xandroid.javatest.algorithm;

import java.util.HashSet;
import java.util.Set;

public class testAlgorithm {
    public static void main(String[] args) {
        //eatApple(10);
        calculateAllNumber(10);
//        calculateSquare(5);
    }


    /**
     * x个苹果，一天只能吃一个、两个、或者三个，问多少天可以吃完？
     *
     * @param sum
     */
    public static void eatApple(int sum) {
        //方法一
        eatApple(10, 0);
        for (Integer day : mDaysSet) {
            System.out.println("第" + day + "天吃完");
        }
        //方法二
        int maxDay = sum;
        int minDay = sum % 3 == 0 ? sum / 3 : sum / 3 + 1;
        System.out.println("需要" + minDay + "~" + maxDay + "天");
    }

    public static Set<Integer> mDaysSet = new HashSet();

    public static void eatApple(int sum, int days) {
        if (sum > 0) {
            eatApple(sum - 1, days + 1);
        } else {
            mDaysSet.add(days);
            return;
        }

        if (sum > 1) {
            eatApple(sum - 2, days + 1);
        }

        if (sum > 2) {
            eatApple(sum - 3, days + 1);
        }
    }

    /**
     * x个苹果，一天只能吃一个、两个、或者三个，问有多少种吃法？
     *
     * @param x
     * @return
     */
    public static void calculateAllNumber(int x){
        System.out.println("一共有" + getStepNumber(10)+ "吃法");
    }


    public static long getStepNumber(int x) {
        if (x == 1) {
            return 1;
        }

        if(x == 2){
            return 2;
        }

        if(x == 3){
            return 4;
        }

        if(x > 3){
            return getStepNumber(x-1) + getStepNumber(x-2)+ getStepNumber(x-3) ;
        }

        return 0;
    }


    /**
     * N*N的方格纸,里面有多少个正方形？
     *
     * @param n
     * @param
     */
    public static void calculateSquare(int n) {
        // 方法一
        int sum = 0;
        for (int m = 1; m <= n; m++) {
            sum += calculateSquare(n, m);
        }
        System.out.println(n + "*" + n + "的方格纸,里面有" + sum + "个正方形");
        //方法二
        System.out.println(n + "*" + n + "的方格纸,里面有" + ((n + 1) * (2 * n + 1) * n / 6) + "个正方形");


    }

    public static int calculateSquare(int n, int m) {
        if (m <= n && m > 0) {
            return (int) Math.pow(n - (m - 1), 2);
        }
        return 0;
    }

    /***
     * 冒泡排序
     */


}
