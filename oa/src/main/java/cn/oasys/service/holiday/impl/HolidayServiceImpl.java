package cn.oasys.service.holiday.impl;

import cn.oasys.dao.holiday.HolidayMapper;
import cn.oasys.pojo.holiday.Holiday;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.holiday.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("holidayService")
public class HolidayServiceImpl extends BaseServiceImpl<Holiday> implements HolidayService {

    @Autowired
    HolidayMapper holidayMapper;

    @Override
    public Holiday selectHoliday(Long processId) {
        return holidayMapper.selectHoliday(processId);
    }

    @Override
    public int insertholiday(Holiday over) {
        return holidayMapper.insertholiday(over);
    }
}
