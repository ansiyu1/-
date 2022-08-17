package cn.oasys.dao;

import cn.oasys.pojo.Dept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface DeptMapper extends Mapper<Dept> {
    @Select("SELECT aoa_dept.dept_name FROM aoa_dept WHERE aoa_dept.dept_id = #{deptId}")
    String deptName(@Param("deptId") Long deptId);
}
