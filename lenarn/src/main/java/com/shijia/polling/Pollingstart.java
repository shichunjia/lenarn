package com.shijia.polling;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class Pollingstart implements ApplicationListener<ContextRefreshedEvent> {
    private static boolean runflag = false;
    public void onApplicationEvent(ContextRefreshedEvent event){
        if(event.getApplicationContext().getParent()!=null&&!runflag){
            runflag = true;
           Thread thread = new Thread(new HandleDBOperate());
           thread.setDaemon(true);
           thread.setName("HandleDBOperate");
           thread.start();
        }
    }
}
