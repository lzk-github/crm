package com.kaishengit.controller;

import com.kaishengit.controller.exception.ForbiddenException;
import com.kaishengit.controller.exception.NotFoundException;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.entity.Account;
import com.kaishengit.entity.Task;
import com.kaishengit.service.TaskService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 待办任务的业务控制器
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 访问待办事项列表页面
     * @return
     */
    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("curr_acc");

        //传值,待办事项的list集合
        List<Task> taskList = taskService.findTasksByAccountId(account.getId());
        model.addAttribute("taskList",taskList);
        System.out.println(taskList);


        return "task/task-list";
    }

    /**
     * 从 task/list - >页面访问新增任务页面
     */
    @GetMapping("/new")
    public String newTask() {
        return "task/new-task";
    }

    /**
     * 新增task对象的表单提交
     */
    @PostMapping("/new")
    public String newTask(Task task , RedirectAttributes redirectAttributes,
                          HttpSession session) {

        Account account = (Account) session.getAttribute("curr_acc");

        taskService.saveNewTask(task,account,null,null);


        redirectAttributes.addFlashAttribute("message","添加新的待办事项成功!");
        return "redirect:/task/list";
    }

    @PostMapping("/state/change")
    @ResponseBody
    public AjaxResult changeState(Integer taskId,Integer accountId,HttpSession session) {

        Account account = (Account) session.getAttribute("curr_acc");
        Task task = taskService.findByTaskId(taskId);

        //安全校验
        if(task == null) {
            throw new NotFoundException();
        }
        if(!accountId.equals(account.getId())) {
            throw new ForbiddenException();
        }

        //改变Task任务的state状态
        taskService.changeState(task);

        return AjaxResult.getSuccessInstance();
    }

    /**
     * 删除任务Task
     * @return
     */
    @GetMapping("/del/{id:\\d+}")
    public String delTask(@PathVariable Integer id,HttpSession session,RedirectAttributes redirectAttributes) {

        //安全校验
        Task task = taskService.findByTaskId(id);
        Account account = (Account) session.getAttribute("curr_acc");
        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        //执行删除Task对象
        taskService.delTask(task);

        redirectAttributes.addFlashAttribute("message","删除任务成功!");

        return "redirect:/task/list";
    }

    /**
     * 访问编辑Task任务页面
     */
    @GetMapping("/edit/{id:\\d+}")
    public String edit(@PathVariable Integer id,Integer accountId,
                       Model model,HttpSession session) {
        //安全校验
        Task task = taskService.findByTaskId(id);
        Account account = (Account) session.getAttribute("curr_acc");
        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        model.addAttribute("task",task);

        return "task/edit";
    }

    /**
     * 完成编辑任务后的表单提交
     */
    @PostMapping("/edit/post")
    public String edit(Task task,RedirectAttributes redirectAttributes,
                       HttpSession session) {
        //安全校验
        Account account = (Account) session.getAttribute("curr_acc");
        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        //执行更新编辑任务内容
        taskService.updateTask(task);
        redirectAttributes.addFlashAttribute("message","更新任务成功!");

        return "redirect:/task/list";
    }

}
