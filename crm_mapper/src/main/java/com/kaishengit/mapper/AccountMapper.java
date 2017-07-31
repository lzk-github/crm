package com.kaishengit.mapper;

import com.kaishengit.entity.Account;
import com.kaishengit.entity.AccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    List<Account> findAllWithDept();

    Long countByDeptId(@Param("deptId") Integer deptId);

    List<Account> findByDeptId(@Param("deptId") Integer deptId);

    Account selectByMobile(String mobile);
}