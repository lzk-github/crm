package com.kaishengit.mapper;

import com.kaishengit.entity.Chance;
import com.kaishengit.entity.ChanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChanceMapper {
    long countByExample(ChanceExample example);

    int deleteByExample(ChanceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Chance record);

    int insertSelective(Chance record);

    List<Chance> selectByExample(ChanceExample example);


    int updateByExampleSelective(@Param("record") Chance record, @Param("example") ChanceExample example);

    int updateByExample(@Param("record") Chance record, @Param("example") ChanceExample example);

    int updateByPrimaryKeySelective(Chance record);

    int updateByPrimaryKey(Chance record);

    List<Chance> selectByAccountId(@Param("accountId") Integer accountId);

    Chance selectByPrimaryKey(@Param("id") Integer id);

    List<Chance> selectByCustomerId(Integer id);
}