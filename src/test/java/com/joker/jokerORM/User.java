package com.joker.jokerORM;

import com.joker.jokerORM.util.Entity;
import com.joker.jokerORM.util.MajorKey;

public class User {
	
	@MajorKey(value="userId")
	private int userId;
	private String password;
	@Entity(value="hotinfo")
	private Hotinfo hotInfo;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Hotinfo getHotInfo() {
		return hotInfo;
	}
	public void setHotInfo(Hotinfo hotInfo) {
		this.hotInfo = hotInfo;
	}
	
	
}
