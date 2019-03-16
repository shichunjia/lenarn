package com.shijia.service;

import com.shijia.dao.UserDao;
import com.shijia.pojo.UserDto;

public interface MySerivceTest {
	
	 UserDto getUser();
	
	 UserDto getUserById();

	UserDto getUserByNum(int id);
	
}
