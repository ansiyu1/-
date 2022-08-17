package cn.oasys.pojo.evection;

import cn.oasys.pojo.ProcessList;
import cn.oasys.pojo.stay.Stay;
import cn.oasys.pojo.traffic.Traffic;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name="aoa_evectionmoney")
//出差费用申请表
public class EveCtIonMoney {
    @Id
    @Column(name="evectionmoney_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long evectionmoneyId;

    private Double money; //申请总金额

    private String name; //关联客户

    @Column(name="manager_advice")
    private String managerAdvice;//经理意见及说明

    @Column(name="financial_advice")
    private String financialAdvice;//财务部意见及说明

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="pro_id")
    private ProcessList proId;

    private Long pro;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="evectionId")
    List<Traffic> traffic;

    @OneToMany(cascade=CascadeType.ALL,mappedBy="evemoneyId")
    List<Stay> stay;

    @Transient
    private String shenname;//审核人员
}
