package cn.oasys.service.schedue.impl;

import cn.oasys.dao.ScheduleListMapper;
import cn.oasys.pojo.ScheduleList;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.schedue.ScheduleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("scheduleListService")
public class ScheduleListServiceImpl extends BaseServiceImpl<ScheduleList> implements ScheduleListService {
    @Autowired
    ScheduleListMapper scheduleListMapper;
    @Override
    public List<ScheduleList> findstart(Long userId) {
        return scheduleListMapper.findstart(userId);
    }
}
