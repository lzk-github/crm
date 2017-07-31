package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.zxing.WriterException;
import com.kaishengit.controller.exception.ForbiddenException;
import com.kaishengit.controller.exception.NotFoundException;
import com.kaishengit.entity.Account;
import com.kaishengit.entity.Chance;
import com.kaishengit.entity.Customer;
import com.kaishengit.entity.Task;
import com.kaishengit.service.AccountService;
import com.kaishengit.service.ChanceService;
import com.kaishengit.service.CustomerService;
import com.kaishengit.service.TaskService;
import com.kaishengit.util.QrCodeUtil;
import com.kaishengit.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 客户管理控制器controller
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ChanceService chanceService;

    /**
     * 自动注入CustomerService接口的实现类
     */
    @Autowired
    private CustomerService customerService;

    /**
     * 自动注入AccountService的实现类
     */
    @Autowired
    private AccountService accountService;

    /**
     * 显示->我的客户<-页面
     * @param session
     * @return
     */
    @GetMapping("/my")
    public String myCustomer(@RequestParam(required = false) String keyword,
                             @RequestParam(required = false,defaultValue = "1") Integer p,
                             HttpSession session,Model model) {

        Account account = (Account) session.getAttribute("curr_acc");

        Map<String,Object> queryItemsMap = Maps.newHashMap();

        keyword = StringUtils.IsoToUtf(keyword);

        queryItemsMap.put("keyword",keyword);
        queryItemsMap.put("accountId",account.getId());
        queryItemsMap.put("pageNo",p);

        PageInfo<Customer> pageInfo = customerService.findCustomersByQueryMap(queryItemsMap);
//        List<Customer> customerList = customerService.getCustomers(account);

        //向前端传(我的客户)列表
        model.addAttribute("pageInfo",pageInfo);
        //向前端回传keyword搜索关键字
        model.addAttribute("keyword",keyword);
        return "customer/customer-my";
    }

    /**
     * 新增客户页面
     */
    @GetMapping("/new")
    public String newCustomer(Model model) {

        //1.查出来客户来源列表并返回
        List<String> sourceList = customerService.getCustomerSources();
        model.addAttribute("sourceList",sourceList);

        //2.查出来所属行业并返回
        List<String> tradeList = customerService.getCustomerTrades();
        model.addAttribute("tradeList",tradeList);

        return "customer/customer-new";
    }

    /**
     * 新增客户的表单提交请求
     * @return 重定向到我的客户页面
     */
    @PostMapping("/new")
    public String newCustomer(HttpSession session, Customer customer, RedirectAttributes redirectAttributes) {
        Account account = (Account) session.getAttribute("curr_acc");
        customerService.saveCustomer(customer,account);
        redirectAttributes.addFlashAttribute("message","新增客户成功!");
        return "redirect:/customer/my";
    }

    /**
     * 访问客户的详情页
     */
    @GetMapping("/my/{id:\\d+}")
    public String accessCustomer(@PathVariable Integer id,
                                 HttpSession session,
                                 Model model) {
        Account account = (Account) session.getAttribute("curr_acc");

        Customer customer = customerService.findById(id);

        //安全校验
        if(customer == null) {
            throw new NotFoundException();
        }

        //判断当前员工是否具有访问客户的权限
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException("权限不足-notice by lzk");
        }

        //往前端传值:员工列表,目的是方便执行转移客户动作
        List<Account> accountList = customerService.findAllAccount();
        model.addAttribute("accountList",accountList);

        //往前端页面传值:当前客户详情页的客户对象
        model.addAttribute("customer",customer);

        //传值,销售机会列表
        List<Chance> chanceList = chanceService.findChanceByCustomerId(id);
        model.addAttribute("chanceList",chanceList);

        //传值,日程安排,与此customer相关的task列表
        List<Task> taskList = taskService.findTasksByAccountIdAndCustomerId(account.getId(),id);
        model.addAttribute("taskList",taskList);

        return "customer/info";
    }

    /**
     * 客户二维码生成
     */
    @GetMapping("/my/qrcode/{id:\\d+}")
    public void qrcode(@PathVariable Integer id, HttpServletResponse response) {
        //设置响应类型
        response.setContentType("image/png");

        //找到对应客户对象
        Customer customer = customerService.findById(id);

        //vcard 格式 https://zxing.appspot.com/generator
        StringBuffer str = new StringBuffer();
        str.append("BEGIN:VCARD\r\n");
        str.append("VERSION:3.0\r\n");
        str.append("N:").append(customer.getCustName()).append("\r\n");
        str.append("TITLE:").append(customer.getJobTitle()).append("\r\n");
        str.append("TEL:").append(customer.getMobile()).append("\r\n");
        str.append("ADR:").append(customer.getAddress()).append("\r\n");
        str.append("END:VCARD\r\n");

        try {
            OutputStream outputStream = response.getOutputStream();
            QrCodeUtil.writeToStream(str.toString(), outputStream, 150, 150);
            outputStream.flush();
            outputStream.close();
        } catch (IOException|WriterException ex) {
            throw new RuntimeException("渲染二维码失败",ex);
        }

    }


    /**
     * 客户信息编辑页面
     */
    @GetMapping("/my/edit/{id:\\d+}")
    public String editCustomer(@PathVariable Integer id ,HttpSession session,Model model) {

        Account account = (Account) session.getAttribute("curr_acc");
        Customer customer = customerService.findById(id);
        if(customer == null) {
            throw new NotFoundException();
        }
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException("访问权限不足!");
        }
        model.addAttribute("customer",customer);
        model.addAttribute("tradeList",customerService.getCustomerTrades());
        model.addAttribute("sourceList", customerService.getCustomerSources());
        return "customer/editCustomer";

    }

    /**
     * 客户编辑页面后将信息更新至数据库
     * @return 返回用户的客户列表展示页面
     */
    @PostMapping("/my/edit/{id:\\d+}")
    public String editCustomer(Customer customer,HttpSession session,RedirectAttributes redirectAttributes) {
        Account account = (Account) session.getAttribute("curr_acc");

        if(customer == null) {
            throw new NotFoundException();
        }
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException("访问权限不足!");
        }

        customerService.updateCustomer(customer);
        redirectAttributes.addFlashAttribute("message","客户信息修改成功!");

        return "redirect:/customer/my/" + customer.getId();
    }

    /**
     * 删除当前用户的客户
     */
    @GetMapping("/my/del/{id:\\d+}")
    public String delById(@PathVariable Integer id,HttpSession session,RedirectAttributes redirectAttributes) {
        Account account = (Account) session.getAttribute("curr_acc");

        Customer customer = customerService.findById(id);

        if(customer == null) {
            throw new NotFoundException();
        }

        //判断当前员工是否具有访问客户的权限
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException("权限不足-notice by lzk");
        }

        customerService.delById(id);
        redirectAttributes.addFlashAttribute("message","删除成功!");

        return "redirect:/customer/my";
    }

    /**
     * 将客户放入公海
     */
    @GetMapping("/my/pullToSea/{id:\\d+}")
    public String pullToSea(@PathVariable Integer id,HttpSession session,RedirectAttributes redirectAttributes) {
        Account account = (Account) session.getAttribute("curr_acc");

        Customer customer = customerService.findById(id);

        //判断客户是否存在
        if(customer == null) {
            throw new NotFoundException();
        }

        //判断当前员工是否具有访问客户的权限
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException("权限不足-notice by lzk");
        }
        //执行将客户放入公海的动作
        customerService.pullToSea(customer,account);

        //重定向时传递提示性信息
        redirectAttributes.addFlashAttribute("message",
                "已经将" + customer.getCustName() + "放入公海");
        return "redirect:/customer/my";
    }

    /**
     * 客户转移
     */
    @GetMapping("/my/{id:\\d+}/transform/{accountId:\\d+}")
    public String transform(@PathVariable Integer id,@PathVariable Integer accountId,
                          HttpSession session,RedirectAttributes redirectAttributes) {

        Account account = (Account) session.getAttribute("curr_acc");

        //判断接收员工是否存在
        Account reciveAccount = accountService.findAccountById(accountId);
        if(reciveAccount == null) {
            throw new NotFoundException("员工不存在");
        }

        //通过客户id查找客户对象
        Customer customer = customerService.findById(id);

        //判断客户是否存在
        if(customer == null) {
            throw new NotFoundException();
        }

        //判断当前员工是否具有访问客户的权限
        if(!customer.getAccountId().equals(account.getId())) {
            throw new ForbiddenException("权限不足-notice by lzk");
        }

        //执行转交动作
        customer.setAccountId(accountId);
        customerService.updateCustomer(customer);
        redirectAttributes.addFlashAttribute("message",
                "将客户:" + customer.getCustName() +
                        "转交给 " + reciveAccount.getUserName() + "成功!");

        return "redirect:/customer/my";
    }

    /**
     * 导出当前员工的客户信息表
     */
    @GetMapping("/my/export")
    public void exportExcel(HttpSession session,HttpServletResponse response) throws IOException {
        //从session对象中获取员工对象,因为拦截器已经校验过account状态,则不再校验account
        Account account = (Account) session.getAttribute("curr_acc");

//        String excelName = account.getUserName().concat("的客户清单.xls");

        //设置响应MIME类型
        response.addHeader("Content-Disposition","attachment;filename=\"customers.xls\"");

        response.setContentType("application/vnd.ms-excel");

        customerService.exportCustomersToExcel(account,response.getOutputStream());

    }

    /**
     * 关联某个客户的任务,接收表单提交内容
     * @return
     */
    @PostMapping("/task/new")
    public String newTaskWithCustomer(Task task ,HttpSession session) {
        //安全校验
        Account account = (Account) session.getAttribute("curr_acc");
        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        //接收表单内容,并提交数据
        Customer customer = customerService.findById(task.getCustomerId());
        taskService.saveNewTask(task,account,null,customer);

        return "redirect:/customer/my/" + customer.getId();
    }


}
