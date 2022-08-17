package cn.oasys.pojo;

import cn.oasys.pojo.attch.Attachment;
import cn.oasys.pojo.user.User;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name="aoa_process_list")
public class ProcessList {
    @Id
    @Column(name="process_id")
    private Long processId;

    @Column(name="type_name")
    private String typeName;			//流程申请类型

    @Column(name="status_id")
    private Long statusId;			//流程审核状态 id

    @Column(name="deeply_id")
    private Long deeplyId;    //紧急程度

    @Column(name="process_name")
    private String processName;		//标题

    @Column(name="process_des",columnDefinition="text")
    private String processDes;	//流程申请原因内容

    @ManyToOne
    @JoinColumn(name="process_user_id")
    private User processUserId;			//流程申请人

    @Column(name="apply_time")
    private Date applyTime;			//流程申请时间

    @Column(name="is_checked")
    private Boolean  isChecked=false;		//流程是否被驳回

    @Column(name="start_time")
    private Date startTime;			//流程开始时间

    @Column(name="end_time")
    private Date endTime;			//流程结束时间

    @Column(name="procsee_days")
    private Double procseeDays;		//流程总天数

    @ManyToOne
    @JoinColumn(name="pro_file_id")
    private Attachment proFileid;   //流程附件id

    private String shenuser;
}
