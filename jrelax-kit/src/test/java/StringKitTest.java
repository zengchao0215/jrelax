import com.jrelax.kit.StringKit;

public class StringKitTest {
    public static void main(String[] args) {
        String source = "创建角色：{{0}, {1}, {0}}";

        System.out.println(StringKit.format(source, "书记", "1254"));
    }
}
