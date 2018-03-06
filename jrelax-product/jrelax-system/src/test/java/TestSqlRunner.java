import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;

import java.io.File;

/**
 * Created by zengchao on 2017/3/2.
 */
public class TestSqlRunner {

    public static void main(String[] args) {
        SQLExec exec = new SQLExec();
        exec.setDriver("com.mysql.jdbc.Driver");
        exec.setUrl("jdbc:mysql://localhost:3306/test?useSSL=true&verifyServerCertificate=false&useUnicode=true&characterEncoding=UTF-8");
        exec.setUserid("root");
        exec.setPassword("pass");
        exec.setEncoding("UTF-8");

       //传入执行的脚本
        exec.setSrc(new File("/Users/zengchao/Desktop/jrelax-bi_2017-03-02.sql"));
        exec.setPrint(true);
        exec.setProject(new Project());
        exec.setOnerror(new SQLExec.OnError());

        exec.execute();
    }
}
