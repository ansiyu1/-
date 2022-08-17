package cn.oasys.service.mail.impl;

import cn.oasys.dao.mail.MailReciverMapper;
import cn.oasys.pojo.mail.MailReciver;
import cn.oasys.pojo.mail.PageMail;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.mail.MailReciverService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("mailReciverService")
public class MailReciverServiceImpl extends BaseServiceImpl<MailReciver> implements MailReciverService {
    @Autowired
    MailReciverMapper mailReciverMapper;
    @Override
    public List<MailReciver> readMail(Long userId) {
        return mailReciverMapper.readMail(userId);
    }

    @Override
    public List<MailReciver> findByReadAndDelAndReciverId(Integer read, Integer del, Long userId) {
        return mailReciverMapper.findByReadAndDelAndReciverId(read,del,userId);
    }

    @Override
    public List<MailReciver> findByDelAndReciverId(Integer del, Long userId) {
        return mailReciverMapper.findByDelAndReciverId(del,userId);
    }

    @Override
    public PageInfo<MailReciver> findMail(int page,int size,Long userId, Integer del) {
        PageHelper.startPage(page,size);
        System.out.println("查询"+mailReciverMapper.findMail(userId,del));
        return new PageInfo<MailReciver>(mailReciverMapper.findMail(userId,del));
    }

    @Override
    public List<MailReciver> findMailByStatus(Long userId, Long statusId, Integer del) {
        return mailReciverMapper.findMailByStatus(userId,statusId,del);
    }

    @Override
    public List<MailReciver> findMailByType(Long userId, Long typeId, Integer del) {
        return mailReciverMapper.findMailByType(userId,typeId,del);
    }

    @Override
    public List<MailReciver> findMails(Long userId, Integer del, String val) {
        return mailReciverMapper.findMails(userId,del,val);
    }

    @Override
    public int mailCount(Long userId) {
        return mailReciverMapper.mailCount(userId);
    }

    @Override
    public int insertMailReciver(MailReciver mreciver) {
        return mailReciverMapper.insertMailReciver(mreciver);
    }

    @Override
    public MailReciver selectByReciverIdAndMailId(Long userId, Long id) {
        return mailReciverMapper.selectByReciverIdAndMailId(userId,id);
    }
}
