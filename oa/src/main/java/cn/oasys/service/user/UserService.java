package cn.oasys.service.user;

import cn.oasys.pojo.AttendsList;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;

import java.util.List;


public interface UserService extends BaseService<User> {
    User findLoginUser(String userName, String password,String userTel);
    List<User> staff(Long fatherId);

    PageInfo<User> myEmployUser(Long fatherId, String baseKey, int pageNum, int pageSize);

    User certificationAudit(String username);

    PageInfo<User> findByFather(List<User> list, int page, int size);

    List<User> selectId(Long userId);

    User aa(Long userId);

    List<User> se(Long userId);

    List<User> dd(Long taskPushUserId);

    List<User> selectAllUser();

    User selectReviewed(Long userId);

    void user(Model model);

    List<User> selectByPinyinLike(String pinyin);

    List<User> findUsers(String baseKey, String pinyin);

    List<User> findSelectUsers(String baseKey, String pinyin);

    int updateFilePath(String filePath,Long userId);
}
