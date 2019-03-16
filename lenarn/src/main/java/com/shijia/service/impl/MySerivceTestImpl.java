package com.shijia.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shijia.dao.UserDao;
import com.shijia.pojo.UserDto;
import com.shijia.service.MySerivceTest;

@Service
public class MySerivceTestImpl implements MySerivceTest{
	@Autowired
	private UserDao  userDao;
	private static final Logger log=LoggerFactory.getLogger(MySerivceTestImpl.class);
	public UserDto getUser() {
		log.info("查询用户");
		UserDto user=new UserDto();
		user.setId("10001");
		user.setUserName("张三");
		user.setPassword("1254525");
		return user;
	}
	public UserDto getUserById() {
		Map<String ,String> map=new HashMap<String, String>();
		map.put("id", "1563");
		return userDao.getUserById(map);
	}

	public UserDto getUserByNum(int id) {
		return userDao.getUserByNum(id);
	}
	

}
