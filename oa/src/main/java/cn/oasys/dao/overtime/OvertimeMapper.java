package cn.oasys.dao.overtime;

import cn.oasys.pojo.overtime.Overtime;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface OvertimeMapper extends Mapper<Overtime> {
    Overtime selectOvertime(@Param("processId") Long processId);

    int insertOvertime(Overtime over);
}
