package nl;

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {

    String id;
    private static int counter;

    public HelloJob() {
    }

    public HelloJob(String id) {
        this.id = id;
    }

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {

        System.out.println("Running job " + jec.getJobDetail().getDescription() + " ** " + counter++);
    }

    @Override
    public String toString() {
        return "job met id " + id + ", at " + new Date();
    }

}
