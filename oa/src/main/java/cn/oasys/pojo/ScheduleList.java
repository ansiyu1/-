package cn.oasys.pojo;

import cn.oasys.pojo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "aoa_schedule_list")
public class ScheduleList {
    @Id
    @Column(name = "rc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rcId;	//日程id

    @Column(name = "type_id")
    private Long typeId;	//类型id

    @Column(name = "status_id")
    private Long statusId;	//状态id

    @Column(name = "start_time")
    private Date startTime;	//开始时间

    @Column(name = "end_time")
    private Date endTime;	//结束时间

    @Column(name = "create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date();	//发布时间

    private String title;	//日程标题

    @Column(name = "miaoshu")
    private String describe;	//日程描述

    @Column(name = "is_remind")
    private Boolean isRemind = false;	//是否提醒

    @Transient
    private String username;

    private Boolean isreminded = false;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;			//日程所属人

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "aoa_schedule_user",			//日程联系人关联表
            joinColumns = {
                    @JoinColumn(name = "rcid")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id")
            })
    private List<User> users;	//联系人集合
}
