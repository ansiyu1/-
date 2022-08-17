package cn.oasys.service.bursement.impl;

import cn.oasys.dao.bursement.BurSeMeNtMapper;
import cn.oasys.pojo.bursement.BurSeMeNt;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.bursement.BurSeMeNtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("burSeMeNtService")
public class BurSeMeNtServiceImpl extends BaseServiceImpl<BurSeMeNt> implements BurSeMeNtService {
    @Autowired
    BurSeMeNtMapper burSeMeNtMapper;
    @Override
    public BurSeMeNt findByProId(Long processId) {
        return burSeMeNtMapper.findByProId(processId);
    }

    @Override
    public BurSeMeNt selectReimbursement(Long processId) {
        return burSeMeNtMapper.selectReimbursement(processId);
    }

    @Override
    public int insertBurSeMeNt(BurSeMeNt bu) {
        return burSeMeNtMapper.insertBurSeMeNt(bu);
    }
}
