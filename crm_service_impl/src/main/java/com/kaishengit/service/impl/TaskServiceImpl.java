package com.kaishengit.service.impl;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.job.MyDynamicJob;
import com.kaishengit.mapper.TaskMapper;
import com.kaishengit.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cronutils.model.field.expression.FieldExpressionFactory.*;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    /**
     * 自动注入quartz的调度器
     */
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    @Autowired
    private TaskMapper taskMapper;

    /**
     * 根据员工id查找任务列表
     *
     * @param id
     * @return
     */
    @Override
    public List<Task> findTasksByAccountId(Integer id) {

        return taskMapper.findByAccountId(id);
    }

    /**
     * 新增任务,并调用quartz框架设置提醒
     *
     * @param account  当前员工对象
     * @param chance   关联Chance对象
     * @param customer 关联客户对象
     */
    @Override
    @Transactional
    public void saveNewTask(Task task, Account account, Chance chance, Customer customer) {
        //传值判断
        Integer accountId = account.getId();
        Integer chanceId = null;
        Integer customerId = null;
        chanceId = (chance == null ? null : chance.getId());
        customerId = (customer == null ? null : customer.getId());

        //封装Task对象
        task.setAccountId(accountId);
        task.setChanceId(chanceId);
        task.setCustomerId(customerId);
        task.setCreateTime(new Date().toString());

        //执行添加数据库
        taskMapper.insert(task);

        //使用quartz框架设置提醒动作
        newQuartzJobScheduler(task);
    }

    /**
     * 对传入的task对象创建Quartz框架的动态提醒任务
     * @param task
     */
    private void newQuartzJobScheduler(Task task) {
        if (StringUtils.isNotEmpty(task.getRemindTime())) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.putAsString("to", task.getId());
            jobDataMap.put("message", task.getTaskName());
            //通过调度器工厂获取调度器
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //jobDetail
            JobDetail jobDetail = JobBuilder.newJob(MyDynamicJob.class)
                    .withIdentity(new JobKey("taskId:" + task.getId(), "mydynamic-group"))
                    .setJobData(jobDataMap).build();

            //将remindTime由String类型转为时间格式
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            DateTime dateTime = formatter.parseDateTime(task.getRemindTime());

            //根据日期生成cron表达式
            Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                    .withYear(on(dateTime.getYear()))
                    .withMonth(on(dateTime.getMonthOfYear()))
                    .withDoM(on(dateTime.getDayOfMonth()))
                    .withHour(on(dateTime.getHourOfDay()))
                    .withMinute(on(dateTime.getMinuteOfHour()))
                    .withSecond(on(dateTime.getSecondOfMinute()))
                    .withDoW(questionMark())
                    .instance();

            //获取cron表达式
            String cronExpression = cron.asString();
            System.out.println("cron表达式:" + cronExpression);//TODO delete

            //通过 cron调度器 -> 创建触发器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

            try {
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
            } catch (SchedulerException ex) {
                throw new ServiceException("添加定时任务异常", ex);
            }
        }
    }

    /**
     * 根据task id获取Task对象
     *
     * @param taskId
     * @return
     */
    @Override
    public Task findByTaskId(Integer taskId) {
        return taskMapper.selectByTaskId(taskId);
    }

    /**
     * 改变Task对象的state状态
     *
     * @param task
     */
    @Override
    public void changeState(Task task) {
        //更改状态
        Byte state = task.getState();
        if (state.equals(new Byte("0"))) {
            state = 1;
        } else {
            state = 0;
        }
        task.setState(state);

        //更新task对象
        taskMapper.updateByPrimaryKeySelective(task);
    }

    /**
     * 根据员工id与客户id查找任务列表
     *
     * @param accountId
     * @param customerId
     * @return 任务列表的集合
     */
    @Override
    public List<Task> findTasksByAccountIdAndCustomerId(Integer accountId, Integer customerId) {
        return taskMapper.selectByAccountIdAndCustomerId(accountId, customerId);
    }

    /**
     * 根据员工id和销售机会id查找任务列表
     *
     * @param accountId
     * @param chanceId
     * @return 任务列表
     */
    @Override
    public List<Task> findByAccountIdAndChanceId(Integer accountId, Integer chanceId) {
        return taskMapper.selectByAccountIdAndChanceId(accountId, chanceId);
    }

    /**
     * 执行删除Task对象
     *
     * @param task
     */
    @Override
    @Transactional
    public void delTask(Task task) {
        taskMapper.deleteByPrimaryKey(task.getId());
        //查看任务调度器中是否有该Task对象对应的任务,如果有,则删除
        if (StringUtils.isNotEmpty(task.getRemindTime())) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.deleteJob(new JobKey("taskId:" + task.getId(), "mydynamic-group"));
            } catch (SchedulerException e) {
                throw new ServiceException("删除调度器任务失败", e);
            }
        }
    }

    /**
     * 更新Task对象,更新quartz提醒事件
     *
     * @param task
     */
    @Override
    @Transactional
    public void updateTask(Task task) {
        taskMapper.updateByPrimaryKeySelective(task);

        //更新quartz调度器提醒
        //步骤1,删除原提醒
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = new JobKey("taskId:" + task.getId(), "mydynamic-group");
        try {
            if (scheduler.checkExists(jobKey)) {         //如果已经存在这个jobkey,就删除
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            throw new ServiceException();
        }
        //步骤2,新创建提醒
        newQuartzJobScheduler(task);

    }
}
