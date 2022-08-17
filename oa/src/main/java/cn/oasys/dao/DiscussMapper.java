package cn.oasys.dao;

import cn.oasys.pojo.Discuss;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;


public interface DiscussMapper extends Mapper<Discuss> {
    @Select("SELECT COUNT(*) FROM aoa_discuss_list WHERE aoa_discuss_list.discuss_user_id = #{userId}")
    int discussCount(@Param("userId")Long userId);
}
