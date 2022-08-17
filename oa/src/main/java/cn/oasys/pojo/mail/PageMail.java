package cn.oasys.pojo.mail;

import lombok.Data;

import java.util.Date;

@Data
public class PageMail {

	private Long mail_id;//邮件id
	
	private Long mail_type;//邮件类型（通知，公告，邮件）
	
	private Long mail_status_id;//邮件状态（一般，紧急，重要）
	
	private String mail_title;//邮件主题
	
	private String in_receiver;//接收人（可以是多个）
	
	private Long mail_file_id;//邮件附件id
	
	private Date mail_create_time;//邮件创建时间
	
	private Boolean is_star=false;//是否星标
	
	private Boolean is_read=false;//是否已读
	
}
