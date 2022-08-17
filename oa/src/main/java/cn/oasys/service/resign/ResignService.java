package cn.oasys.service.resign;

import cn.oasys.pojo.resign.Resign;
import cn.oasys.service.BaseService;

public interface ResignService extends BaseService<Resign> {
    Resign selectResignation(Long processId);

    int insertResign(Resign over);
}
