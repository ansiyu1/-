package cn.oasys.service.evection;

import cn.oasys.pojo.ProcessList;
import cn.oasys.pojo.evection.EveCtIonMoney;
import cn.oasys.service.BaseService;

public interface EveCtIonMoneyService extends BaseService<EveCtIonMoney> {

    EveCtIonMoney findByProId(Long processId);

    int insertEveCtIonMoney(EveCtIonMoney emoney);
}
