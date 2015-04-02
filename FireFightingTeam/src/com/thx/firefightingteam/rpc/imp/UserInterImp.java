package com.thx.firefightingteam.rpc.imp;

import com.thx.firefightingteam.modle.User;
import com.thx.firefightingteam.rpc.UserInter;

public class UserInterImp implements UserInter{

	@Override
	public boolean login(User user) {
		System.out.println(user.getName()+" "+user.getPassword());
		return false;
	}

	@Override
	public User query(int id) {
		return null;
	}

}
