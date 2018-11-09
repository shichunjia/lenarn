package com.shijia.dao;

import java.util.Map;

import com.shijia.pojo.UserDto;

public interface UserDao {
	
	public UserDto getUserById(Map<String ,String> map);
}
