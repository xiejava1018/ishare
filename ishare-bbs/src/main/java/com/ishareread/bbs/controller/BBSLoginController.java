package com.ishareread.bbs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ishareread.common.base.AjaxResult;
import com.ishareread.common.utils.ServletUtils;
import com.ishareread.common.utils.StringUtils;
import com.ishareread.framework.web.controller.BaseController;

@Controller
public class BBSLoginController extends BaseController{
	
	@GetMapping("/bbs/login.html")
	public String showlogin(HttpServletRequest request, HttpServletResponse response) {
		// 如果是Ajax请求，返回Json字符串。
		if (ServletUtils.isAjaxRequest(request)) {
			return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
		}
		return "user/login";
	}
	
	@PostMapping("/bbs/login")
	@ResponseBody
	public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return success("sucess111");
		} catch (AuthenticationException e) {
			String msg = "用户或密码错误";
			if (StringUtils.isNotEmpty(e.getMessage())) {
				msg = e.getMessage();
			}
			return error(msg);
		}
		
		/*
		 * if (password.equals("1111")) { return success("sucess1111"); } else { return
		 * error("errormsg"); }
		 */
	}
}
