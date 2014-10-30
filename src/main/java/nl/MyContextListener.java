package nl;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyContextListener implements ServletContextListener {

    @Inject
    MyScheduler myScheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("App started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        myScheduler.shutdown();
        System.out.println("App stopped");
    }

}
