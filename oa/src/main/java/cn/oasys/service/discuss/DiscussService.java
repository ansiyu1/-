package cn.oasys.service.discuss;

import cn.oasys.pojo.Discuss;
import cn.oasys.service.BaseService;

public interface DiscussService extends BaseService<Discuss> {
    int discussCount(Long userId);
}
