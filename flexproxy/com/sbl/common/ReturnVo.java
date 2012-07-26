package com.sbl.common;

import java.util.ArrayList;

public class ReturnVo {
	private ArrayList<Object> returnVo;
	
	private UserInf userVo;
	
	public ReturnVo()
	{
		this.returnVo = new ArrayList<Object>();
		
		this.userVo = new UserInf();
	}

	public ArrayList<Object> getReturnVo() {
		return returnVo;
	}

	public void setReturnVo(ArrayList<Object> returnVo) {
		this.returnVo = returnVo;
	}

	public UserInf getUserVo() {
		return userVo;
	}

	public void setUserVo(UserInf userVo) {
		this.userVo = userVo;
	}
	
	public void addObj(Object obj)
	{
		this.returnVo.add(obj);
	}
	
	public void setUserid(String userid)
	{
		this.userVo.setUserid(userid);
	}
	
	public void setPasswd(String passwd)
	{
		this.userVo.setPasswd(passwd);
	}
}
