package cn.oasys.service.mail;

import cn.oasys.pojo.mail.MailNumber;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface MailNumberService extends BaseService<MailNumber> {
    PageInfo<MailNumber> index(int page, int size, User user, String o, Model model);

    int insertMailNumber(MailNumber mail);

    MailNumber selectAccountInformation(Long accountId);

    List<MailNumber> findByStatusAndMailUserId(Long status, Long userId);

    MailNumber selectMailNumber(Long inmail);

    boolean isContainChinese(String inReceiver);
}
