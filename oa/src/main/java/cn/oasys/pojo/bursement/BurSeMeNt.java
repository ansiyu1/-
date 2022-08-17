package cn.oasys.pojo.bursement;

import cn.oasys.pojo.ProcessList;
import cn.oasys.pojo.details.DetailsBurse;
import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name="aoa_bursement")
//费用报销表
public class BurSeMeNt {
    @Id
    @Column(name="bursement_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long bursementId;

    @OneToOne()
    @JoinColumn(name="user_id")
    private User userId;//证明人

    private String name;//相关客户

    @Column(name="type_id")
    private Long typeId;//报销方式（银行卡，现金，其他）

    @OneToOne
    @JoinColumn(name="operation_name")
    private User operationName;//报销人员

    private Date burseTime;//报销日期

    private Integer allinvoices ;//票据总数

    @Column(name="manager_advice")
    private String managerAdvice;//经理意见及说明

    @Column(name="financial_advice")
    private String financialAdvice;//财务部意见及说明

    @Column(name="all_money")
    private Double allMoney;//总计金额

    @Transient
    private String username;//审核人员

    @Transient
    private String namemoney;//承担主体

    @OneToMany(cascade=CascadeType.ALL,mappedBy="bursmentId",orphanRemoval = true)
    List<DetailsBurse> details;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="pro_id")
    private ProcessList proId;
}
