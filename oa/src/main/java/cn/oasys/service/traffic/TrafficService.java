package cn.oasys.service.traffic;

import cn.oasys.pojo.traffic.Traffic;
import cn.oasys.service.BaseService;

import java.util.List;

public interface TrafficService extends BaseService<Traffic> {
    List<Traffic> selectTraffic();
}
