package cyb.xandroid.javatest;


import java.io.Closeable;
import java.io.IOException;

/**
 * 1.测试clone() 方法的使用，深入理解浅拷贝和深拷贝的区别
 */
public class TestClone {

    public static void main(String[] arg) {
        //
        Human humanOrigin = new Human();
        Human humanClone = null;
        try {
            humanClone = (Human) humanOrigin.clone();
            System.out.println("humanOrigin ?=  humanClone : " + (humanOrigin == humanClone));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            //因为Person没有继承Cloneable接口所以报错
            System.out.println("clone failed !");
        }
        System.out.println("\n\n");


        Person personOrigin = new Person("origin");
        Person personClone = null;
        try {
            personClone = (Person) personOrigin.clone();
            //查看克隆后名字
            System.out.println("personOrigin name : " + personOrigin.getName()
                    + "; personClone name : " + personClone.getName());
            //查看修改克隆名字
            personClone.setName("clone");
            System.out.println("personOrigin name : " + personOrigin.getName()
                    + "; personClone name : " + personClone.getName());
            //
            System.out.println("personOrigin ?=  personClone : " + (personClone == personOrigin));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            //因为Person没有继承Cloneable接口所以报错
            System.out.println("clone failed !");
        }
    }

    /**
     *
     */
    static class Human {

        public Human() {

        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    static class Person implements Cloneable {

        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }


    static class Student implements Closeable {

        private Boy boy;

        private Grill grill;



        @Override
        public void close() throws IOException {

        }
    }

    static class Boy implements Closeable{

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void close() throws IOException {

        }
    }

    static class Grill implements Closeable{

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void close() throws IOException {

        }
    }

}
