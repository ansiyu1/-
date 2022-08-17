package cn.oasys.service.sysmenu;

import cn.oasys.pojo.SysMenu;
import cn.oasys.pojo.TypeList;
import cn.oasys.service.BaseService;

import java.util.List;

public interface SysMenuService extends BaseService<SysMenu> {
    List<SysMenu> findByParentAll();
    List<SysMenu> findByParentsXian();
    List<SysMenu> selectFu();


    int addMenu(TypeList typeList);
}
