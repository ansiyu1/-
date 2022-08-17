package cn.oasys.service.user;

import cn.oasys.pojo.user.UserLog;
import cn.oasys.pojo.user.UserLogRecord;
import cn.oasys.service.BaseService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserLogRecordService extends BaseService<UserLogRecord> {
    @Select("\tSELECT COUNT(*) FROM aoa_user_login_record WHERE DATE_FORMAT(aoa_user_login_record.login_time,'%Y-%m-%d')=#{date}")
    int recordCount(String date);


}
