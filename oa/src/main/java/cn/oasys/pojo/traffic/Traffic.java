package cn.oasys.pojo.traffic;

import cn.oasys.pojo.evection.EveCtIonMoney;
import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name="aoa_traffic")
//交通费用明细表
public class Traffic {
    @Id
    @Column(name="traffic_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long trafficId;

    @OneToOne
    @JoinColumn(name="user_name")
    private User userName;//出差人员

    @Column(name="depart_time")
    private Date departTime;//出发时间

    @Column(name="depart_name")
    private String departName;//出发城市

    @Column(name="reach_name")
    private String reachName;//到达城市

    @Column(name="traffic_name")
    private String trafficName;//交通工具

    @Column(name="seat_type")
    private String seatType;//座位类型

    @Column(name="traffic_money")
    private Double trafficMoney;//交通标准

    @ManyToOne()
    @JoinColumn(name="evection_id")
    private EveCtIonMoney evectionId;

    @Transient
    private String username;
}
