package com.kaishengit.controller;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.dto.AjaxResult;
import com.kaishengit.dto.DataTablesResult;
import com.kaishengit.dto.ZTreeNode;
import com.kaishengit.entity.Account;
import com.kaishengit.entity.AccountDeptKey;
import com.kaishengit.entity.Dept;
import com.kaishengit.service.AccountDeptService;
import com.kaishengit.service.AccountService;
import com.kaishengit.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by lzk on 2017/7/17.
 */
@Controller
@RequestMapping("/manage/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private AccountDeptService accountDeptService;

    /**
     * 删除员工
     * @Param 要删除的员工 ID
     */
    @PostMapping("/del/{id:\\d+}")
    @ResponseBody
    public AjaxResult delById(@PathVariable Integer id) {
        //1. 删除员工表内容
        accountService.delById(id);
        //2. 删除员工-部门关系表
        accountDeptService.delByAccountId(id);
        return AjaxResult.getSuccessInstance();
    }

    /**
     * 员工信息列表页面
     * @return manage/accounts.jsp页面
     */
    @GetMapping
    public String accountShow() {
        return "manage/accounts";
    }

    /**
     * 员工列表 dataTables 异步加载
      * @param deptId
     * @param request
     * @return
     */
    @GetMapping("/load.json")
    @ResponseBody
    public DataTablesResult<Account> loadAccount(Integer deptId, HttpServletRequest request) {

        String draw = request.getParameter("draw");

        //获取总员工数量
        Long total = accountService.countAll();
        //获取过滤后的员工数量
        Long filteredTotal = accountService.countByFilter(deptId);

        /*获取总员工list集合
        List<Account> accList = accountService.showAccounts();*/

        //获取过滤后的员工集合
        List<Account> accountList = accountService.findByFilter(deptId);

        //把数据封装成dataTables需要的对象传到前端的dataTables
        return new DataTablesResult<Account>(draw,total,filteredTotal,accountList);
    }

    /**
     * 加载部门的ZTree树
     * @return
     */
    @PostMapping("depts.json")
    @ResponseBody
    public List<ZTreeNode> getTree() {
        List<Dept> deptList = deptService.findDepts();
        List<ZTreeNode> zTreeNodeList = Lists.newArrayList(Collections2.transform(deptList, new Function<Dept, ZTreeNode>() {
            @Nullable
            @Override
            public ZTreeNode apply(@Nullable Dept input) {
                ZTreeNode zTreeNode = new ZTreeNode();
                zTreeNode.setId(input.getId());
                zTreeNode.setName(input.getDeptName());
                zTreeNode.setpId(input.getpId());
                return zTreeNode;
            }
        }));

        return zTreeNodeList;
    }


    /**
     * 新增部门
     */
    @PostMapping("/dept/new")
    @ResponseBody
    public AjaxResult addDept(Dept dept) {
        deptService.save(dept);
        return AjaxResult.getSuccessInstance();
    }

    /*@GetMapping("/list")
    @ResponseBody
    public List<Account> findAccs() {
        List<Account> list = accountService.showAccounts();
        return list;
    }*/

    /**
     * 新增员工
     *
     */
    @PostMapping("/new")
    @ResponseBody
    public AjaxResult addAcc(Account account,Integer[] deptId) {
        accountService.saveAccount(account,deptId);
        return AjaxResult.getSuccessInstance();
    }

    /**
     * 删除部门
     */
    @PostMapping("/delDept/{delDeptId:\\d+}")
    @ResponseBody
    public AjaxResult delDept(@PathVariable Integer delDeptId, HttpSession session) {
        List<AccountDeptKey> accountDeptKeys = accountDeptService.findByDeptId(delDeptId);
        AjaxResult ajaxResult = new AjaxResult();

        //如果该部门下还有员工,则不能删除该部门
        if( accountDeptKeys != null && accountDeptKeys.size() != 0) {
            ajaxResult.setState("error");
            ajaxResult.setMessage("该部门还有员工,不能直接删除");
        } else {
            //执行删除部门操作
            deptService.delById(delDeptId);
            ajaxResult.setState("success");
        }

        return ajaxResult;
    }


}
