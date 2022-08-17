package cn.oasys.dao.director;

import cn.oasys.pojo.director.Director;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface DirectorMapper extends Mapper<Director> {
    @Select("SELECT COUNT(*) FROM aoa_director WHERE aoa_director.user_id = #{userId}")
    int directCount(@Param("userId")Long userId);

    Director selectDirector(@Param("did") Long did);
}
