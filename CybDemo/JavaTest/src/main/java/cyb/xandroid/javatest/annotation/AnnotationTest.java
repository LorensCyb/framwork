package cyb.xandroid.javatest.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 测试java注解类
 *
 * @author zhangqh
 * @date 2018年4月22日
 */
@AnTargetType
public class AnnotationTest {
    @AnTargetField
    private String field = "我是字段";

    @AnTargetMethod("测试方法")
    public void test(@AnTargetParameter String args) {
        System.out.println("参数值 === "+args);
    }

    public static void main(String[] args) {
        // 获取类上的注解MyAnTargetType
        AnTargetType t = AnnotationTest.class.getAnnotation(AnTargetType.class);
        System.out.println("类上的注解值 === "+t.value());
        AnTargetMethod tm = null;
        try {
            // 根据反射获取AnnotationTest类上的test方法
            Method method = AnnotationTest.class.getDeclaredMethod("test",String.class);
            // 获取方法上的注解MyAnTargetMethod
            tm = method.getAnnotation(AnTargetMethod.class);
            System.out.println("方法上的注解值 === "+tm.value());
            // 获取方法上的所有参数注解  循环所有注解找到MyAnTargetParameter注解
            Annotation[][] annotations = method.getParameterAnnotations();
            for(Annotation[] tt : annotations){
                for(Annotation t1:tt){
                    if(t1 instanceof AnTargetParameter){
                        System.out.println("参数上的注解值 === "+((AnTargetParameter) t1).value());
                    }
                }
            }
            method.invoke(new AnnotationTest(), "改变默认参数");
            // 获取AnnotationTest类上字段field的注解MyAnTargetField
            AnTargetField fieldAn = AnnotationTest.class.getDeclaredField("field").getAnnotation(AnTargetField.class);
            System.out.println("字段上的注解值 === "+fieldAn.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}