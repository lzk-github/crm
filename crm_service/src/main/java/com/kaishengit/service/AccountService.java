package com.kaishengit.service;

import com.kaishengit.entity.Account;
import com.kaishengit.entity.Dept;

import java.util.List;

/**
 * Created by lzk on 2017/7/17.
 */
public interface AccountService {

    List<Account> showAccounts();

    void saveAccount(Account account, Integer[] depts);

    Long countAll();

    Long countByFilter(Integer deptId);

    List<Account> findByFilter(Integer deptId);

    void delById(Integer id);

    Account login(String mobile, String password);

    void changePassword(Account account, String oldPassword, String password);

    Account findAccountById(Integer accountId);
}
