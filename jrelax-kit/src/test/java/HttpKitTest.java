import com.jrelax.kit.HttpKit;

import java.util.HashMap;
import java.util.Map;

public class HttpKitTest {
    public static void main(String[] args) {
        String data = null;
        Map<String, String> params = new HashMap<>();
        params.put("token", "D009FAD777AF4CEE9D3B091F831D1BA6");
        data = HttpKit.sendPost("http://login.dgc.com:8080/edu-sso/sso/api", params);
        System.out.println(data);
    }
}
