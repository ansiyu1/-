package cn.oasys.pojo;

import cn.oasys.pojo.user.User;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 计划表
 */
@Data
@Table(name="aoa_plan_list")
public class PlanList {
    @Id
    @Column(name="plan_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long planId;


    @Column(name="type_id")
    private Long typeId; //类型id

    @Column(name="status_id")
    private Long statusId; //状态id


    @Column(name="attach_id")
    private Long attachId;   //附件id

    @Column(name="start_time")
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date startTime;   //开始时间

    @Column(name="end_time")
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;   //结束时间

    @Column(name="create_time")
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;   //记录创建时间

    @NotEmpty(message="标题输入框不能为空")
    @Length(min=0,max=50)
    private String title;   //标题

    private String label;   //标签

    @Column(name="plan_content")
    @NotEmpty(message="计划输入框不能为空")
    private String planContent;   //计划内容

    @Column(name="plan_summary")
    private String planSummary;   //计划总结

    @Column(name="plan_comment")
    private String planComment;   //计划评论



    @Column(name="plan_user_id")
    private Long userId; //用户计划外键
}
