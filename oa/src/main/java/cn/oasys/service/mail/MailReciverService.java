package cn.oasys.service.mail;

import cn.oasys.pojo.mail.MailReciver;
import cn.oasys.pojo.mail.PageMail;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MailReciverService extends BaseService<MailReciver> {
    List<MailReciver> readMail(Long userId);

    List<MailReciver> findByReadAndDelAndReciverId(Integer read, Integer del, Long userId);

    List<MailReciver> findByDelAndReciverId(Integer del, Long userId);

    PageInfo<MailReciver> findMail(int page,int size,Long userId, Integer del);

    List<MailReciver> findMailByStatus(Long userId, Long statusId, Integer del);

    List<MailReciver> findMailByType(Long userId, Long typeId, Integer del);

    List<MailReciver> findMails(Long userId, Integer del, String val);

    int mailCount(Long userId);

    int insertMailReciver(MailReciver mreciver);

    MailReciver selectByReciverIdAndMailId(Long userId, Long id);
}
