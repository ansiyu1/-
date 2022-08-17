package cn.oasys.pojo.holiday;

import cn.oasys.pojo.ProcessList;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name="aoa_holiday")
//请假表
public class Holiday {

    @Id
    @Column(name="holiday_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long holidayId;

    @Column(name="type_id")
    private Long typeId;  //请假类型

    @Column(name="leave_days")
    private Double leaveDays; //请假天数

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
