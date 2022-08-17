package cn.oasys.dao.regular;

import cn.oasys.pojo.regular.Regular;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface RegularMapper extends Mapper<Regular> {
    Regular selectRegular(@Param("processId") Long processId);

    int insertRegular(Regular over);
}
