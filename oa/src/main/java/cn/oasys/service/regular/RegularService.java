package cn.oasys.service.regular;

import cn.oasys.pojo.regular.Regular;
import cn.oasys.service.BaseService;

public interface RegularService extends BaseService<Regular> {
    Regular selectRegular(Long processId);

    int insertRegular(Regular over);
}
