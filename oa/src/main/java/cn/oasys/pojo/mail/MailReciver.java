package cn.oasys.pojo.mail;

import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;
@Data
@Table(name="aoa_mail_reciver")
public class MailReciver {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="pk_id")
    private Long pkId;


    @ManyToOne
    @JoinColumn(name="mail_id")
    private Inmaillist mailId;//内部邮件id


    @ManyToOne
    @JoinColumn(name="mail_reciver_id")
    private User mailReciverId;//内部用户id

    @Column(name="is_read")
    private Boolean isRead=false;//是否已读

    @Column(name="is_star")
    private Boolean isStar=false;//是否星标

    @Column(name="is_del")
    private Boolean isDel=false;//是否真正删除
}
