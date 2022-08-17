package cn.oasys.service.task.impl;

import cn.oasys.dao.task.TaskListMapper;
import cn.oasys.pojo.task.TaskList;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.task.TaskListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("taskListService")
public class TaskListServiceImpl extends BaseServiceImpl<TaskList> implements TaskListService {
    @Autowired
    TaskListMapper taskListMapper;
    @Override
    public int taskCount(Long userId) {
        return taskListMapper.taskCount(userId);
    }

    @Override
    public List<TaskList> select(TaskList taskList) {
        return taskListMapper.select(taskList);
    }

    @Override
    public List<TaskList> selectName(String title) {
        return taskListMapper.selectName(title);
    }

    @Override
    public int save() {
        return taskListMapper.save();
    }

    @Override
    public TaskList selectOn(Long taskId) {
        return taskListMapper.selectOn(taskId);
    }


    @Override
    public PageInfo<TaskList> taskListPage(int page, int size) {
        PageHelper.startPage(page,size);
        return new PageInfo<TaskList>(taskListMapper.selectTask());
    }

    @Override
    public PageInfo<TaskList> selectManageTable(String val,int page, int size) {
        PageHelper.startPage(page,size);
        return new PageInfo<TaskList>(taskListMapper.selectManageTable(val));
    }

    @Override
    public PageInfo<TaskList> selectTitle(String title, int page, int size) {
        PageHelper.startPage(page,size);
        return new PageInfo<TaskList>(taskListMapper.selectTitle(title));
    }


}
