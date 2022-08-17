package cn.oasys.service.bursement;

import cn.oasys.pojo.bursement.BurSeMeNt;
import cn.oasys.service.BaseService;

public interface BurSeMeNtService extends BaseService<BurSeMeNt> {
    BurSeMeNt findByProId(Long processId);

    BurSeMeNt selectReimbursement(Long processId);

    int insertBurSeMeNt(BurSeMeNt bu);
}
