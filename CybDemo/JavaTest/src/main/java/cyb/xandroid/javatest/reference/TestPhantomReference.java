package cyb.xandroid.javatest.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class TestPhantomReference {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed");
    }

    public static void main(String[] arg){
        ReferenceQueue rq = new ReferenceQueue();
        TestPhantomReference twr = new TestPhantomReference();
        PhantomReference pr = new PhantomReference(twr, rq);
        System.out.println("before gc: " + pr.get() + ", " + rq.poll());
        twr = null;  //去掉强引用twr
        System.gc();
        System.out.println("after  gc: " + pr.get() + ", " + rq.poll());
    }
}
