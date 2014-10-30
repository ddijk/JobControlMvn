package nl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.TriggerKey;

@Named("backing")
@SessionScoped
public class Backing implements Serializable {

    String cronSchedule;

    String name;

    Map<String, TriggerKey> triggerMap;
    @Inject
    MyScheduler myScheduler;

    public Backing() {
        this.triggerMap = new HashMap<>();
    }

    public String getCronSchedule() {
        return cronSchedule;
    }

    public void setCronSchedule(String cronSchedule) {
        this.cronSchedule = cronSchedule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void schedule() {

        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(HelloJob.class)
                .withIdentity(name, "group1")
                .withDescription(name)
                .build();

        // Trigger the job to run on the next round minute
        Date runTime = evenMinuteDate(new Date());
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(runTime)
                .build();

//        CronTrigger trigger = newTrigger()
//                .withIdentity("trigger1", "group1")
//                .withSchedule(cronSchedule(cronSchedule))
//                .build();
        triggerMap.put(name, trigger.getKey());

        try {
            myScheduler.scheduleJob(job, trigger);
            System.out.println("Schedule job " + name + ", schema is " + cronSchedule);
        } catch (SchedulerException ex) {
            System.out.println("Failed to Schedule job " + name + ", schema is " + cronSchedule + ", ex=" + ex);
        }
    }

    public void unschedule() {

        myScheduler.unscheduleJob(triggerMap.get(name));
    }
}
