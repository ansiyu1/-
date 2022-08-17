package cn.oasys.service.overtime.impl;

import cn.oasys.dao.overtime.OvertimeMapper;
import cn.oasys.pojo.overtime.Overtime;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.overtime.OvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("overtimeService")
public class OvertimeServiceImpl extends BaseServiceImpl<Overtime> implements OvertimeService {
    @Autowired
    OvertimeMapper overtimeMapper;
    @Override
    public Overtime selectOvertime(Long processId) {
        return overtimeMapper.selectOvertime(processId);
    }

    @Override
    public int insertOvertime(Overtime over) {
        return overtimeMapper.insertOvertime(over);
    }
}
