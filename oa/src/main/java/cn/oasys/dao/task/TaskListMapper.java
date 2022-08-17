package cn.oasys.dao.task;

import cn.oasys.pojo.task.TaskList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TaskListMapper extends Mapper<TaskList> {
    @Select("SELECT COUNT(*) from aoa_task_list where aoa_task_list.status_id=7 and aoa_task_list.task_push_user_id=#{userId}")
    int taskCount(@Param("userId") Long userId);

    @Select("SELECT * FROM aoa_task_list INNER JOIN aoa_type_list INNER JOIN aoa_user INNER JOIN aoa_dept INNER JOIN aoa_status_list WHERE aoa_task_list.type_id=aoa_type_list.type_id AND aoa_type_list.type_id IN (3,4) AND aoa_task_list.task_push_user_id=aoa_user.user_id AND aoa_user.user_id='1'  AND aoa_dept.dept_id='1' AND aoa_task_list.status_id=aoa_status_list.status_id;  ")
    List<TaskList> select(TaskList t);

    @Select("SELECT * FROM aoa_task_list WHERE title=#{title}")
    List<TaskList> selectName(@Param("title") String title);

    @Insert("INSERT INTO aoa_task_list(aoa_task_list.modify_time,aoa_task_list.publish_time,aoa_task_list.reciverlist,aoa_task_list.status_id,aoa_task_list.title,aoa_task_list.type_id,aoa_task_list.task_describe) VALUES (#{modifyTime},#{publishTime},#{reciverlist},#{statusId},#{title},#{typeId},#{taskDescribe})")
    int save();

    @Select("SELECT * FROM aoa_task_list WHERE task_id=#{taskId}")
    TaskList selectOn(Long taskId);

    @Select("SELECT * FROM aoa_task_list INNER JOIN aoa_type_list INNER JOIN aoa_user INNER JOIN aoa_dept INNER JOIN aoa_status_list WHERE aoa_task_list.type_id=aoa_type_list.type_id AND aoa_type_list.type_id IN (3,4) AND aoa_task_list.task_push_user_id=aoa_user.user_id AND aoa_user.user_id='1'  AND aoa_dept.dept_id='1' AND aoa_task_list.status_id=aoa_status_list.status_id")
    List<TaskList> selectTask();



    @Update("UPDATE aoa_task_list SET `comment`=#{comment},end_time=#{endTime},is_cancel=#{cancel},is_top=#{top},modify_time=#{modifyTime},publish_time=#{publishTime},star_time=#{starTime},status_id=#{statusId},task_describe=#{taskDescribe},title=#{title},type_id=#{typeId},task_push_user_id=#{taskPushUserId},reciverlist=#{reciverlist} WHERE task_id=#{taskId}")
    int update(TaskList tasklist);

    @Select("SELECT * FROM aoa_task_list INNER JOIN aoa_type_list INNER JOIN aoa_user INNER JOIN aoa_dept INNER JOIN aoa_status_list WHERE aoa_task_list.type_id=aoa_type_list.type_id AND aoa_type_list.type_id IN (3,4) AND aoa_task_list.task_push_user_id=aoa_user.user_id AND aoa_user.user_id='1'  AND aoa_dept.dept_id='1' AND aoa_task_list.status_id=aoa_status_list.status_id AND aoa_task_list.title like '%${val}%'")
    List<TaskList> selectManageTable(@Param("val") String val);

    @Select("SELECT * FROM aoa_task_list INNER JOIN aoa_type_list INNER JOIN aoa_user INNER JOIN aoa_dept INNER JOIN aoa_status_list WHERE aoa_task_list.type_id=aoa_type_list.type_id AND aoa_type_list.type_id IN (3,4) AND aoa_task_list.task_push_user_id=aoa_user.user_id AND aoa_user.user_id='1'  AND aoa_dept.dept_id='1' AND aoa_task_list.status_id=aoa_status_list.status_id AND aoa_task_list.title like '%${title}%'")
    List<TaskList> selectTitle(@Param("title") String title);

}
