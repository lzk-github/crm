package com.kaishengit.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 项目中的动态任务
 */
@Component
public class MyDynamicJob implements Job {

    /**
     * 实现类继承接口中的执行任务的方法
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        Integer to = dataMap.getIntegerFromString("to");
        String message = dataMap.getString("message");
        System.out.println("任务Id:" + to + ";发送微信,内容是:" + message);
    }
}
