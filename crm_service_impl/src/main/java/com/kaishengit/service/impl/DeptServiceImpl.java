package com.kaishengit.service.impl;

import com.kaishengit.entity.Dept;
import com.kaishengit.entity.DeptExample;
import com.kaishengit.mapper.DeptMapper;
import com.kaishengit.service.DeptService;
import com.kaishengit.weixin.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lzk on 2017/7/17.
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private WeixinUtil weixinUtil;

    /**
     * 查找所有部门
     * @return
     */
    @Override
    public List<Dept> findDepts() {
        return deptMapper.selectByExample(new DeptExample());
    }

    /**
     * 新增部门,对接企业微信新增部门
     * @param dept
     */
    @Override
    @Transactional
    public void save(Dept dept) {
        //1.数据库中新增加部门
        deptMapper.insert(dept);
        //2.企业微信新增加部门
        weixinUtil.createDept(dept.getId(),dept.getpId(),dept.getDeptName());
    }

    /**
     * 根据部门id删除部门,并删除企业微信中的部门
     * @param delDeptId
     */
    @Override
    public void delById(Integer delDeptId) {
        //1.删除数据库中的对应部门
        deptMapper.deleteByPrimaryKey(delDeptId);
        //2.删除企业微信中的部门
        weixinUtil.deleteByDeptId(delDeptId);
    }
}
