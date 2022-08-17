package cn.oasys.service.user.impl;

import cn.oasys.dao.DeptMapper;
import cn.oasys.dao.PositionMapper;
import cn.oasys.dao.user.UserMapper;
import cn.oasys.pojo.Dept;
import cn.oasys.pojo.Position;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.user.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    DeptMapper deptMapper;
    @Autowired
    PositionMapper positionMapper;
    @Override
    public User findLoginUser(String userName, String password,String userTel) {
        return userMapper.findLoginUser(userName,password,userTel);
    }

    @Override
    public List<User> staff(Long fatherId) {
        return userMapper.staff(fatherId);
    }

    @Override
    public PageInfo<User> myEmployUser(Long fatherId, String baseKey, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<User>(userMapper.myEmployUser(fatherId,baseKey));
    }

    @Override
    public User certificationAudit(String username) {
        return userMapper.certificationAudit(username);
    }

    @Override
    public PageInfo<User> findByFather(List<User> list, int page, int size) {
        PageHelper.startPage(page, size);
        return  new PageInfo< >( list);
    }

    @Override
    public List<User> selectId(Long userId) {
        return userMapper.selectId(userId);
    }

    @Override
    public User aa(Long userId) {
        return userMapper.aa(userId);
    }

    @Override
    public List<User> se(Long userId) {
        return userMapper.se(userId);
    }

    @Override
    public List<User> dd(Long taskPushUserId) {
        return userMapper.dd(taskPushUserId);
    }

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public User selectReviewed(Long userId) {
        return userMapper.selectReviewed(userId);
    }

    /**
     * 用户封装
     * @param
     * @param
     * @return
     */
    @Override
    public void user(Model model){
        //查看用户
        List<User> userList = userMapper.selectAllUser();
        // 查询部门表
        Iterable<Dept> deptlist = deptMapper.selectAll();
        // 查职位表
        Iterable<Position> poslist = positionMapper.selectAll();
        model.addAttribute("emplist", userList);
        model.addAttribute("deptlist", deptlist);
        model.addAttribute("poslist", poslist);
        model.addAttribute("url", "names");
    }

    @Override
    public List<User> selectByPinyinLike(String pinyin) {
        return userMapper.selectByPinyinLike(pinyin);
    }

    @Override
    public List<User> findUsers(String baseKey, String pinyin) {
        return userMapper.findUsers(baseKey,pinyin);
    }

    @Override
    public List<User> findSelectUsers(String baseKey, String pinyin) {
        return userMapper.findSelectUsers(baseKey,pinyin);
    }

    @Override
    public int updateFilePath(String filePath,Long userId) {
        return userMapper.updateFilePath(filePath,userId);
    }


}
