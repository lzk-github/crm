package com.kaishengit.controller;

import com.kaishengit.dto.AjaxResult;
import com.kaishengit.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    private CustomerService customerService;

    /**
     * 静态图表演示页面
     * @return
     */
    @GetMapping("/static")
    public String staticChart() {
        return "chart/static-charts";
    }

    /**
     * 客户星级分析图表,异步发送数据
     */
    @PostMapping("/customer/analyze.json")
    @ResponseBody
    public AjaxResult customerAnalyze() {
        List<Map<String,Object>> levelData = customerService.analyzeLevelData();
        return AjaxResult.getSuccessInstance(levelData);
    }

    @GetMapping("/customer")
    public String showCostomerData() {
        return "chart/customer-analyze";
    }

}
