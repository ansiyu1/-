package cn.oasys.pojo.notice;

import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;

/**
 * 此处有一个parentid需要连接
 * @author admin
 * ---目录表----
 *
 */
@Data
@Entity
@Table(name="aoa_catalog")
public class Catalog {

	@Id
	@Column(name="catalog_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long catalogId; //目录id
	
	
	
	@Column(name="catalog_name")
	private String catalogName; //目录名字
	
	@ManyToOne
	@JoinColumn(name="cata_user_id")
	private User cataUserId;
	
	//判断id
	@Column(name="parent_id")
	private Integer parentId;

	
	
}
