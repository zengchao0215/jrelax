import org.apache.commons.lang.StringUtils;

/**
 * Created by zengchao on 2017/7/4.
 */
public class TestLevenshteinDistance {
    public static void main(String[] args) {
        System.out.println(StringUtils.getLevenshteinDistance("/open/dev", "/index"));
    }
}
