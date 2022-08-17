package cn.oasys.dao.details;

import cn.oasys.pojo.details.DetailsBurse;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DetailsBursaMapper extends Mapper<DetailsBurse> {
    List<DetailsBurse> selectBurs(Long burSeMeNtId);
}
