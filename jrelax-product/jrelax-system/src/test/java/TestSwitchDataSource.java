import com.jrelax.orm.datasource.DataSourceSwitcher;
import com.jrelax.web.system.service.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 多数据源切换测试类
 * 多数据源切换是指在系统需要连接多个数据源时，直接使用DataSourceSwitcher类切换数据源
 * （切换只对当前线程有效，借助ThreadLocal实现）
 * Created by zengchao on 2016-10-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-context.xml"})
public class TestSwitchDataSource {
    @Resource
    private LogService logService;

    @Test
    public void test(){
        DataSourceSwitcher dataSourceSwitcher = DataSourceSwitcher.getInstance();

        System.out.println(logService.count());

        dataSourceSwitcher.switchToSlave();//切换数据源

        System.out.println(logService.count());
        System.out.println(logService.count());

        dataSourceSwitcher.switchToMaster();
        System.out.println(logService.count());

    }
}
