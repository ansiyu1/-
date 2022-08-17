package cn.oasys.dao.user;

import cn.oasys.pojo.user.UserLog;
import cn.oasys.pojo.user.UserLogRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserLogRecordMapper extends Mapper<UserLogRecord> {
    @Select("SELECT COUNT(*) FROM aoa_user_login_record WHERE DATE_FORMAT(aoa_user_login_record.login_time,'%Y-%m-%d')=#{date}")
    int recordCount(@Param("date") String date);

}
