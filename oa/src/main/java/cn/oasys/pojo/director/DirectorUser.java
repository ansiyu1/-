package cn.oasys.pojo.director;


import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name="aoa_director_users")
public class DirectorUser {
	@Id
	@Column(name="director_users_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long directorUserId;
	
	@ManyToOne
	@JoinColumn(name="director_id")
	private Director directorId;

	@Column(name = "is_handle")
	private  Boolean isHandle=false;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User userId;
	
	@ManyToOne
	@JoinColumn(name="share_user_id")
	private User shareUserId;
	
	@Column(name="catelog_name")
	private String catelogName;
	
	private Date sharetime=new Date();

	
}
