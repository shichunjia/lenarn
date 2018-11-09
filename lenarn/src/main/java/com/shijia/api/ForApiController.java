package com.shijia.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shijia.dao.UserDao;

@Controller
@RequestMapping(value="/api")
public class ForApiController {

	@RequestMapping(value="/getUserInfo")
	public UserDao getUserInfo() {
		
		return null;
	}
}
