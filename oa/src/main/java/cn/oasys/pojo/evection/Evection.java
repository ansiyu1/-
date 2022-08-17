package cn.oasys.pojo.evection;

import cn.oasys.pojo.ProcessList;
import lombok.Data;

import javax.persistence.*;
@Data
@Table
@Entity(name="aoa_evection")
//出差表
public class Evection {

    @Id
    @Column(name="evection_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long evectionId;

    @Column(name="type_id")
    private Long typeId;	//外出类型

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="pro_id")
    private ProcessList proId;

    @Column(name="personnel_advice")
    private String personnelAdvice;//人事部意见及说明

    @Column(name="manager_advice")
    private String managerAdvice;//经理意见及说明

    @Transient
    private String  nameuser;//审核人员
}
