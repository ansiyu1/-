package cn.oasys.service.details;

import cn.oasys.pojo.details.DetailsBurse;
import cn.oasys.service.BaseService;

import java.util.List;

public interface DetailsBurseService extends BaseService<DetailsBurse> {
    List<DetailsBurse> selectBurs(Long burSeMeNtId);
}
