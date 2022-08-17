package cn.oasys.service.plan.impl;

import cn.oasys.dao.plan.PlanListMapper;
import cn.oasys.pojo.PlanList;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.plan.PlanListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("planListService")
public class PlanListServiceImpl extends BaseServiceImpl<PlanList> implements PlanListService {
    @Autowired
    PlanListMapper planListMapper;
    @Override
    public List<PlanList> planLimit(Long userId) {
        return planListMapper.planLimit(userId);
    }
}
