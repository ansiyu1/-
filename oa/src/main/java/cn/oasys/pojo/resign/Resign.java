package cn.oasys.pojo.resign;

import cn.oasys.pojo.ProcessList;
import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="aoa_resign")
//离职表
public class Resign {

    @Id
    @Column(name="resign_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long resignId;

    private String suggest;//申请人的意见及建议

    @Column(name="is_finish")
    private Boolean isFinish=false;//是否还有费用报销未完成

    @OneToOne
    @JoinColumn(name="hand_user")
    private User handUser; //工作交接人员

    private String nofinish;//未完成事宜

    @Column(name="financial_advice")
    private String financialAdvice;//财务部意见及说明

    @Column(name="personnel_advice")
    private String personnelAdvice;//人事部意见及说明

    @Column(name="manager_advice")
    private String managerAdvice;//经理意见及说明

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="pro_id")
    private ProcessList proId;

    @Transient
    private String nameuser;//审核人员

    @Transient
    private String handuser;//交接人员
}
