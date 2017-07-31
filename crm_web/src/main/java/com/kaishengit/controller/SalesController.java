package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.controller.exception.ForbiddenException;
import com.kaishengit.controller.exception.NotFoundException;
import com.kaishengit.entity.*;
import com.kaishengit.service.ChanceService;
import com.kaishengit.service.CustomerService;
import com.kaishengit.service.RecordService;
import com.kaishengit.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private CustomerService customerService;

    /**
     * 自动注入ChanceService
     */
    @Autowired
    private ChanceService chanceService;

    /**
     * 访问我的销售机会页面
     *
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/my")
    public String mySales(@RequestParam(required = false, defaultValue = "1") Integer p,
                          Model model, HttpSession session) {

        Account account = (Account) session.getAttribute("curr_acc");

        PageInfo<Chance> pageInfo = chanceService.findChancePage(account, p);
        model.addAttribute("pageInfo", pageInfo);

        return "/sales/my-sales";
    }

    /**
     * 添加销售机会页面的访问
     */
    @GetMapping("/add")
    public String addChance(Model model) {

        //传值,进度列表
        List<String> progressList = chanceService.findAllProgress();
        model.addAttribute("progressList", progressList);

        //传值,客户列表
        List<Customer> customerList = customerService.getAllCustomers();
        model.addAttribute("customerList", customerList);

        //访问添加销售机会页面
        return "sales/add-sales";
    }

    /**
     * 销售机会新增的表单提交处理
     */
    @PostMapping("/add")
    public String addChance(Chance chance, HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Account account = (Account) session.getAttribute("curr_acc");

        //新增销售机会
        chanceService.saveChance(account, chance);

        //如果content内容不为空,则新增一条record数据
        if(StringUtils.isNotEmpty(chance.getContent())) {
            recordService.saveNewRecord(chance.getId(),chance.getContent());
        }

        redirectAttributes.addFlashAttribute("message", "新增销售机会成功");

        return "redirect:/sales/my";
    }

    /**
     * 访问销售机会详情页
     * @param id
     * @return
     */
    @GetMapping("/info/{id:\\d+}")
    public String myInfo (@PathVariable Integer id,HttpSession session,Model model) {

        Account account = (Account) session.getAttribute("curr_acc");
        Chance chance = chanceService.findChanceById(id);

        //判断是否存在chance对象
        if(chance == null) {
            throw new NotFoundException("未找到该销售机会数据");
        }
        //判断是否有权访问该chance对象
        if(!chance.getAccountId().equals(account.getId())) {
            throw new ForbiddenException("权限不够哦");
        }

        //传值,① Chance销售机会对象,② 封装一个Customer对象放入chance对象
        model.addAttribute("chance",chance);

        //传值,进度列表集合
        List<String> progressList = chanceService.findAllProgress();
        model.addAttribute("progressList",progressList);

        //传值,record跟进记录的list集合
        List<Record> recordList = recordService.findAllByChanceId(id);
        model.addAttribute("recordList",recordList);

        //传值,关联销售机会Chance id的日程安排Task列表
        List<Task> taskList = taskService.findByAccountIdAndChanceId(account.getId(),id);
        model.addAttribute("taskList",taskList);

        return "sales/sale-info";
    }

    /**
     * 删除销售机会的动作
     * @param id
     * @param session
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/del/{id:\\d+}")
    public String delChance(@PathVariable Integer id,HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Account account = (Account) session.getAttribute("curr_acc");
        Chance chance = chanceService.findChanceById(id);

        //安全校验
        if(chance == null) {
            throw new NotFoundException();
        }
        if(!chance.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        //执行删除
        chanceService.delByChanceId(id);

        return "redirect:/sales/my";
    }

    /**
     * 新增销售机会的记录
     * @return
     */
    @PostMapping("my/new/record")
    public String newRecord(Integer chanceId,String content,HttpSession session) {
        //获取值
        Account account = (Account) session.getAttribute("curr_acc");
        Chance chance = chanceService.findChanceById(chanceId);

        //安全校验
        if(chance == null) {
            throw new NotFoundException();
        }
        if(!chance.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        recordService.saveNewRecord(chanceId,content);

        return "redirect:/sales/info/" + chanceId;
    }

    /**
     * 更新销售机会的进度
     * @return
     */
    @PostMapping("/my/update/progress/{id:\\d+}")
    public String updateProgress(@PathVariable Integer id,String progress,
                                 HttpSession session) {
        //获取值
        Account account = (Account) session.getAttribute("curr_acc");
        Chance chance = chanceService.findChanceById(id);

        //安全校验
        if(chance == null) {
            throw new NotFoundException();
        }
        if(!chance.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        chanceService.updateByProgress(progress,id);

        return "redirect:/sales/info/" + id;

    }

}
