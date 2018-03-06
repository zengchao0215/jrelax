import com.jrelax.job.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Job1 extends Job {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        logger.info("Job1 is running...");
    }
}
