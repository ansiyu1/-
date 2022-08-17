package cn.oasys.pojo;

import cn.oasys.pojo.user.User;
import lombok.Data;

import javax.persistence.*;
@Data
@Table(name = "aoa_director")
public class Director {
    @Id
    @Column(name="director_id")
    private Integer  directorId; //主键

    @Column(name="user_name")
    private String userName;	//通讯录名称

    private String pinyin;		//通讯录名称的拼音

    private String  sex;		//性别

    @Column(name="phone_number")
    private String  phoneNumber;  //电话号码

    @Column(name="image_path")
    private Integer  attachment;	 //头像路径

    private String  remark;     //备注

    private String  address;	//用户住址

    @Column(name = "user_id")
    private Integer userId;		//由哪个用户创建的

    private String  email;		//邮件

    @Column(name="company_number")
    private String companyNumber;	//公司号码

    private String companyname;		//公司名称

}
