package com.kaishengit.mapper;

import com.kaishengit.entity.Record;

import java.util.List;

public interface RecordMapper {
    List<Record> findByChanceId(Integer id);

    void delByChanceId(Integer id);

    void save(Record record);
}
