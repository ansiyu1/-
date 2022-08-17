package cn.oasys.pojo.role;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name="aoa_role_")
//角色表
public class Role {

    @Id
    @Column(name="role_id")
    private Long roleId;//角色id

    @Column(name="role_name")
    private String roleName;//角色名

    @Column(name="role_value")
    private Integer  roleValue;//角色权限值
}
