package cn.oasys.service.user.impl;

import cn.oasys.dao.user.UserLogMapper;
import cn.oasys.pojo.user.UserLog;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.user.UserLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@Service("userLogService")
public class UserLogServiceImpl extends BaseServiceImpl<UserLog> implements UserLogService {
    @Autowired
    UserLogMapper userLogMapper;
    @Override
    public List<UserLog> userlogs(Long userId) {
        return userLogMapper.userLogs(userId);
    }
    @Override
    public PageInfo<UserLog> ulogpaging(int page, String basekey, long userid, String time) {
        PageHelper.startPage(page,10);
        if(!StringUtils.isEmpty(basekey)){
            //模糊
            return new PageInfo<UserLog>(userLogMapper.selectBaseKey(userid, basekey));
        }//0为降序 1为升序
        if(!StringUtils.isEmpty(time)){
            if(time.toString().equals("0")) return new PageInfo<UserLog>(userLogMapper.selectByUserOrderByLogTimeDesc(userid));
            if(time.toString().equals("1")) return new PageInfo<UserLog>(userLogMapper.findByUserOrderByLogTimeAsc(userid));
        }else{
            return new PageInfo<UserLog>(userLogMapper.selectByUserOrderByLogTimeDesc(userid));
        }
        return null;
    }

}
