package com.kaishengit.service;

import com.kaishengit.entity.Dept;

import java.util.List;

/**
 * Created by lzk on 2017/7/17.
 */
public interface DeptService {
    List<Dept> findDepts();

    void save(Dept dept);

    void delById(Integer delDeptId);
}
