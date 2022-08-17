package cn.oasys.dao.evection;

import cn.oasys.pojo.evection.Evection;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface EvectionMapper extends Mapper<Evection> {
    Evection findByProId(@Param("processId") Long processId);

    int insertEvection(Evection ev);
}
