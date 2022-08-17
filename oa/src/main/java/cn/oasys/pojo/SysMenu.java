package cn.oasys.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Data
@Entity
@Table(name = "aoa_sys_menu")
public class SysMenu {

    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId; // 菜单id

    @Column(name = "parent_id")
    private Long parentId=0L; // 父id

    @Column(name = "menu_name")
    @NotEmpty(message="菜单名字不能为空")
    private String menuName; // 菜单名字

    @Column(name = "menu_url")
    @NotEmpty(message="{sysMenu.menuUrl.NotNull}")
    private String menuUrl; // 菜单链接

    @Column(name = "menu_icon")
    private String menuIcon; // 菜单图标

    @Column(name = "sort_id")
    private Integer sortId=999; // 菜单排序id

    @Column(name = "is_show")
    private Boolean isShow=false; // 菜单是否显示

    @Column(name = "menu_grade")
    private Integer menuGrade=0; // 权限值分数
}
