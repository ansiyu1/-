package cn.oasys.dao.type;

import cn.oasys.pojo.TypeList;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TypeListMapper extends Mapper<TypeList> {
    //根据模块名查找到类型集合
    @Select("SELECT * FROM aoa_type_list WHERE aoa_type_list.type_model=#{typeModel}")
    List<TypeList> findByTypeModel(@Param("typeModel")String typeModel);
    @Select("select aoa_type_list.type_name from aoa_type_list  where aoa_type_list.type_id=#{typeId}")
    String findName(@Param("typeId")Long typeId);
    @Select("SELECT * FROM aoa_type_list WHERE type_id=#{typeId}")
    TypeList selectId(@Param("typeId") Long typeId);

    @Select("SELECT * FROM aoa_type_list WHERE aoa_type_list.type_model=#{arg0} and aoa_type_list.type_name=#{arg1}")
    TypeList findByTypeModelAndTypeName(String type,String val);

    @Select("SELECT * FROM aoa_type_list WHERE type_model like #{name} or type_name like #{name}")
    List<TypeList> selectAuthor(@Param("name") String name);
}
