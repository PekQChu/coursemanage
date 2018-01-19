package com.shu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shu.cpa.utility.utility;



/**
 * @author Administrator
 * uid  20+临时用户
 *
 */
@Entity
@Table(name = "user")
public class User implements Serializable{
	private static final long serialVersionUID = -8366929034564774130L;

	public static String SESSION_PERMISSION = "session_permision";
	public static int SUPERADMIN=1000;
	public static int ADMIN=900;
	public static int TEACHER=800;
	public static int STUDENT=700;
	public  static  int TempUser=600;//临时用户
	public  static  int AwayStuUser=650;//离校�?
	public static String CUR_USER = "cur_user";
	public static int VerySchool=0;
	public static int VeryOut=1;

	public User() {
		super();
		ensureId();
		very_type = 0;
		fob = false;
		loginCount = 0;
		createDate = new Date();
		company="上海大学";
		part = "-" ;

	}
	
	public User(String uid, String name, String password, int very_type,int userType,String part,String company) {
		super();
		ensureId();
		this.uid = uid;
		this.userName = name;
		this.setPassword(password);
		this.very_type = very_type;
		this.fob = false;
		this.loginCount = 0;
		this.createDate = new Date();
		this.userType = userType;
		this.company = company==null?"上海大学":company;
		this.part = part ;
	}
	
	@PrePersist
	// 生成String类型的id
	private User ensureId() {
		this.setId(UUID.randomUUID().toString());return this;
	}
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "username")
	private String userName;				// 姓名
	private String nickName;
	@Column(name = "uid")
	private String uid; 				   // 学号
	@Column(name = "password")
	private String password; 				// 登陆密码
	@Column(name = "very_type")
	private int very_type; 				// 验证方式 0-校内 1-校外
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "loginDate")
	private Date loginDate; 				// 登录时间
	@Column(name = "loginCount")
	private Integer loginCount; 			// 登录计数
	@Column(name = "fob")
	private boolean fob; 					// 禁止登录
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	private Date createDate;				// 创建时间
	@Column(name = "userType")
	private int userType;//用户类型  教师、学生�?�企业人员�?�管理员、超级管理员
	@Column(name = "email")
	private String email; 				// 邮箱
	@Column(name = "phone")
	private String phone; 				// 电话
	@Column(name = "company")
	private String company; 
	@Column(name = "part")// 公司
	private String part; 				// 部门、专�?
	@Column(name = "sex")// 公司
	private boolean sex; 				// 1-�? 2-�?
	@Column(name = "headImg")// 公司
	private String headImg; 				// 部门、专�?
	private String address;
	public String getHeadImg() {
		if(headImg==null) {
			return "img/user.jpg";
		}
		return headImg;
	}

	public User setHeadImg(String headImg) {
		this.headImg = headImg;return this;
	}

	public boolean isMale() {
		return sex;
	}

	public User setMale(boolean sex) {
		this.sex = sex;return this;
	}

	public String getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getUid() {
		return uid;
	}

	public String getPassword() {
		return password;
	}
	
	public int getVeryType() {
		return very_type;
	}
	
	public Date getLoginDate() {
		return loginDate;
	}
	
	public Integer getLoginCount() {
		return loginCount;
	}
	
	public boolean isFob() {
		return fob;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public User setId(String id) {
		this.id = id;return this;
	}

	public User setUserName(String userName) {
		this.userName = userName;return this;
	}

	public User setUid(String uid) {
		this.uid = uid;return this;
	}

	public User setPassword(String password) {
		this.password = utility.GetMD5Code(password)+utility.GetMD5Code(password);return this;
	}

	public User setType(int type) {
		this.very_type = type;return this;
	}

	public User setLoginDate(Date loginDate) {
		this.loginDate = loginDate;return this;
	}

	public User setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;return this;
	}

	public User setFob(boolean fob) {
		this.fob = fob;return this;
	}
	public User setCreateDate(Date createDate) {
		this.createDate = createDate;return this;
	}

	public int getVery_type() {
		return very_type;
	}

	public User setVery_type(int very_type) {
		this.very_type = very_type;return this;
	}

	public long getUserType() {
		return userType;
	}

	public User setUserType(int userType) {
		this.userType = userType;return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;return this;
	}

	public String getPhone() {
		return phone;
	}

	public User setPhone(String phone) {
		this.phone = phone;return this;
	}

	public String getCompany() {
		return company;
	}

	public User setCompany(String company) {
		this.company = company;return this;
	}

	public String getPart() {
		return part;
	}

	public User setPart(String part) {
		this.part = part;return this;
	}

	public boolean passEquel(String password2) {
		// TODO Auto-generated method stub
		if(password.equals(utility.GetMD5Code(password2)+utility.GetMD5Code(password2)))return true;
		return false;
	}
	public String getTypeStr() {
		if(userType==User.ADMIN) {
			return "普�?�管理员";
		}
		else if(userType==User.STUDENT) {
			return "学生用户";
		}else if(userType==User.SUPERADMIN) {
			return "超级管理员";
		}else if(userType==User.TEACHER) {
			return "教师用户";
		}else if(userType==User.TempUser) {
			return "临时用户";
		}else if(userType==User.AwayStuUser) {
			return "离校学生";
		}else {
			return "状�?�错�?";
		}
	}
	
//	用户配置模块
	

	public boolean isSex() {
		return sex;
	}

	public User setSex(boolean sex) {
		this.sex = sex;return this;
	}

	

	public String getNickName() {
		return nickName;
	}

	public User setNickName(String nickName) {
		this.nickName = nickName;
		return this;
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setPassword("tx@2017");
		System.err.println(user.getPassword());
	}

	public String getAddress() {
		return address;
	}

	public User setAddress(String address) {
		this.address = address;
		return this;
	}
	
}
