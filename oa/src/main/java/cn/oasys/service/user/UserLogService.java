package cn.oasys.service.user;

import cn.oasys.pojo.user.User;
import cn.oasys.pojo.user.UserLog;
import cn.oasys.service.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserLogService extends BaseService<UserLog> {
    List<UserLog> userlogs(Long userId);
    PageInfo<UserLog> ulogpaging(int page, String basekey, long userid, String time);
}
