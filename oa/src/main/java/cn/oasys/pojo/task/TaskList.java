package cn.oasys.pojo.task;

import cn.oasys.pojo.user.User;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name="aoa_task_list")
public class TaskList {
    @Id
    @Column(name="task_id")
    private Long taskId;//任务id主键

    @Column(name="type_id")
    private Long typeId;//任务类型（公事，私事）

    @Column(name="publish_time")
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date publishTime;//发布时间

    @Column(name="star_time")
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date starTime;//任务开始时间

    @Column(name="end_time")
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;//任务结束时间

    @Column(name="modify_time")
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date modifyTime;//任务修改时间

    @Column(name="title",nullable=false)
    @NotEmpty(message="主题名字不能为空")
    private String title;//任务主题

    @Column(name="reciverlist")
    private String reciverlist;

    @Column(name="task_push_user_id")
    private Long taskPushUserId;//发布人id

    @Column(name="task_describe")
    private String taskDescribe;//任务描述

    @Column(name="comment")
    private String comment;//任务评价

    @Column(name="is_top")
    private Boolean top=false;//任务是否置顶

    @Column(name="is_cancel")
    private Boolean cancel=false;//是否取消任务

    @Column(name="ticking")
    private String ticking;//任务结束后反馈

    @Column(name="status_id")
    private Integer statusId;//任务状态id

    @Transient
    private String userName;

    @Transient
    @Column(name="type_name")
    private String typeName;

    @Transient
    private String deptName;

    @Transient
    private String statuscolor;

    @Transient
    private String statusname;
}
