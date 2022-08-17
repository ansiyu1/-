package cn.oasys.dao.holiday;

import cn.oasys.pojo.holiday.Holiday;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface HolidayMapper extends Mapper<Holiday> {
    Holiday selectHoliday(@Param("processId") Long processId);

    int insertholiday(Holiday over);
}
