package cn.oasys.dao;

import cn.oasys.pojo.Position;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface PositionMapper extends Mapper<Position> {
    @Select("SELECT aoa_position.`name` FROM aoa_position WHERE aoa_position.position_id = #{posId}")
    String positionName(@Param("posId") Long posId);

//    Position selectReviewed(Long positionId);
}
