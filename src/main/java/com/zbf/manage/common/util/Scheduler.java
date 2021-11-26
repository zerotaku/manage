package com.zbf.manage.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zbf.manage.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private AccessService accessService;

    //每隔2秒执行一次
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void testTasks() {
        accessService.scriptKeepAlive();
//        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
    }

    //每天3：05执行
//    @Scheduled(cron = "0 05 03 ? * *")
//    public void testTasksLong() {
//        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
//    }


}
