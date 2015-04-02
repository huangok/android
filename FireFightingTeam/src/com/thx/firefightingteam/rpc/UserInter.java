package com.thx.firefightingteam.rpc;

import com.thx.firefightingteam.modle.User;

public interface UserInter {

	boolean login(User user);
	
	User query(int id);
}
