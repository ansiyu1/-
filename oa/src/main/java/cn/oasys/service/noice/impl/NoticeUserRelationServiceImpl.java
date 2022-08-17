package cn.oasys.service.noice.impl;

import cn.oasys.dao.notice.NoticeUserRelationMapper;
import cn.oasys.pojo.notice.NoticeUserRelation;
import cn.oasys.pojo.notice.ReceiverNote;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.noice.NoticeUserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeUserRelationServiceImpl extends BaseServiceImpl<NoticeUserRelation> implements NoticeUserRelationService {
    @Autowired
    NoticeUserRelationMapper noticeUserRelationMapper;
    @Override
    public List<NoticeUserRelation> unread(Long read, Long userId) {
        return noticeUserRelationMapper.unread(read, userId);
    }



}
