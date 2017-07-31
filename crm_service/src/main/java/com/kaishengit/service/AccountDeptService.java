package com.kaishengit.service;

import com.kaishengit.entity.AccountDeptKey;

import java.util.List;

/**
 * Created by lzk on 2017/7/18.
 */
public interface AccountDeptService {
    void delByAccountId(Integer id);

    List<AccountDeptKey> findByDeptId(Integer delDeptId);
}
