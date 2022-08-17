package cn.oasys.dao.plan;

import cn.oasys.pojo.PlanList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PlanListMapper extends Mapper<PlanList> {
    @Select("SELECT * from aoa_plan_list p where p.plan_user_id=#{userId} ORDER BY p.create_time DESC,p.end_time DESC LIMIT 0,2")
    List<PlanList> planLimit(@Param("userId") Long userId);
}
