package cn.oasys.dao.evection;

import cn.oasys.pojo.evection.EveCtIonMoney;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface EveCtIonMoneyMapper extends Mapper<EveCtIonMoney> {
    @Select("select * from aoa_evectionmoney where aoa_evectionmoney.pro_id=#{processId}")
    EveCtIonMoney finByProId(@Param("processId") Long processId);

    int insertEveCtIonMoney(EveCtIonMoney emoney);
}
