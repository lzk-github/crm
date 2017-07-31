package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Account;
import com.kaishengit.entity.Chance;
import com.kaishengit.entity.Record;
import com.kaishengit.mapper.ChanceMapper;
import com.kaishengit.mapper.RecordMapper;
import com.kaishengit.service.ChanceService;
import com.kaishengit.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ChanceServiceImpl implements ChanceService {

    /**
     * 从配置文件中自动注入销售机会进度选项列表
     */
    @Value("#{'${chance.progress}'.split(',')}")
    private List<String> progressList;

    @Autowired
    private ChanceMapper chanceMapper;

    @Autowired
    private RecordMapper recordMapper;

    /**
     * 根据员工查找其对应的销售机会
     * @param account
     * @return
     */
    @Override
    @Transactional
    public PageInfo<Chance> findChancePage(Account account,Integer p) {

        Integer accountId = null;

        //如果传入的account为空,则查找的是公共销售机会
        if(account == null) {
            accountId = null;
        } else {
            accountId = account.getId();
        }

        PageHelper.startPage(p,5);

        List<Chance> chanceList = chanceMapper.selectByAccountId(accountId);

        return new PageInfo<>(chanceList);
    }

    /**
     * 查找所有进度选项列表
     * @return
     */
    @Override
    public List<String> findAllProgress() {
        return progressList;
    }

    /**
     * 新增销售机会
     * @param account 当前员工对象
     * @param chance 销售机会对象
     */
    @Override
    public void saveChance(Account account, Chance chance) {
        //将员工id存入chance对象
        Integer accountId = account.getId();

        chance.setAccountId(accountId);
        chance.setCreateTime(new Date());
        chance.setLastContactTime(new Date());

        chanceMapper.insert(chance);
    }

    /**
     * 根据chance id查找销售机会对象
     * @param id
     * @return
     */
    @Override
    public Chance findChanceById(Integer id) {
        return chanceMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新Chance对象
     * @param chance
     */
    @Override
    public void updateChance(Chance chance) {
        chanceMapper.updateByPrimaryKeySelective(chance);
    }

    /**
     * 根据Chance的id删除数据库中的记录
     * @param id Chance对象id
     */
    @Override
    @Transactional
    public void delByChanceId(Integer id) {
        //需求,根据id删除记录
        //1.根据id删除record表中的记录
        //2.删除销售机会表中的记录

        recordMapper.delByChanceId(id);
        chanceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新销售机会的进度
     * @param progress
     */
    @Override
    @Transactional
    public void updateByProgress(String progress,Integer chanceId) {
        //1, 更新chance表的当前进度
        Chance chance = chanceMapper.selectByPrimaryKey(chanceId);
        chance.setCurrProgress(progress);
        chanceMapper.updateByPrimaryKeySelective(chance);
        //2, 更新Record表,添加新新的记录
        Record record = new Record();
        record.setCreateTime(new Date());
        record.setChanceId(chanceId);
        record.setContent("将当前进度修改为：[ " + progress + " ]");
        recordMapper.save(record);

    }

    /**
     * 根据客户id查找销售机会列表
     * @param id
     * @return
     */
    @Override
    public List<Chance> findChanceByCustomerId(Integer id) {
        return chanceMapper.selectByCustomerId(id);
    }
}
