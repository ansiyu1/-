package cn.oasys.service.position;

import cn.oasys.pojo.Position;
import cn.oasys.service.BaseService;

public interface PositionService extends BaseService<Position> {
    String positionName(Long posId);

//    Position selectReviewed(Long positionId);
}
