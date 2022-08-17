package cn.oasys.service.schedue;

import cn.oasys.pojo.ScheduleList;
import cn.oasys.service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleListService extends BaseService<ScheduleList> {
    List<ScheduleList> findstart(Long userId);
}
