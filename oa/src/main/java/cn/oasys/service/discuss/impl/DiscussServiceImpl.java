package cn.oasys.service.discuss.impl;

import cn.oasys.dao.DiscussMapper;
import cn.oasys.pojo.Discuss;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.discuss.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("discussService")
public class DiscussServiceImpl extends BaseServiceImpl<Discuss> implements DiscussService {
    @Autowired
    DiscussMapper discussMapper;
    @Override
    public int discussCount(Long userId) {
        return discussMapper.discussCount(userId);
    }
}
