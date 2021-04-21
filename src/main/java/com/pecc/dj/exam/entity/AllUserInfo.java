package com.pecc.dj.exam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="exam_user")
public class AllUserInfo {

	@Id
	private String userId;			//审查代码
	
	@Column
	private String password;
	
	@Column
	private String userName;
	
	@Column
	private String jobNum;		//工号
	
	@Column
	private String department;	//部
	
	@Column
	private String office;		//科室
	
	@Column
	private String email;		//邮箱
	
	@Column
	private int userRole;		//用户权限，0是考官，1是考生
	
	@Column
	private String blank1;
	
	@Column
	private String blank2;
	
	@Column
	private String blank3;
	
	public AllUserInfo() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public String getBlank1() {
		return blank1;
	}

	public void setBlank1(String blank1) {
		this.blank1 = blank1;
	}

	public String getBlank2() {
		return blank2;
	}

	public void setBlank2(String blank2) {
		this.blank2 = blank2;
	}

	public String getBlank3() {
		return blank3;
	}

	public void setBlank3(String blank3) {
		this.blank3 = blank3;
	}
	
}
