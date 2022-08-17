package cn.oasys.pojo.regular;

import cn.oasys.pojo.ProcessList;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="aoa_regular")
//转正表
public class Regular {

    @Id
    @Column(name="regular_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long regularId;

    private String experience;//试用期/实习期心得体会

    private String understand;//对本岗位职员的要求的理解

    private String  pullulate;//试用期/实习期在哪些方面有了成长

    private String deficiency;//目前存在的不足

    private String dobetter;//如何在本岗位作得更好

    private String advice;//对公司产品的意见及建议

    private Double days;//实习天数

    @Column(name="personnel_advice")
    private String personnelAdvice;//人事部意见及说明

    @Column(name="manager_advice")
    private String managerAdvice;//经理意见及说明

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="pro_id")
    private ProcessList proId;

    @Transient
    private String nameuser;
}
