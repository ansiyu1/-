package cn.oasys.dao.resign;

import cn.oasys.pojo.resign.Resign;
import tk.mybatis.mapper.common.Mapper;

public interface ResignMapper extends Mapper<Resign> {
    Resign selectResignation(Long processId);

    int insertResign(Resign over);
}
