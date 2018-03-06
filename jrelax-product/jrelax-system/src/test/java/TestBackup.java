import com.jrelax.config.JRelaxPropertyPlaceholderConfigurer;
import com.jrelax.orm.backup.BackupFactory;
import com.jrelax.orm.backup.IBackup;
import com.jrelax.web.system.service.DataBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库备份测试用例
 * Created by zengchao on 2017/3/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-context.xml"})
public class TestBackup {
    @Resource
    private DataBaseService dataBaseService;
    @Resource
    private JRelaxPropertyPlaceholderConfigurer configurer;

    @Test
    public void mysql() {
        Map<String, String> dbInfoMap = dataBaseService.getDBInfo();
        Properties props = configurer.getHibernateProperties();

        String type = dbInfoMap.get("type");

        Properties config = new Properties();
        config.setProperty("url", props.getProperty("jdbc.master.url"));
        config.setProperty("username", props.getProperty("jdbc.master.username"));
        config.setProperty("password", props.getProperty("jdbc.master.password"));

        IBackup backup = BackupFactory.create(type);
        backup.setProperties(config);
        backup.setExecPath("D:\\Program Files\\MySQL\\MySQL Server 5.7\\bin");
        backup.setSavePath("F:/backup");

        System.out.println(backup.list());
        System.out.println(backup.list("sys_config"));
    }
}
