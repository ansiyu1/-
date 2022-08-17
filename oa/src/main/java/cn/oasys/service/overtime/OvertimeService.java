package cn.oasys.service.overtime;

import cn.oasys.pojo.overtime.Overtime;
import cn.oasys.service.BaseService;

public interface OvertimeService extends BaseService<Overtime> {
    Overtime selectOvertime(Long processId);

    int insertOvertime(Overtime over);
}
