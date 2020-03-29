package cyb.xandroid.javatest.proxy;

import java.lang.reflect.Proxy;

public class DynamicClient {

    public static void main(String[] args){
        //
        IShop liuShop = new LiuShop();
        DynamicPurchasing dynamicPurchasing = new DynamicPurchasing(liuShop);
        ClassLoader loader = dynamicPurchasing.getClass().getClassLoader();
        IShop purchasing =(IShop)  Proxy.newProxyInstance(loader, liuShop
                .getClass().getInterfaces(),dynamicPurchasing);
        purchasing.buy();
    }
}
