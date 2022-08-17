package cn.oasys.service.position.impl;

import cn.oasys.dao.PositionMapper;
import cn.oasys.pojo.Position;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("positionService")
public class PositionServiceImpl extends BaseServiceImpl<Position> implements PositionService {
    @Autowired
    PositionMapper positionMapper;
    @Override
    public String positionName(Long posId) {
        return positionMapper.positionName(posId);
    }

//    @Override
//    public Position selectReviewed(Long positionId) {
//        return positionMapper.selectReviewed(positionId);
//    }
}
