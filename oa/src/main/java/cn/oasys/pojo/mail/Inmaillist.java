package cn.oasys.pojo.mail;


import cn.oasys.pojo.attch.Attachment;
import cn.oasys.pojo.user.User;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name="aoa_in_mail_list")
//内部邮件
public class Inmaillist {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="mail_id")
	private Long mailId;//邮件id
	
	@Column(name="mail_type")
	private Long mailType;//邮件类型（通知，公告，邮件）
	
	@Column(name="mail_status_id")
	private Long mailStatusId;//邮件状态（一般，紧急，重要）
	
	@ManyToOne
	@JoinColumn(name="mail_in_push_user_id")
	private User mailInPushUserId;//发件人id
	
	@Column(name="mail_title")
	@NotEmpty(message="邮件主题不能为空")
	private String mailTitle;//邮件主题
	
	@Column(name="mail_content")
	@NotEmpty(message="邮件内容不能为空")
	private String mailContent;//邮件内容
	
	@Column(name="in_receiver")
	private String inReceiver;//接收人（可以是多个）
	
	@ManyToOne
	@JoinColumn(name="mail_file_id")
	private Attachment mailFileId;//邮件附件id
	
	@Column(name="mail_create_time")
	private Date mailCreateTime;//邮件创建时间
	
	@ManyToOne
	@JoinColumn(name="mail_number_id")
	private MailNumber mailNumberid;//外部邮件账号id
	
	@Column(name="mail_del")
	private Boolean mailDel=false;
	
	@Column(name="mail_push")
	private Boolean mailPush=false;
	
	@Column(name="mail_star")
	private Boolean mailStar=false;
	
	@Transient 
	private Long inmail;
	
	@Transient 
	private String huizhuan;
}
