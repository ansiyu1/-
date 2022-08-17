package cn.oasys.pojo.task;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name="aoa_task_logger")
public class TaskLogger {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="logger_id")
    private Long loggerId;//任务日志id主键

    @Column(name="create_time")
    private Date createTime;//任务日志创建时间

    @Column(name="logger_ticking")
    private String loggerTicking;//任务日志反馈内容


    @ManyToOne
    @JoinColumn(name="task_id")
    private TaskList taskId;//任务id外键

    @Column(name="username")
    private String username;//任务日志生成人

    @Column(name="logger_statusid")
    private Integer loggerStatusid; //状态id

}
