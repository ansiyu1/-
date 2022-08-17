package cn.oasys.dao.mail;

import cn.oasys.pojo.mail.MailNumber;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MailNumberMapper extends Mapper<MailNumber> {
    List<MailNumber> findByMailUserId(@Param("userId") Long userId);

    List<MailNumber> findByMailUserIdOrderByMailType(@Param("userId")Long userId);

    List<MailNumber> findByMailUserIdOrderByStatus(@Param("userId")Long userId);

    List<MailNumber> findByMailUserIdOrderByMailCreateTimeDesc(@Param("userId")Long userId);

    List<MailNumber> findByMailUserNameLikeAndMailUserId(@Param("val")String val, @Param("userId")Long userId);

    int insertMailNumber(MailNumber mail);

    MailNumber selectAccountInformation(@Param("accountId") Long accountId);


    List<MailNumber> findByStatusAndMailUserId(@Param("status") Long status,@Param("userId") Long userId);

    MailNumber selectMailNumber(@Param("inmail") Long inmail);
}
