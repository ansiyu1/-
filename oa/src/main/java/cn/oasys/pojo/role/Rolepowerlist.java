package cn.oasys.pojo.role;

import cn.oasys.pojo.SysMenu;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name="aoa_role_power_list")
public class Rolepowerlist {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="pk_id")
    private Long pkId;


    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role roleId;

    @ManyToOne()
    @JoinColumn(name = "menu_id")
    private SysMenu menuId;


    @Column(name="is_show")
    private Boolean check=false;
}
