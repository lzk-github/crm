package com.kaishengit.service;

import com.kaishengit.entity.Account;
import com.kaishengit.entity.Chance;
import com.kaishengit.entity.Customer;
import com.kaishengit.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> findTasksByAccountId(Integer id);

    void saveNewTask(Task task,Account account, Chance chance, Customer customer);

    Task findByTaskId(Integer taskId);

    void changeState(Task task);

    List<Task> findTasksByAccountIdAndCustomerId(Integer accountid, Integer customerId);

    List<Task> findByAccountIdAndChanceId(Integer accountId, Integer chanceId);

    void delTask(Task task);

    void updateTask(Task task);
}
