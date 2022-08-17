package cn.oasys.pojo.details;

import cn.oasys.pojo.bursement.BurSeMeNt;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name="aoa_detailsburse")
//报销费用明细表
public class DetailsBurse {

    @Id
    @Column(name="detailsburse_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long detailsburseId;

    @Column(name = "produce_time")
    private Date produceTime;//费用产生时间

    private String 	subject;//费用产生科目

    private String descript;//费用说明

    private Integer invoices ;//票据张数

    private double detailmoney;//报销金额

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="bursment_id")
    private BurSeMeNt bursmentId;//对应报销表
}
