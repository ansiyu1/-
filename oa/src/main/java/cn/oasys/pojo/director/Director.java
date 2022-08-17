package cn.oasys.pojo.director;

import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;

/**
 * user_id
 * 外键没有连
 * @author admin
 *---通讯录表----
 */
@Data
@Entity
@Table(name="aoa_director")
public class Director {

    @Id
    @Column(name="director_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long  directorId; //主键

    @Column(name="user_name")
    private String userName;	//通讯录名称

    private String pinyin;		//通讯录名称的拼音

    private String  sex;		//性别

    @Column(name="phone_number")
    private String  phoneNumber;  //电话号码

    @Column(name="image_path")
    private Long  imagePath;	 //头像路径

    private String  remark;     //备注

    private String  address;	//用户住址

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;		//由哪个用户创建的

    private String  email;		//邮件

    @Column(name="company_number")
    private String companyNumber;	//公司号码

    private String companyname;		//公司名称
}
