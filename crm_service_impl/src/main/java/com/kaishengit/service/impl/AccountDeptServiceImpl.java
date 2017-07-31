package com.kaishengit.service.impl;

import com.kaishengit.entity.AccountDeptExample;
import com.kaishengit.entity.AccountDeptKey;
import com.kaishengit.mapper.AccountDeptMapper;
import com.kaishengit.service.AccountDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lzk on 2017/7/18.
 */
@Service
public class AccountDeptServiceImpl implements AccountDeptService {

    @Autowired
    private AccountDeptMapper accountDeptMapper;

    /**
     * 根据员工id删除关系表中数据
     * @param id 员工id
     */
    @Override
    public void delByAccountId(Integer id) {
        AccountDeptExample accountDeptExample = new AccountDeptExample();
        accountDeptExample.createCriteria().andAccountIdEqualTo(id);
        accountDeptMapper.deleteByExample(accountDeptExample);
    }

    /**
     * 根据部门id查找关系表对应数据
     * @param deptId 部门id
     * @return  关系表中该部门对应的关系表数据集合
     */
    @Override
    public List<AccountDeptKey> findByDeptId(Integer deptId) {
        AccountDeptExample accountDeptExample = new AccountDeptExample();
        accountDeptExample.createCriteria().andDeptIdEqualTo(deptId);
        return accountDeptMapper.selectByExample(accountDeptExample);
    }
}
