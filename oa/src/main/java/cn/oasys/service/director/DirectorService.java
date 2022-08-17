package cn.oasys.service.director;

import cn.oasys.pojo.director.Director;
import cn.oasys.service.BaseService;

import java.util.List;
import java.util.Map;

public interface DirectorService extends BaseService<Director> {
    int directCount(Long userId);

    Director selectDirector(Long did);
}
