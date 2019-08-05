package com.ishareread.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/login.html")
	public String showlogin() {
		return "user/login";
	}
	
	@GetMapping("/reg.html")
	public String showreg() {
		return "user/reg";
	}
}
