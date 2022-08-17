package cn.oasys.dao.stay;

import cn.oasys.pojo.stay.Stay;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StayMapper extends Mapper<Stay> {
    List<Stay> selectStay();
}
