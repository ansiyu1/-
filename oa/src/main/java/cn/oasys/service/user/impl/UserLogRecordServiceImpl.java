package cn.oasys.service.user.impl;

import cn.oasys.dao.user.UserLogMapper;
import cn.oasys.dao.user.UserLogRecordMapper;
import cn.oasys.pojo.user.UserLog;
import cn.oasys.pojo.user.UserLogRecord;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.user.UserLogRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("userLogRecordService")
public class UserLogRecordServiceImpl extends BaseServiceImpl<UserLogRecord> implements UserLogRecordService {
    @Autowired
    UserLogRecordMapper userLogRecordMapper;
    @Override
    public int recordCount(String date) {
        return userLogRecordMapper.recordCount(date);
    }


}
