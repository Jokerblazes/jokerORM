package com.joker.jokerORM;

import com.joker.jokerORM.util.MajorKey;

public class User {
	@MajorKey(value="userid")
	private int userid;
	private String password;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
