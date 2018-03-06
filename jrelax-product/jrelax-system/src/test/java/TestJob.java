import com.jrelax.job.JobManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class TestJob {
    public static void main(String[] args) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        try {
            factoryBean.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JobManager jobManager = new JobManager();
        jobManager.setScheduler(factoryBean.getScheduler());

        jobManager.add("job1", "job", "job1-trigger", "trigger", Job1.class, "0/5 * * * * ? ");

        try {
            Thread.sleep(1000 * 11);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        jobManager.pause("job1", "job");
        System.out.println("任务暂停");

        try {
            Thread.sleep(1000 * 11);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        jobManager.resume("job1", "job");
        System.out.println("任务恢复");
    }
}

