package cn.oasys.pojo;

import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 审核表
 */

@Data
@Entity
@Table(name="aoa_reviewed")
public class Reviewed {
    @Id
    @Column(name="reviewed_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long reviewedId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User userId;//审核人

    private String advice; //审核人意见

    @Column(name="status_id")
    private Long statusId;//审核人状态

    @Column(name="reviewed_time")
    private Date reviewedTime;//审核时间

    @ManyToOne
    @JoinColumn(name="pro_id")
    private ProcessList proId;

    @Column(name="del")
    private Boolean del=false;

    @Transient
    private String username;//传过来的审核人的名字
}
