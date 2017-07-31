package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Account;
import com.kaishengit.entity.Chance;

import java.util.List;

public interface ChanceService {
    PageInfo<Chance> findChancePage(Account account,Integer p);

    List<String> findAllProgress();

    void saveChance(Account account, Chance chance);

    Chance findChanceById(Integer id);

    void updateChance(Chance chance);

    void delByChanceId(Integer id);

    void updateByProgress(String progress,Integer id);

    List<Chance> findChanceByCustomerId(Integer id);
}
