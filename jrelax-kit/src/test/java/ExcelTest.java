import com.jrelax.kit.ExcelKit;
import com.jrelax.kit.excel.TableToExcel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zengchao on 2017-03-20.
 */
@RunWith(JUnit4.class)
public class ExcelTest {
    @Test
    public void readRow(){
        File file = new File("F:/1.xlsx");
        System.out.println(ExcelKit.getRowAt(file, 0, 0));
    }

    @Test
    public void readSheet(){
        File file = new File("F:/1.xlsx");
        System.out.println(ExcelKit.getSheets(file));
    }

    @Test
    public void fromHtml(){
        StringBuilder html = new StringBuilder("<table>");
        for(int i=0;i<10;i++){
            html.append("<tr>");

            html.append("<td>ABC</td>");
            html.append("<td>&&&</td>");
            html.append("<td>中国字</td>");

            html.append("</tr>");
        }
        html.append("</table>");

        String file = "/Users/zengchao/Downloads/1.xls";
        try {
            new TableToExcel().parse(html.toString()).write(new FileOutputStream(new File(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
