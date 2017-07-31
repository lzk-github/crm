package com.kaishengit.mapper;

import com.kaishengit.entity.Task;
import com.kaishengit.entity.TaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskMapper {
    long countByExample(TaskExample example);

    int deleteByExample(TaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    int insertSelective(Task record);

    List<Task> selectByExample(TaskExample example);

    Task selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Task record, @Param("example") TaskExample example);

    int updateByExample(@Param("record") Task record, @Param("example") TaskExample example);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    List<Task> findByAccountId(@Param("id") Integer id);

    List<Task> selectByAccountIdAndCustomerId(@Param("accountId") Integer accountId,
                                              @Param("customerId") Integer customerId);

    List<Task> selectByAccountIdAndChanceId(@Param("accountId") Integer accountId,
                                            @Param("chanceId") Integer chanceId);

    Task selectByTaskId(Integer taskId);
}