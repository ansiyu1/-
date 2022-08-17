package cn.oasys.service.regular.impl;

import cn.oasys.dao.regular.RegularMapper;
import cn.oasys.pojo.regular.Regular;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.regular.RegularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("regularService")
public class RegularServiceImpl extends BaseServiceImpl<Regular> implements RegularService {
    @Autowired
    RegularMapper regularMapper;
    @Override
    public Regular selectRegular(Long processId) {
        return regularMapper.selectRegular(processId);
    }

    @Override
    public int insertRegular(Regular over) {
        return regularMapper.insertRegular(over);
    }
}
