package cn.oasys.service.evection;

import cn.oasys.pojo.evection.Evection;
import cn.oasys.service.BaseService;

public interface EvectionService extends BaseService<Evection> {
    Evection findByProId(Long processId);

    int insertEvection(Evection ev);
}
