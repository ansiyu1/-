package cn.oasys.service.task.impl;

import cn.oasys.dao.task.TaskLoggerMapper;
import cn.oasys.pojo.task.TaskLogger;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.task.TaskLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("taskLoggerService")
public class TaskLoggerServiceImpl extends BaseServiceImpl<TaskLogger> implements TaskLoggerService {
    @Autowired
    TaskLoggerMapper taskLoggerMapper;

    @Override
    public int xinzeng(TaskLogger logger) {
        return taskLoggerMapper.xinzeng(logger);
    }

    @Override
    public int updateStatusId(Long loggerId) {
        return taskLoggerMapper.updateStatusId(loggerId);
    }

    @Override
    public List<TaskLogger> findByTaskId(Long taskId) {
        return taskLoggerMapper.findByTaskId(taskId);
    }
}
