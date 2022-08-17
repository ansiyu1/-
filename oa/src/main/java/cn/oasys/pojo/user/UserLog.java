package cn.oasys.pojo.user;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@Entity
@Table(name = "aoa_user_log")
public class UserLog {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;	//logid

    @Column(name = "ip_addr")
    private String ipAddr;	//ip地址

    private String title;	//操作菜单名字

    private String url;		//操作菜单url

    @Column(name = "log_time")
    private Date logTime;	//日志记录时间

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
