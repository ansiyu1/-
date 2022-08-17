package cn.oasys.pojo.overtime;

import cn.oasys.pojo.ProcessList;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name="aoa_overtime")
//加班表
public class Overtime {

    @Id
    @Column(name="overtime_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long overtimeId;
    @Column(name="type_id")
    private Long typeId; //加班类型

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="pro_id")
    private ProcessList proId;

    @Column(name="personnel_advice")
    private String personnelAdvice;//人事部意见及说明

    @Column(name="manager_advice")
    private String managerAdvice;//经理意见及说明

    @Transient
    private String nameuser;
}
