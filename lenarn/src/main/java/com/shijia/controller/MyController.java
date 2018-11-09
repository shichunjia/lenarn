package com.shijia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shijia.pojo.UserDto;
import com.shijia.service.MySerivceTest;

@Controller
@RequestMapping(value="/login")
public class MyController {
	
	private static final Logger log=LoggerFactory.getLogger(MyController.class);
	@Autowired
	private MySerivceTest myservice;
	
	@ResponseBody
	@RequestMapping(value="")
	public UserDto  login() {
		return myservice.getUser();
	}
	
	@ResponseBody
	@RequestMapping(value="/b")
	public String  a() {
		return "aaaaa";
	}
	
	@ResponseBody
	@RequestMapping(value="/a")
	public UserDto  getUserById() {
		log.info("数据库查询用户开始");
		return myservice.getUserById();
	}
}
