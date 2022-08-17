package cn.oasys.dao.user;

import cn.oasys.pojo.user.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    User findLoginUser(@Param("userName") String userName, @Param("password") String password, @Param("userTel") String userTel);

    @Select("SELECT*FROM aoa_user WHERE aoa_user.father_id=#{fatherId}")
    List<User> staff(@Param("fatherId") Long fatherId);

    List<User> myEmployUser(@Param("fatherId")Long fatherId,@Param("baseKey")String baseKey);

    @Select("SELECT*FROM aoa_user WHERE aoa_user.user_name=#{username}")
    User certificationAudit(String username);

    @Select("SELECT * FROM aoa_user WHERE user_id=#{userId}")
    List<User> selectId(Long userId);

    @Select("SELECT * FROM aoa_user WHERE user_id=#{userId}")
    User aa(Long userId);

    @Select("SELECT aoa_user.user_name FROM aoa_user WHERE user_id=#{userId}")
    List<User> se(Long userId);

    @Select("SELECT * FROM aoa_user INNER JOIN aoa_task_list WHERE aoa_task_list.task_push_user_id=aoa_user.user_id AND aoa_task_list.task_push_user_id=#{taskPushUserId}")
    List<User> dd(Long taskPushUserId);

    List<User> selectAllUser();

    User selectReviewed(@Param("userId") Long userId);

    List<User> selectByPinyinLike(@Param("pinyin")String pinyin);

    List<User> findUsers(@Param("baseKey")String baseKey, @Param("pinyin")String pinyin);

    List<User> findSelectUsers(@Param("baseKey")String baseKey,@Param("pinyin") String pinyin);

    @Update("UPDATE aoa_user SET img_path = #{filePath} WHERE user_id = #{userId}")
    int updateFilePath(@Param("filePath") String filePath,@Param("userId") Long userId);
}
