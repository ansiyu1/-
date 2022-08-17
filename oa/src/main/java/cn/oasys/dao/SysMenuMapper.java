package cn.oasys.dao;

import cn.oasys.pojo.SysMenu;
import cn.oasys.pojo.TypeList;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysMenuMapper extends Mapper<SysMenu> {


    @Select("SELECT*FROM aoa_sys_menu,aoa_role_power_list WHERE aoa_role_power_list.menu_id=aoa_sys_menu.menu_id AND aoa_sys_menu.parent_id=0 AND aoa_role_power_list.role_id=2")
    List<SysMenu> findByParentAll();

    @Select("SELECT*FROM aoa_sys_menu WHERE  aoa_sys_menu.parent_id!=0 ORDER BY aoa_sys_menu.parent_id")
    List<SysMenu> findByParentsXian();

    @Select("SELECT * FROM aoa_sys_menu WHERE aoa_sys_menu.parent_id=0")
    List<SysMenu> selectFu();

    int addMenu(TypeList typeList);
}
