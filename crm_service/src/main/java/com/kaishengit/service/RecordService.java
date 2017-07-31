package com.kaishengit.service;

import com.kaishengit.entity.Record;

import java.util.List;

public interface RecordService {
    List<Record> findAllByChanceId(Integer id);

    void saveNewRecord(Integer chanceId, String content);
}
