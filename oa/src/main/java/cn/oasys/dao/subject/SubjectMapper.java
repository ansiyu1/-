package cn.oasys.dao.subject;

import cn.oasys.pojo.subject.Subject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SubjectMapper extends Mapper<Subject> {
    @Select("SELECT*FROM aoa_subject WHERE aoa_subject.parent_id=#{parentId}")
    List<Subject> findParentId(@Param("parentId") Long parentId);
    @Select("SELECT*FROM aoa_subject WHERE aoa_subject.parent_id!=#{parentId}")
    List<Subject> findNotParentId(@Param("parentId")Long parentId);
}
