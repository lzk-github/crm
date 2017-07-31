package com.kaishengit.job;

import org.springframework.stereotype.Component;

@Component
public class MyConstantJob {

    public void doMyConstantJob() {
        System.out.println("执行: 我的固定任务 by quartz - job schedule ... ");
    }
}
