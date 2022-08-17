package cn.oasys.service.task;

import cn.oasys.pojo.task.TaskLogger;
import cn.oasys.service.BaseService;
import java.util.List;

public interface TaskLoggerService extends BaseService<TaskLogger> {


    int xinzeng(TaskLogger logger);

    int updateStatusId(Long loggerId);

    List<TaskLogger> findByTaskId(Long taskId);
}
