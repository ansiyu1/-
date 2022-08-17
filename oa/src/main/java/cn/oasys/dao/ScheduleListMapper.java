package cn.oasys.dao;

import cn.oasys.pojo.ScheduleList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ScheduleListMapper extends Mapper<ScheduleList> {
    @Select("SELECT*FROM aoa_schedule_list WHERE aoa_schedule_list.user_id =1")
    List<ScheduleList> findstart(@Param("userId") Long userId);
}
