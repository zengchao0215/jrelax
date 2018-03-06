import com.jrelax.core.support.ApplicationCommon;
import com.jrelax.orm.query.Condition;
import com.jrelax.orm.query.PageBean;
import com.jrelax.web.support.QuickService;
import com.jrelax.web.system.entity.Config;
import com.jrelax.web.system.entity.DataDictItem;
import com.jrelax.web.system.entity.Log;
import com.jrelax.web.system.entity.Resource;
import com.jrelax.web.system.service.ConfigService;
import com.jrelax.web.system.service.DataDictItemService;
import com.jrelax.web.system.service.LogService;
import com.jrelax.web.system.service.ResourceService;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-context.xml"})
public class TestQueryDao {
    @Autowired
    private LogService logService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DataDictItemService dataDictItemService;

    @Test
    public void test() {
        Log log = new Log();

//        log = logService.getById("40289f39563c046701563c05d9d60000");
//        log = logService.get(Condition.NEW().eq("iP", "localhost"));
//        log = logService.get(Restrictions.like("iP", "192.168.1", MatchMode.ANYWHERE));
//        log = logService.get("from Log where iP = ?", "localhost");

//        System.out.println(log.getContent());

//        System.out.println(logService.getDouble("select count(*) from Log"));
//        System.out.println(logService.getDouble("select count(*) from Log where iP = ?", "localhost"));

//        System.out.println(logService.getInt("select count(*) from Log where iP = ?", "localhost"));
//        System.out.println(logService.getLong("select count(*) from Log where iP = ?", "localhost"));
//        System.out.println(logService.getObject("select count(*) = 10 from Log where iP = ?", "localhost"));
//        System.out.println(logService.getBoolean("select count(*) = 10 from Log where iP = ?", "localhost"));

//        System.out.println(logService.getMap("select id as id, type as type from Log"));
//        PageBean pageBean = new PageBean();
//        System.out.println(logService.listToEntity("select id from Log"));
//        System.out.println(pageBean.getTotalCount());

//        System.out.println(logService.get("select id, type from Log where id=?", "402881285682d273015682d2ec0d0000").getType());

//        /********************批量处理数据****************/
//        long a = System.currentTimeMillis();
//
//        String hql = "delete from Log where id=?";
//        Object[][] params = new Object[100][1];
//        for (int i = 0; i < 100; i++) {
//            params[i] = new Object[]{"123"};
//        }
//        logService.executeBatch(hql, params);
//
//        System.out.println(System.currentTimeMillis() - a);
//
//        a = System.currentTimeMillis();
//        for (int i = 0; i < 100; i++) {
//            logService.executeBatch(hql, "23");
//        }
//        System.out.println(System.currentTimeMillis() - a);

        /******************批量保存***********************/
        System.out.println("批量保存....");
        long a = System.currentTimeMillis();

        List<Config> configList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Config config = new Config();
            config.setK("test");
            config.setV("test V");

            configList.add(config);
        }
        configService.save(configList);

        System.out.println(System.currentTimeMillis() - a);

        System.out.println("正常保存....");
        a = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Config config = new Config();
            config.setK("test");
            config.setV("test V");

            configService.save(config);
        }
        System.out.println(System.currentTimeMillis() - a);
    }

    @Test
    public void testNativeToArray() {
        PageBean pageBean = new PageBean();
        logService.nativeListToArray(pageBean, "select id from sys_log");
        System.out.println(pageBean.getTotalCount());
    }

    @Test
    public void testNativeToMap() {
        PageBean pageBean = new PageBean();

        System.out.println(logService.nativeListToMap(pageBean, "select id,type from sys_log").get(0).get("id"));
    }

    @Test
    public void testExample() {
        Condition condition = Condition.NEW();

        Log log = new Log();
        log.setModule("系统登录");

        condition.example(log);

        System.out.println(logService.list(condition));
    }

    @Test
    public void testCache() {
        ApplicationCommon.DEBUG = false;
        PageBean pageBean = new PageBean();

        Resource res = resourceService.list(pageBean).get(0);
        System.out.println(res.getId() + "->" + res.hashCode());

        Resource res2 = resourceService.list(pageBean).get(0);
        System.out.println(res2.getId() + "->" + res2.hashCode());

        System.out.println(res == res2);
    }

    @Test
    public void testMap() {
//        System.out.println(logService.getList("select id, type from Log"));
        System.out.println(logService.getMap("select id, type from Log"));
    }

    @Test
    public void testRecord() {
//        Record record = logService.getRecord("select id, type from Log");
//        System.out.println(record.getString("id"));
//        System.out.println(record.getInt("type"));

//        System.out.println(logService.listToRecord("select id, type from Log"));
        PageBean pageBean = new PageBean();
        pageBean.setRows(2);
//        System.out.println(logService.listToRecord(pageBean, "select id, type from Log"));

//        System.out.println(logService.nativeGetList("select id, type from sys_log"));
//        System.out.println(logService.nativeGetMap("select id, type from sys_log"));
//        System.out.println(logService.nativeGetRecord("select id, type from sys_log"));

        System.out.println(logService.nativeListToRecord("select id, module from sys_log"));
        System.out.println(logService.nativeListToRecord(pageBean, "select id, module from sys_log"));
    }

    /**
     * 经验证，使用OpenSession创建的session doWork方法中的connection最后需要关闭/或者关闭session
     */
    @Test
    public void testDoWork() {
        for (int i = 0; i < 50; i++) {
            Session session = logService.getBaseDao().getSessionFactory().openSession();
            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    Statement stmt = connection.createStatement();
                    stmt.execute("select 1");
                }
            });
            session.close();
            System.out.println(i);
        }

    }

    @Test
    public void testFilter() {
        logService.get();
    }

    @Test
    public void testQuick() {
        for (int i = 0; i < 50; i++) {
            System.out.println(QuickService.get(Log.class).get());
        }
    }

    @Test
    public void testCache1() {
        String id = logService.get().getId();
        System.out.println("id:" + id);
        for (int i = 0; i < 10; i++) {
            Log log = logService.getCacheService().getByCache(id);
            System.out.println("cache id : " + log.getId());
        }
    }

    @Test
    public void testCache2() {
        Log log = logService.get();
        log = logService.getCacheService().getByCache(log.getId());
        System.out.println(log.getContent());

        log.setContent("Update -> "+log.getContent());
        logService.getCacheService().updateAndCache(log);
        log = logService.getCacheService().getByCache(log.getId());
        System.out.println(log.getContent());

        log.setContent("Merge -> "+log.getContent());
        logService.getCacheService().mergeAndCache(log);
        log = logService.getCacheService().getByCache(log.getId());
        System.out.println(log.getContent());
    }

    @Test
    public void testCache3(){
        for (int i = 0; i < 10; i++) {
            System.out.println(logService.getCacheService().countByCache("logCount", "select count(*) from Log"));
        }
    }

    @Test
    public void testQuery(){
//        System.out.println(logService.getInt("select sum(level) from Log"));
//        System.out.println(logService.getInt("select sum(level) from Log where level = 100"));
//        System.out.println(logService.getInt("select sum(level) from Log where level = 100", 0));

        List<DataDictItem> list = dataDictItemService.listToEntity("select dataDict.id, k from DataDictItem");
        System.out.println(list.get(0).getDataDict().getId());
    }

    @Test
    public void testJpaQuery(){
        int count = logService.countAll();

        System.out.println(count);
    }
}
