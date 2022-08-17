package cn.oasys.service.mail.impl;

import cn.oasys.dao.mail.InmaillistMapper;
import cn.oasys.pojo.mail.Inmaillist;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.mail.InmaillistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InmaillistServiceImpl extends BaseServiceImpl<Inmaillist> implements InmaillistService {
    @Autowired
    InmaillistMapper inmaillistMapper;

    @Override
    public List<Inmaillist> findByPushAndDelAndMailUserid(Integer push, Integer del, Long userId) {
        return inmaillistMapper.findByPushAndDelAndMailUserid(push,del,userId);
    }

    @Override
    public Inmaillist selectInMail(Long lid) {
        return inmaillistMapper.selectInMail(lid);
    }

    @Override
    public int insertInMail(Inmaillist mail) {
        return inmaillistMapper.insertInMail(mail);
    }

    @Override
    public Inmaillist selectMaxId() {
        return inmaillistMapper.selectMaxId();
    }
}
