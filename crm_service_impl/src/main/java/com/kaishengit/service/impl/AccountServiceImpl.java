package com.kaishengit.service.impl;

import com.kaishengit.entity.Account;
import com.kaishengit.entity.AccountDeptKey;
import com.kaishengit.entity.AccountExample;
import com.kaishengit.entity.Dept;
import com.kaishengit.exception.AuthenticationException;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.AccountDeptMapper;
import com.kaishengit.mapper.AccountMapper;
import com.kaishengit.mapper.DeptMapper;
import com.kaishengit.service.AccountService;
import com.kaishengit.weixin.util.WeixinUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by lzk on 2017/7/17.
 */
@Service
public class AccountServiceImpl implements AccountService {

    /**
     * 自动注入同步企业微信的工具类
     */
    @Autowired
    private WeixinUtil weixinUtil;

    /**
     * 获取配置文件中的<盐>
     */
    @Value("${password.salt}")
    private String salt;

    /**
     * 自动注入AccountMapper接口的实现类
     */
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 自动注入AccountDeptMapper接口的实现类
     */
    @Autowired
    private AccountDeptMapper accountDeptMapper;

    /**
     * 显示所有员工,包括员工对应的部门
     * @return
     */
    @Override
    public List<Account> showAccounts() {

        return accountMapper.findAllWithDept();
    }


    /**
     * 保存新账户至数据库，同时同步到企业微信
     * @param account 账户
     * @param depts 账户所在部门
     */
    @Transactional
    @Override
    public void saveAccount(Account account, Integer[] depts) {

        //保存账户信息至account数据库表
        account.setCreateTime(new Date());
        account.setPassword(DigestUtils.md5Hex(salt + account.getPassword()));
        accountMapper.insert(account);

        //保存账户部门的关联关系表
        for(Integer dept : depts) {
            AccountDeptKey accountDeptKey = new AccountDeptKey();
            accountDeptKey.setDeptId(dept);
            accountDeptKey.setAccountId(account.getId());
            accountDeptMapper.insert(accountDeptKey);
        }

        //将新增员工同步到企业微信
        weixinUtil.addAccount(account.getId(),account.getUserName(),depts,account.getMobile());

    }

    /**
     * 获取总员工数量
     * @return long类型员工数量
     */
    @Override
    public Long countAll() {
        return accountMapper.countByExample(new AccountExample());
    }

    /**
     * 查找对应部门有多少员工
     * @param deptId 部门id
     * @return 员工数量
     */
    @Override
    public Long countByFilter(Integer deptId) {
        return accountMapper.countByDeptId(deptId);
    }

    /**
     * 根据部门id查找员工列表
     * @param deptId
     * @return 员工集合
     */
    @Override
    public List<Account> findByFilter(Integer deptId) {
        if(new Integer("1004").equals(deptId)) {
            deptId = null;
        }
        return accountMapper.findByDeptId(deptId);
    }

    /**
     * 根据员工id删除员工,同步到企业微信
     * @param id
     */
    @Override
    @Transactional
    public void delById(Integer id) {
        //删除员工表中的内容
        accountMapper.deleteByPrimaryKey(id);
        //删除企业微信中的员工
        weixinUtil.deleteAccount(id);
    }

    /**
     * 判断用户登录时,输入信息是否正确
     * @param mobile 手机号
     * @param password 密码
     */
    @Override
    public Account login(String mobile, String password) {
        Account acc = accountMapper.selectByMobile(mobile);
        password = DigestUtils.md5Hex(salt + password);
        if(acc != null && acc.getPassword().equals(password)) {
            return acc;
        } else {
            throw new AuthenticationException("账号或密码错误!");
        }
    }

    /**
     * 修改用户密码
     * @param account 当前登录账户
     * @param oldPassword 原始密码
     * @param password 新密码
     * @throws ServiceException 当原始密码错误,抛出此异常
     */
    @Override
    @Transactional
    public void changePassword(Account account, String oldPassword, String password) throws ServiceException {
        //旧密码加盐
        oldPassword = DigestUtils.md5Hex(salt + oldPassword);

        if(account.getPassword().equals(oldPassword)) {

            //新密码加盐
            password = DigestUtils.md5Hex(salt + password);

            account.setPassword(password);
            accountMapper.updateByPrimaryKeySelective(account);
        } else {
            throw new ServiceException("原始密码错误!");
        }
    }

    /**
     * 根据员工id查找员工信息
     * @param accountId
     * @return 员工对象
     */
    @Override
    public Account findAccountById(Integer accountId) {
        return accountMapper.selectByPrimaryKey(accountId);
    }


}
