package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kaishengit.controller.exception.ExportExcelException;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.entity.Account;
import com.kaishengit.entity.Customer;
import com.kaishengit.service.CustomerService;
import com.kaishengit.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 公海客户相关的业务控制器
 * @author lzk
 * @since 2017-7-21
 */
@Controller
@RequestMapping("/customer")
public class PublicSeaCustomerController {

    @Autowired
    private CustomerService customerService;


    /**
     * 访问公海客户列表页面
     */
    @GetMapping("/sea")
    public String seaCustomer(@RequestParam(required = false) String keyword,
                              @RequestParam(required = false,defaultValue = "1") Integer p,
                              HttpSession session, Model model) {

        Map<String,Object> queryMap = Maps.newHashMap();
        keyword = StringUtils.IsoToUtf(keyword);

        queryMap.put("keyword",keyword);
        queryMap.put("pageNo",p);
        queryMap.put("accountId",null);

        //往前端页面传分页内容
        PageInfo<Customer> pageInfo = customerService.findPublicSeaCustomersByQueryMap(queryMap);
        model.addAttribute("pageInfo",pageInfo);


        //查出来客户来源列表并返回
        List<String> sourceList = customerService.getCustomerSources();
        model.addAttribute("sourceList",sourceList);

        //查出来所属行业并返回
        List<String> tradeList = customerService.getCustomerTrades();
        model.addAttribute("tradeList",tradeList);

        //回显搜索关键字
        model.addAttribute("keyword",keyword);

        return "/customer/customer-sea";
    }

    /**
     * 新增公海客户的表单提交
     */
    @PostMapping("/sea/new")
    @ResponseBody
    public AjaxResult addSeaCustomer(Customer customer) {

        customerService.savePublicSeaCustomer(customer);

        return AjaxResult.getSuccessInstance();
    }

    /**
     * 公海客户excel列表导出
     */
    @GetMapping("/sea/export")
    public void exportPublicSeaCustomers(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=".concat("publicSeaCustomers.xls"));
        try {
            customerService.exportCustomersToExcel(null,response.getOutputStream());
        } catch (IOException e) {
            throw new ExportExcelException("导出公海客户excel发生错误");
        }
    }


    /**
     * 访问某个公海客户的详细信息页面
     */
    @GetMapping("/sea/{id:\\d+}")
    public String seaInfo(@PathVariable Integer id,Model model) {
        Customer customer = customerService.findById(id);

        model.addAttribute("customer",customer);

        return "/customer/sea-info";
    }

    /**
     * 进入编辑公海客户信息页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/sea/edit/{id:\\d+}")
    public String editSea(@PathVariable Integer id,Model model) {
        Customer customer = customerService.findById(id);

        //返回客户行业列表
        List<String> tradeList = customerService.getCustomerTrades();
        model.addAttribute("tradeList",tradeList);

        //返回客户来源列表
        List<String> sourceList = customerService.getCustomerSources();
        model.addAttribute("sourceList",sourceList);

        //返回客户对象
        model.addAttribute("customer",customer);

        return "/customer/editSea";
    }

    /**
     * 保存编辑公海客户信息
     * @return 重定向到公海客户页面
     */
    @PostMapping("/sea/edit/{id:\\d+}")
    public String editSea(Customer customer, RedirectAttributes redirectAttributes) {

        customerService.updateCustomer(customer);
        redirectAttributes.addFlashAttribute("message","编辑公海客户信息完成!");
        return "redirect:/customer/sea";
    }

    /**
     * 将公海客户转为我的客户
     */
    @GetMapping("/sea/transform/{id:\\d+}")
    public String transformToMe(@PathVariable Integer id,HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Account account = (Account) session.getAttribute("curr_acc");
        Customer customer = customerService.findById(id);

        customerService.transformToMyCustomer(account,id);

        redirectAttributes.addFlashAttribute("message",
                "已经将公海客户:" + customer.getCustName() + "转为我的客户");

        return "redirect:/customer/my";
    }

    /**
     * 根据公海客户Id删除客户
     * @param id 公海客户id
     * @param redirectAttributes 提示信息传递
     */
    @GetMapping("/sea/del/{id:\\d+}")
    public String delSeaById(@PathVariable Integer id,RedirectAttributes redirectAttributes) {

        Customer customer = customerService.findById(id);
        customerService.delById(id);

        redirectAttributes.addFlashAttribute("message","已经删除公海客户:" + customer.getCustName());

        return "redirect:/customer/sea";
    }
}
