package cyb.xandroid.javatest.object;


public class TestFinalizeMethod {

    public static void main(String[] args){
        MObject mObject1 = new MObject();
        String mObject2 = "abc";
        String mObject3 = "abc";
        Integer integer = 1;
        integer.intValue();

//        if(mObject1 == mObject2){
//            System.out.println("mObject1 == mObject2 : true");
//        }else{
//            System.out.println("mObject1 == mObject2 : false");
//        }

        if(mObject2 == mObject3){
            System.out.println("mObject2 == mObject3 : true");
        }else{
            System.out.println("mObject2 == mObject3 : false");
        }

        if(mObject1.equals(mObject2)){
            System.out.println("mObject1.equals(mObject2) : true");
        }else{
            System.out.println("mObject1.equals(mObject2) : false");
        }

        if(mObject3.equals(mObject2)){
            System.out.println("mObject3.equals(mObject2) : true");
        }else{
            System.out.println("mObject3.equals(mObject2) : false");
        }

    }

    static class MObject implements T{

        @Override
        public void test() {

        }
    }

    static  interface T{
        int a = 0;
        public void test();
    }
}
