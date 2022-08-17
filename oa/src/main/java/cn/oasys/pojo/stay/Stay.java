package cn.oasys.pojo.stay;

import cn.oasys.pojo.evection.EveCtIonMoney;
import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="aoa_stay")
//住宿申请表
public class Stay {

    @Id
    @Column(name="stay_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long stayId;

    @OneToOne
    @JoinColumn(name="user_name")
    private User user;//出差人员

    @Column(name="stay_time")
    private Date stayTime;//入住日期

    @Column(name="leave_time")
    private Date leaveTime;//离店日期

    @Column(name="stay_city")
    private String stayCity;//入住城市

    @Column(name="hotel_name")
    private String hotelName;//入住酒店

    @Column(name="day")
    private Integer day;//入住天数

    @Column(name="stay_money")
    private Double stayMoney;//酒店标准

    @ManyToOne()
    @JoinColumn(name="evemoney_id")
    private  EveCtIonMoney  evemoneyId;

    @Transient
    private String nameuser;
}
