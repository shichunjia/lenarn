package com.shijia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/login")
public class MyController {

	@ResponseBody
	@RequestMapping(value="")
	public String  login() {
		return "1111你好";
	}
}
