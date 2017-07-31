package com.kaishengit.controller;

import com.kaishengit.dto.AjaxResult;
import com.kaishengit.entity.Account;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 处理及响应用户登录相关的请求
 * Created by lzk on 2017/7/19.
 */
@Controller
public class LoginController {

    /**
     * 自动注入用户业务处理层对象AccountService
     */
    @Autowired
    private AccountService accountService;

    /**
     * 用户登录界面显示
     * @return 用户登录login.jsp
     */
    @GetMapping("/")
    public String login() {

        return "login";
    }

    /**
     * 用户登录动作的处理
     * @param mobile 用户输入的手机号
     * @param password 用户输入的密码
     */
    @PostMapping("/")
    @ResponseBody
    public AjaxResult login(String mobile, String password, HttpSession session) {
        try {
            Account account = accountService.login(mobile,password);
            session.setAttribute("curr_acc",account);
            return AjaxResult.getSuccessInstance();
        } catch (ServiceException e) {
            return AjaxResult.getErrorInstance(e.getMessage());
        }
    }

    /**
     * 显示主页
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * 安全退出
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 进入个人设置页面
     */
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    /**
     * 对个人设置页面提交的数据进行处理
     */
    @PostMapping("/profile")
    @ResponseBody
    public AjaxResult profile(String oldPassword,String password,HttpSession session) {
        try {
            Account account = (Account) session.getAttribute("curr_acc");
            accountService.changePassword(account,oldPassword,password);
            session.invalidate();
            return AjaxResult.getSuccessInstance();

        } catch (ServiceException e) {
            return AjaxResult.getErrorInstance(e.getMessage());
        }
    }
}
