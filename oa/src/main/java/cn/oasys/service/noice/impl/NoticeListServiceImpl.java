package cn.oasys.service.noice.impl;

import cn.oasys.dao.notice.NoticeListMapper;
import cn.oasys.pojo.notice.NoticeList;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.noice.NoticeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("noticeListService")
public class NoticeListServiceImpl extends BaseServiceImpl<NoticeList> implements NoticeListService {
    @Autowired
    NoticeListMapper noticeListMapper;
    @Override
    public List<Map<String, Object>> myNotice(Long userId) {
        return noticeListMapper.myNotice(userId);
    }
}
