import com.jrelax.core.web.export.Csv;
import com.jrelax.core.web.export.Excel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

/**
 * Excel
 * Created by zengchao on 2017/3/12.
 */
@RunWith(JUnit4.class)
public class TestExport {

    private String[] getTitles(){
        return new String[]{"姓名", "性别", "年龄"};
    }

    public List<Object[]> getData(){
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{"张三", "男", 20});
        data.add(new Object[]{"张三", "男", 20});
        data.add(new Object[]{"张三", "男", 20});
        data.add(new Object[]{"张三", "男", 20});
        data.add(new Object[]{"张三", "男", 20});
        data.add(new Object[]{"张三", "男", 20});
        data.add(new Object[]{"张三", "男", 20});

        return data;
    }

    @Test
    public void testExcel(){
        String[] titles = getTitles();
        List<Object[]> data = getData();

        Excel excel = new Excel();
        excel.setTitles(titles, 0);
        excel.setData(data, 0);
        excel.setTitles(titles, 1);
        excel.setData(data, 1);

        excel.store("/Users/zengchao/Desktop/1.xlsx");
    }

    @Test
    public void testExcel2(){
        String[] titles = new String[]{"姓名", "性别", "年龄"};
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "里斯");map.put("sex", "男");map.put("age", 10);
        for (int i = 0; i < 10; i++) {
            data.add(map);
        }

        Excel excel = new Excel();
        excel.setTitles(Arrays.asList(titles));
        excel.setDataByMap(data, new String[]{"name", "sex", "age"});
        excel.store("/Users/zengchao/Desktop/2.xlsx");
    }

    @Test
    public void testCsv(){
        Csv csv = new Csv();
        csv.setTitles(getTitles());
        csv.setData(getData());

        csv.store("F:/1.csv");
    }
}
