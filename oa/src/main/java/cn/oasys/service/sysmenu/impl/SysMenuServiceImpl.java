package cn.oasys.service.sysmenu.impl;

import cn.oasys.dao.SysMenuMapper;
import cn.oasys.pojo.SysMenu;
import cn.oasys.pojo.TypeList;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.sysmenu.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements SysMenuService {
    @Autowired
    SysMenuMapper sysMenuMapper;
    @Override
    public List<SysMenu> findByParentAll() {
        return sysMenuMapper.findByParentAll();
    }

    @Override
    public List<SysMenu> findByParentsXian() {
        return sysMenuMapper.findByParentsXian();
    }

    @Override
    public List<SysMenu> selectFu() {
        return sysMenuMapper.selectFu();
    }

    @Override
    public int addMenu(TypeList typeList) {
        return sysMenuMapper.addMenu(typeList);
    }


}
