package cn.oasys.service.evection.impl;

import cn.oasys.dao.evection.EveCtIonMoneyMapper;
import cn.oasys.pojo.ProcessList;
import cn.oasys.pojo.evection.EveCtIonMoney;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.evection.EveCtIonMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("eveCtIonMoneyService")
public class EveCtIonMoneyServiceImpl extends BaseServiceImpl<EveCtIonMoney> implements EveCtIonMoneyService {

    @Autowired
    EveCtIonMoneyMapper eveCtIonMoneyMapper;

    @Override
    public EveCtIonMoney findByProId(Long processId) {
        return eveCtIonMoneyMapper.finByProId(processId);
    }

    @Override
    public int insertEveCtIonMoney(EveCtIonMoney emoney) {
        return eveCtIonMoneyMapper.insertEveCtIonMoney(emoney);
    }
}
