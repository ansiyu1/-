package cn.oasys.dao.director;

import cn.oasys.pojo.director.DirectorUser;
import cn.oasys.pojo.user.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DirectorUserMapper extends Mapper<DirectorUser> {
    @Select("select aoa_director_users.catelog_name from aoa_director_users where aoa_director_users.user_id= #{userId} and (aoa_director_users.catelog_name is not null and aoa_director_users.catelog_name !='')")
    Set<String> findByUser(@Param("userId") Long userId);

    List<DirectorUser> findByUserAndShareUserNotNullAndHandle(@Param("userId") Long userId, @Param("isHandle") Long isHandle);

    DirectorUser findByDirectorAndUser(@Param("directorId")Long directorId, @Param("userId")Long userId);

    /*根据用户来找外部通讯录联系人*/
    List<Map<String, Object>> allDirector(@Param("userId") Long userId,@Param("pinyin") String pinyin,@Param("outtype") String outtype,@Param("baseKey") String baseKey);
}
