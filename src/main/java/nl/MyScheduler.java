package nl;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.inject.Singleton;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

@Singleton
@Startup
public class MyScheduler implements Serializable {

    Scheduler sched;

    @PostConstruct
    void init() {
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            sched = sf.getScheduler();
            sched.start();
        } catch (SchedulerException ex) {
            Logger.getLogger(MyScheduler.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Failed to create Scheduler.  " + ex, ex);
        }
    }

    public void scheduleJob(JobDetail jd, Trigger t) throws SchedulerException {

        sched.scheduleJob(jd, t);
        System.out.println("Job scheduled at " + new Date());

    }

    public void unscheduleJob(TriggerKey tk) {
        try {
            sched.unscheduleJob(tk);
            System.out.println("Trgger unscheduled");
        } catch (SchedulerException ex) {
            System.out.println("Failed to remove triggerKey");
        }

    }

    public void shutdown() {
        try {
            sched.shutdown();
        } catch (SchedulerException ex) {
            System.out.println("Shutdown failed. " + ex);
        }
    }
}
