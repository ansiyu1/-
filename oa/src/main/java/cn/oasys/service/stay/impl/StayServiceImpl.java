package cn.oasys.service.stay.impl;

import cn.oasys.dao.stay.StayMapper;
import cn.oasys.pojo.stay.Stay;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.stay.StayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stayService")
public class StayServiceImpl extends BaseServiceImpl<Stay> implements StayService {
    @Autowired
    StayMapper stayMapper;
    @Override
    public List<Stay> selectStay() {

        return stayMapper.selectStay();
    }
}
