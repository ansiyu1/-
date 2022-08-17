package cn.oasys.dao.task;

import cn.oasys.pojo.task.TaskLogger;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TaskLoggerMapper extends Mapper<TaskLogger> {
    @Insert("INSERT INTO aoa_task_logger (create_time,logger_ticking,task_id,username,logger_statusid)VALUES(#{createTime},#{loggerTicking},#{username},#{loggerStatusid});")
    int xinzeng(TaskLogger logger);
  @Update("UPDATE aoa_task_logger SET logger_statusid='5' WHERE logger_id=#{loggerId};")
  int updateStatusId(@Param("loggerId") Long loggerId);
    @Select("SELECT * FROM aoa_task_logger WHERE task_id=#{taskId}")
    List<TaskLogger> findByTaskId(@Param("taskId") Long taskId);



}
