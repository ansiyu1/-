package cn.oasys.service.dept;

import cn.oasys.pojo.Dept;
import cn.oasys.service.BaseService;
import org.apache.ibatis.annotations.Param;

public interface DeptService extends BaseService<Dept> {
    String deptName(Long deptId);
}
