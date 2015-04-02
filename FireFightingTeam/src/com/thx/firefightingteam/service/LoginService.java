package com.thx.firefightingteam.service;

import android.util.Log;

import com.thx.firefightingteam.modle.User;
import com.thx.firefightingteam.rpc.UserInter;
import com.thx.firefightingteam.tools.GlobalTools;

public class LoginService {

	
	public void testLogin(){
		UserInter login=GlobalTools.openLoginProxy();
		User user=new User();
		user.setName("HUnagok");
		user.setPassword("123");
		login.login(user);
	}
	
	public void query(){
		UserInter login=GlobalTools.openLoginProxy();
		User user=	login.query(1);
		Log.i("message", user+"");
	}
}
