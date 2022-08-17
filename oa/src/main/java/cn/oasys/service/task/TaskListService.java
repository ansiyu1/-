package cn.oasys.service.task;

import cn.oasys.pojo.TypeList;
import cn.oasys.pojo.task.TaskList;
import cn.oasys.service.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface TaskListService extends BaseService<TaskList> {
    int taskCount(Long userId);


    List<TaskList> select(TaskList t);

    List<TaskList> selectName(String title);

    int save();

    TaskList selectOn(Long taskId);

    int update(TaskList tasklist);


    PageInfo<TaskList> taskListPage( int page, int size);

    PageInfo<TaskList> selectManageTable(String val,int page, int size);

    PageInfo<TaskList> selectTitle(String title, int page, int size);
}
