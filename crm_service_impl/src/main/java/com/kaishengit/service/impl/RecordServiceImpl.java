package com.kaishengit.service.impl;

import com.kaishengit.entity.Chance;
import com.kaishengit.entity.Customer;
import com.kaishengit.entity.Record;
import com.kaishengit.mapper.ChanceMapper;
import com.kaishengit.mapper.CustomerMapper;
import com.kaishengit.mapper.RecordMapper;
import com.kaishengit.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ChanceMapper chanceMapper;

    @Autowired
    private RecordMapper recordMapper;

    /**
     * 根据chanceId查找对应的Record集合
     * @param id
     * @return Record集合
     */
    @Override
    public List<Record> findAllByChanceId(Integer id) {
        return recordMapper.findByChanceId(id);
    }

    /**
     * 新增Record跟进记录
     * @param chanceId
     * @param content
     */
    @Override
    @Transactional
    public void saveNewRecord(Integer chanceId, String content) {
        //1.新增record表的记录
        Record record = new Record();
        record.setContent(content);
        record.setChanceId(chanceId);
        record.setCreateTime(new Date());
        recordMapper.save(record);

        //2.chance表的跟进时间变化
        Chance chance = chanceMapper.selectByPrimaryKey(chanceId);
        chance.setLastContactTime(new Date());
        chanceMapper.updateByPrimaryKeySelective(chance);

        //3.customer表的跟进时间变化
        Customer customer = customerMapper.selectByPrimaryKey(chance.getCustomerId());
        customer.setLastContactTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);

    }
}
