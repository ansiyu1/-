package cn.oasys.service.plan;

import cn.oasys.pojo.PlanList;
import cn.oasys.service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanListService extends BaseService<PlanList> {
    List<PlanList> planLimit(Long userId);
}
