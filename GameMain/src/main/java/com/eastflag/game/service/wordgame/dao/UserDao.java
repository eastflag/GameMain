package com.eastflag.game.service.wordgame.dao;

import com.eastflag.game.service.wordgame.vo.UserVo;

public interface UserDao {
	
	public UserVo getUser(String userId);

}
