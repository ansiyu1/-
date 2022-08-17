package cn.oasys.dao.process;

import cn.oasys.pojo.ProcessList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProcessListMapper extends Mapper<ProcessList> {
    @Select("select * from aoa_process_list  where aoa_process_list.process_user_id=#{userId} ORDER BY aoa_process_list.apply_time DESC LIMIT 0,3")
    List<ProcessList> processBy(Long userId);

    @Select("select * from aoa_process_list  where aoa_process_list.process_user_id=#{userId} and aoa_process_list.process_id=#{proId}")
    ProcessList byUserIdAndTitle(Long userId,Long proId);

    ProcessList selectThisApplication(@Param("proId") Long proId);

    List<ProcessList> findByUserId(Long userId);

    ProcessList selectId(@Param("id") Long id);

    int insertProcess(ProcessList pro);
}
