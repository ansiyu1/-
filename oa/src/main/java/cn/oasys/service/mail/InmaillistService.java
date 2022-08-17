package cn.oasys.service.mail;

import cn.oasys.pojo.mail.Inmaillist;
import cn.oasys.service.BaseService;

import java.util.List;

public interface InmaillistService extends BaseService<Inmaillist> {
    List<Inmaillist> findByPushAndDelAndMailUserid(Integer push, Integer del, Long userId);

    Inmaillist selectInMail(Long lid);

    int insertInMail(Inmaillist mail);

    Inmaillist selectMaxId();
}
