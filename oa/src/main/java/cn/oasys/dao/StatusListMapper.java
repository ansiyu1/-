package cn.oasys.dao;

import cn.oasys.pojo.StatusList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StatusListMapper extends Mapper<StatusList> {
    @Select("SELECT * FROM aoa_status_list WHERE aoa_status_list.status_model=#{statusModel}")
    List<StatusList> findByStatusModel(String statusModel);

    //根据模块名和名字查找到唯一对象
    @Select("SELECT * FROM aoa_status_list WHERE aoa_status_list.status_model=#{statusModel} AND aoa_status_list.status_name=#{statusName}")
    StatusList selectStatusId(@Param("statusModel") String statusModel, @Param("statusName") String statusName);

    @Select("SELECT * FROM aoa_status_list WHERE status_id=#{statusId}")
    StatusList selectO(Long statusId);

    @Select("SELECT * FROM aoa_status_list WHERE status_name like#{statusName}")
    List<StatusList> selectName(@Param("name")String name);
}
