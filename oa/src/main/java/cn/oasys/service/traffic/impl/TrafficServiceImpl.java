package cn.oasys.service.traffic.impl;

import cn.oasys.dao.traffic.TrafficMapper;
import cn.oasys.pojo.traffic.Traffic;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.traffic.TrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("trafficService")
public class TrafficServiceImpl extends BaseServiceImpl<Traffic> implements TrafficService {
    @Autowired
    TrafficMapper trafficMapper;
    @Override
    public List<Traffic> selectTraffic() {
        return trafficMapper.selectTraffic();
    }
}
