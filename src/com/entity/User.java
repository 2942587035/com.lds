package com.entity;

import java.io.Serializable;

/**
 * 用户表 不可编辑
 * 
 * @author lds
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer yh_id;
	//编码
	private String yh_code;
	//用户名
	private String yh_name;
	//密码
	private String yh_password;
	//类型
	private Integer yh_type;
	
	public User() {
		super();
	}
	
	public Integer getYh_id() {
		return yh_id;
	}

	public void setYh_id(Integer yh_id) {
		this.yh_id = yh_id;
	}

	public String getYh_code() {
		return yh_code;
	}

	public void setYh_code(String yh_code) {
		this.yh_code = yh_code;
	}

	public String getYh_name() {
		return yh_name;
	}

	public void setYh_name(String yh_name) {
		this.yh_name = yh_name;
	}

	public String getYh_password() {
		return yh_password;
	}

	public void setYh_password(String yh_password) {
		this.yh_password = yh_password;
	}

	public Integer getYh_type() {
		return yh_type;
	}

	public void setYh_type(Integer yh_type) {
		this.yh_type = yh_type;
	}

}
