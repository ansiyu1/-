package cn.oasys.dao.mail;

import cn.oasys.pojo.mail.Inmaillist;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface InmaillistMapper extends Mapper<Inmaillist> {
    List<Inmaillist> findByPushAndDelAndMailUserid(@Param("push") Integer push, @Param("del")Integer del, @Param("userId") Long userId);

    Inmaillist selectInMail(@Param("lid") Long lid);

    int insertInMail(Inmaillist mail);

    Inmaillist selectMaxId();
}
