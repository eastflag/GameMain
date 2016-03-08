package com.eastflag.game.service.wordgame.dao;

import com.eastflag.game.core.dao.CommonDaoImpl;
import com.eastflag.game.core.dao.DataMap;
import com.eastflag.game.service.wordgame.vo.UserVo;

public class UserDaoImpl extends CommonDaoImpl implements UserDao {
	
	private final String MAPPER_NS = "UserMapper.";

	public UserDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public UserVo getUser(String userId) {
		
		DataMap dataMap = new DataMap();
		dataMap.put("user_id",userId);
		
		UserVo user = selectOne(MAPPER_NS+"selectUser", dataMap, UserVo.class);
		
		return user;
	}
}
