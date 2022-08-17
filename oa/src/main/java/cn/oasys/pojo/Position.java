package cn.oasys.pojo;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "aoa_position")
public class Position {

    @Id
    @Column(name = "position_id")
    private Long positionId;	//职位id

    @Column(unique = true)
    private String name;	//职位名称。

    private Integer level;	//职位层级

    private String describtion;//职位描述

    private Long deptid;
}
