package cn.oasys.pojo.user;

import cn.oasys.pojo.*;
import cn.oasys.pojo.role.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="aoa_user")
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;		//用户id

    @Column(name="user_name")
    @NotEmpty(message="用户名不能为空")
    private String userName;	//登录用户名

    @Column(name="user_tel")
    @NotEmpty(message="电话不能为空")
    private String userTel;		//用户电话

    @Column(name="real_name")
    @NotEmpty(message="真实姓名不能为空")
    private String realName;    //真实姓名

    private String pinyin;

    @NotEmpty(message="邮箱不能为空")
    @Pattern(regexp="^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$",message="请填写正确邮箱号")
    private String eamil;		//邮件

    @NotEmpty(message="地址不能为空")
    private String address;		//地址

    @Column(name="user_edu")
    @NotEmpty(message="学历不能为空")
    private String userEdu;		//用户学历


    private Boolean superman=false;

    @Column(name="user_school")
    @NotEmpty(message="毕业院校不能为空")
    private String userSchool;		//学校

    @Column(name="user_idcard")
    @Pattern(regexp="^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$",message="请填写正确身份证号")
    private String idCard;		//用户身份证

    @NotEmpty(message="卡号不能为空")
    @Length(min=16, max=19,message="银行卡号长度必须在16到19之间!")
    private String bank;		//银行

    private String sex;			//性别

    @Column(name="theme_skin")
    private String themeSkin;	//主题皮肤

    private Date birth;			//生日

    @Column(name="user_sign")
    private String userSign;	//用户签名

    private String password;	//用户密码

    private String salary;		//用户薪水

    @Column(name="img_path")
    private String imgPath;		//用户头像路径

    @Column(name="hire_time")
    private Date hireTime;		//入职时间

    @Column(name="is_lock")
    private Integer isLock=0;		//该用户是否被禁用

    @Column(name="last_login_ip")
    private String lastLoginIp;	//用户最后登录ip；

    @Column(name="last_login_time")
    private Date lastLoginTime;	//最后登录时间

    @Column(name="modify_time")
    private Date modifyTime;		//最后修改时间

    @Column(name="modify_user_id")
    private Long modifyUserId;	//最后修改此用户的用户id

    @Column(name="father_id")
    private Long fatherId;		//上司id

    private Integer holiday;   //请假天数

    @ManyToOne()
    @JoinColumn(name = "position_id")
    private Position positionId;	//外键关联 职位表

    @ManyToOne()
    @JoinColumn(name = "dept_id")
    private Dept deptId;			//外键关联 部门表

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role roleId;			//外键关联 角色表


    @ManyToMany(mappedBy = "users")
    private List<ScheduleList> scheduleLists;

//    @ManyToMany(mappedBy = "users")
//    private List<Reply> replys;

    @ManyToMany(mappedBy = "users")
    private List<Discuss> discuss;

//    @ManyToMany(mappedBy = "userss")
//    private List<Note> note;

    @OneToMany(mappedBy="attendsUserId",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Set<AttendsList> aSet;
}
