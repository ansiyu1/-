package cn.oasys.dao;

import cn.oasys.pojo.Director;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface DirectorMapper extends Mapper<Director> {
    @Select("SELECT COUNT(*) FROM aoa_director WHERE aoa_director.user_id = #{userId}")
    int directCount(@Param("userId")Long userId);
}
