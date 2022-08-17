package cn.oasys.pojo.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Table(name = "aoa_user_login_record")
public class UserLogRecord {
    @Id
    @Column(name = "record_id")
    private Integer id;	//记录id


    @Column(name = "ip_addr")
    private String ipAddr;	//ip地址

    @Column(name = "login_time")
    private Date loginTime;	//登陆时间

    @Column(name = "session_id")
    private String sessionId;	//session id

    private String browser;	//使用浏览器



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
