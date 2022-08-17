package cn.oasys.service.resign.impl;

import cn.oasys.dao.resign.ResignMapper;
import cn.oasys.dao.reviewed.ReviewedMapper;
import cn.oasys.pojo.resign.Resign;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.resign.ResignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resignService")
public class ResignServiceImpl extends BaseServiceImpl<Resign> implements ResignService {
    @Autowired
    ResignMapper resignMapper;
    @Override
    public Resign selectResignation(Long processId) {
        return resignMapper.selectResignation(processId);
    }

    @Override
    public int insertResign(Resign over) {
        return resignMapper.insertResign(over);
    }
}
