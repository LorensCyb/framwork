package cyb.xandroid.javatest.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class TestAtomicReferenceFieldUpdater {

    public static void main(String[] arg){
        AtomicReferenceFieldUpdater updater= AtomicReferenceFieldUpdater.newUpdater(Person .class, String.class, "name");
        Person person = new Person();
        updater.compareAndSet(person ,person .name,"老王") ;
        System.out.println(person.name);
    }

    static  class  Person {
        volatile  String name="老刘";
    }
}
