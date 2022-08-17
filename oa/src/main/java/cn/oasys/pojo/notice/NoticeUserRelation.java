package cn.oasys.pojo.notice;

import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;
@Data
public class NoticeUserRelation {

    @Id
    @Column(name = "relatin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relatinId;	//用户与通知中间关联表主键id


    @ManyToOne
    @JoinColumn(name = "relatin_notice_id")
    private NoteList relatinNoticeId;				//通知id

    @ManyToOne
    @JoinColumn(name = "relatin_user_id")
    private User relatinUserId;				//用户id

    @Column(name = "is_read")
    private Long isRead;				//此条通知该用户是否一已读
}
