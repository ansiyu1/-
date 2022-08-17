package cn.oasys.service.holiday;

import cn.oasys.pojo.holiday.Holiday;
import cn.oasys.service.BaseService;

public interface HolidayService extends BaseService<Holiday> {
    Holiday selectHoliday(Long processId);

    int insertholiday(Holiday over);
}
