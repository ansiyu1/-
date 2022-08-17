package cn.oasys.service.director;

import cn.oasys.pojo.director.DirectorUser;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DirectorUserService extends BaseService<DirectorUser> {
    Set<String> findByUser(Long userId);

    List<DirectorUser> findByUserAndShareUserNotNullAndHandle(Long userId, Long isHandle);

    DirectorUser findByDirectorAndUser(Long directorId, Long userId);

    List<Map<String, Object>> allDirector(Long userId, String alph, String outtype, String baseKey);
}
