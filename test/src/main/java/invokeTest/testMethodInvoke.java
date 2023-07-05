package invokeTest;

import java.lang.reflect.Method;

public class testMethodInvoke {



    public static void main(String[] args) {
        try{
            Class<?> clazz=Class.forName("invokeTest.invokeTest");
            Class<?> clazz1=Class.forName("java.lang.String");
            Object object="String";
            Method method=clazz.getMethod("test");
            Method method1=clazz.getMethod("test1",new Class[]{clazz1});
            String result= (String) method.invoke(clazz.newInstance());
            String result1=(String)method1.invoke(clazz.newInstance(),object);
            System.out.println(result);
            System.out.println(result1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
