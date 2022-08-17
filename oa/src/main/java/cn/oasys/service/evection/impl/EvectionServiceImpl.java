package cn.oasys.service.evection.impl;

import cn.oasys.dao.evection.EvectionMapper;
import cn.oasys.pojo.evection.Evection;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.evection.EvectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvectionServiceImpl extends BaseServiceImpl<Evection> implements EvectionService {
    @Autowired
    EvectionMapper evectionMapper;

    @Override
    public Evection findByProId(Long processId) {
        return evectionMapper.findByProId(processId);
    }

    @Override
    public int insertEvection(Evection ev) {
        return evectionMapper.insertEvection(ev);
    }
}
