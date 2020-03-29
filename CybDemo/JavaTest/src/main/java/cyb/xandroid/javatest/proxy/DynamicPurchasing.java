package cyb.xandroid.javatest.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicPurchasing implements InvocationHandler {

    private Object obj;

    public DynamicPurchasing(Object object){
        this.obj = object;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
//        System.out.println("Object proxy :" + proxy);
        System.out.println("Object[] objects :" + objects);
        Object result  = method.invoke(obj,objects);
        if(method.getName().equals("buy")){
            System.out.println("L buy buy buy");
        }
        return null;
    }
}
