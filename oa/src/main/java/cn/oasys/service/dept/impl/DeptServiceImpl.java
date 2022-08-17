package cn.oasys.service.dept.impl;

import cn.oasys.dao.DeptMapper;
import cn.oasys.pojo.Dept;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.dept.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deptService")
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService {
    @Autowired
    DeptMapper deptMapper;
    @Override
    public String deptName(Long deptId) {
        return deptMapper.deptName(deptId);
    }
}
