package cn.oasys.dao.mail;

import cn.oasys.pojo.mail.MailReciver;
import cn.oasys.pojo.mail.PageMail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MailReciverMapper extends Mapper<MailReciver> {
    @Select("SELECT * FROM aoa_mail_reciver,aoa_in_mail_list WHERE  aoa_mail_reciver.mail_id=aoa_in_mail_list.mail_id AND aoa_mail_reciver.mail_reciver_id=#{userId} AND aoa_mail_reciver.is_read=0 AND aoa_mail_reciver.is_del=0")
    List<MailReciver> readMail(@Param("userId") Long userId);

    List<MailReciver> findByReadAndDelAndReciverId(@Param("read") Integer read, @Param("del") Integer del, @Param("userId") Long userId);

    List<MailReciver> findByDelAndReciverId(@Param("del")Integer del, @Param("userId")Long userId);

    List<MailReciver> findMail(@Param("userId")Long userId, @Param("del")Integer del);

    List<MailReciver> findMailByStatus(@Param("userId")Long userId, @Param("statusId")Long statusId, @Param("del")Integer del);

    List<MailReciver> findMailByType(@Param("userId")Long userId,@Param("typeId") Long typeId,@Param("del") Integer del);

    List<MailReciver> findMails(@Param("userId")Long userId, @Param("del")Integer del, @Param("val")String val);
    @Select("SELECT COUNT(*) FROM aoa_mail_reciver WHERE aoa_mail_reciver.mail_reciver_id = #{userId}")
    int mailCount(@Param("userId") Long userId);

    int insertMailReciver(MailReciver mreciver);

    MailReciver selectByReciverIdAndMailId(@Param("userId")Long userId,@Param("id") Long id);
}
