import com.jrelax.core.web.transform.TreeTransforms;
import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengchao on 2017/6/30.
 */
public class TestTreeNode {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("item-" + i);
        }

        JSONArray array = TreeTransforms.JSTree.transform2(list, (str, treeNode) -> {
            treeNode.setId(str);
            treeNode.setText(str);
            treeNode.setOpened(true);
            treeNode.setIcon("fa fa-user");
        });


        System.out.println(array);
    }
}
