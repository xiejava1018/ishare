package com.ishareread.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ishareread.bbs.user.service.IBBSUserService;
import com.ishareread.common.annotation.Log;
import com.ishareread.common.base.AjaxResult;
import com.ishareread.common.enums.BusinessType;
import com.ishareread.common.utils.StringUtils;
import com.ishareread.framework.web.controller.BaseController;
import com.ishareread.project.system.user.domain.User;

@Controller
@RequestMapping("/user")
public class BBSUserController extends BaseController {
	
	@Autowired
	@Qualifier("BBSUserServiceImpl")
	private IBBSUserService userService;
	

	
	@GetMapping("/reg.html")
	public String showreg() {
		return "user/reg";
	}
	
	/**
	 * 新增注册用户
	 */
	@PostMapping("/add")
	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	public AjaxResult addSave(User user) {
		if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
			return error("不允许修改超级管理员用户");
		}
		return toAjax(userService.insertUser(user));
	}
	
	/**
	 * 校验用户名
	 */
	@PostMapping("/checkLoginNameUnique")
	@ResponseBody
	public String checkLoginNameUnique(User user) {
		return userService.checkLoginNameUnique(user.getLoginName());
	}

	/**
	 * 校验手机号码
	 */
	@PostMapping("/checkPhoneUnique")
	@ResponseBody
	public String checkPhoneUnique(User user) {
		return userService.checkPhoneUnique(user);
	}

	/**
	 * 校验email邮箱
	 */
	@PostMapping("/checkEmailUnique")
	@ResponseBody
	public String checkEmailUnique(User user) {
		return userService.checkEmailUnique(user);
	}
	
}
