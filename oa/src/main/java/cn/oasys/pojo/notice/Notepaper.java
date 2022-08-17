package cn.oasys.pojo.notice;

import cn.oasys.pojo.user.User;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;

import javax.persistence.*;
import java.util.Date;

/**
 * 便签表
 */
@Data
@Entity
@Table(name="aoa_notepaper")
public class Notepaper {


    @Id
    @Column(name="notepaper_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long  notepaperId;			//主键id

    private String title;				//便签标题

    @Column(columnDefinition="text")	//便签内容
    private String concent;


    @Column(name="notepaper_user_id")
    private Long notepaperUserId;				//编写便签的用户

    @Column(name="create_time")
    private Date createTime;			//便签创建时间
}
