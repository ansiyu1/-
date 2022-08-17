package cn.oasys.dao.user;

import cn.oasys.pojo.user.User;
import cn.oasys.pojo.user.UserLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserLogMapper extends Mapper<UserLog> {
    List<UserLog> userLogs(@Param("userId")Long userId);

    List<UserLog> selectBaseKey(@Param("userId") Long userId, @Param("baseKey")String baseKey);

    List<UserLog> selectByUserOrderByLogTimeDesc(@Param("userId")Long userId);

    List<UserLog> findByUserOrderByLogTimeAsc(@Param("userId")Long userId);
}
