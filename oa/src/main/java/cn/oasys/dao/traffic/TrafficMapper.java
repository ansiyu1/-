package cn.oasys.dao.traffic;

import cn.oasys.pojo.traffic.Traffic;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TrafficMapper extends Mapper<Traffic> {
    List<Traffic> selectTraffic();
}
