package cn.oasys.pojo.mail;

import cn.oasys.pojo.user.User;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
@Data
@Entity
@Table(name="aoa_mailnumber")
//邮箱账号表
public class MailNumber {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="mail_number_id")
    private Long mailNumberId; //邮箱的主键id

    @Column(name="mail_type")
    private Long mailType;//邮件账号类型

    @Column(name="status")
    private Long status;//邮件状态（是否可用）

    @ManyToOne
    @JoinColumn(name="mail_num_user_id")
    private User mailNumUserId;//用户id

    @Column(name="mail_user_name",nullable=false)
    @NotEmpty(message="发件昵称不能为空")
    private String mailUserName;//用户别名

    @Column(name="mail_create_time")
    private Date mailCreateTime;//账号创建时间

    @Column(name="mail_account",nullable=false)
    @NotEmpty(message="邮件账号不能为空")
    @Pattern(regexp="^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$",message="请填写正确邮箱号")
    private String mailAccount;//邮件账号

    @Column(name="password",nullable=false)
    @NotEmpty(message="授权码不能为空")
    private String password;//账号授权码

    @Column(name="mail_des")
    private String mailDes;//账号信息备注
}
