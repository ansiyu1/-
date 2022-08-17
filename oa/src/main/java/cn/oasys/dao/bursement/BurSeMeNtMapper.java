package cn.oasys.dao.bursement;

import cn.oasys.pojo.bursement.BurSeMeNt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface BurSeMeNtMapper extends Mapper<BurSeMeNt> {
    @Select("select * from aoa_bursement where aoa_bursement.pro_id=#{processId}")
    BurSeMeNt findByProId(@Param("processId") Long processId);

    BurSeMeNt selectReimbursement(Long processId);

    int insertBurSeMeNt(BurSeMeNt bu);
}
