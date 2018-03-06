import com.jrelax.kit.ReflectKit;
import reflect.A;
import reflect.B;
import reflect.C;

public class ReflectKitTest {
    public static void main(String[] args) {
        new ReflectKitTest().test();
    }

    public void test(){
        A a = new A();
//        a.name = "HHH";
//
//        a.b = new B();
//        a.b.age = 12;
//
//        a.b.c = new C();
//        a.b.c.address = "安徽合肥";
//
//        System.out.println(ReflectKit.getFieldValue(a, "b.c"));

        try {
            ReflectKit.setFieldValue(a, "b.age", 11);
            ReflectKit.setFieldValue(a, "b.c.address", "安徽合肥");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("直接访问");
        System.out.println(a.b.age);
        System.out.println(a.b.c.address);

        System.out.println("反射访问");
        System.out.println(ReflectKit.getFieldValue(a, "b.age"));
        System.out.println(ReflectKit.getFieldValue(a, "b.c.address"));
    }
}