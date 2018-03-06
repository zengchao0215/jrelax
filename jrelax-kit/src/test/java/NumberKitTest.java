import com.jrelax.kit.NumberKit;

public class NumberKitTest {
    public static void main(String[] args) {
        String s = "20170930142002190";

        System.out.println(NumberKit.toHex2(s));
        System.out.println(NumberKit.toHex8(s));
        System.out.println(NumberKit.toHex16(s));
        System.out.println(NumberKit.toHex32(s));
        System.out.println(NumberKit.toHex36(s));
        System.out.println(NumberKit.toHex58(s));
        System.out.println(NumberKit.toHex62(s));
        System.out.println(NumberKit.toHex64(s));
    }
}
