package com.ishareread.bbs.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ishareread.framework.shiro.service.PasswordService;
import com.ishareread.project.system.user.domain.User;
import com.ishareread.project.system.user.mapper.UserMapper;
import com.ishareread.project.system.user.service.IUserService;

@Service
public class BBSUserServiceImpl implements IBBSUserService {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 新增保存用户信息
	 * @param user 用户信息
	 * @return 结果
	 */
	@Override
	public int insertUser(User user) {
		user.randomSalt();
		user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
		// 新增用户信息
		int rows = userMapper.insertUser(user);
		return rows;
	}

	@Override
	public User selectUserByLoginName(String userName) {
		return userService.selectUserByLoginName(userName);
	}

	@Override
	public User selectUserByPhoneNumber(String phoneNumber) {
		return userService.selectUserByPhoneNumber(phoneNumber);
	}

	@Override
	public User selectUserByEmail(String email) {
		return userService.selectUserByEmail(email);
	}

	@Override
	public User selectUserById(Long userId) {
		return userService.selectUserById(userId);
	}

	@Override
	public int updateUser(User user) {
		return userService.updateUser(user);
	}

	@Override
	public int updateUserInfo(User user) {
		return userService.updateUserInfo(user);
	}

	@Override
	public int resetUserPwd(User user) {
		return userService.resetUserPwd(user);
	}

	@Override
	public String checkLoginNameUnique(String loginName) {
		return userService.checkLoginNameUnique(loginName);
	}

	@Override
	public String checkPhoneUnique(User user) {
		return userService.checkPhoneUnique(user);
	}

	@Override
	public String checkEmailUnique(User user) {
		return userService.checkEmailUnique(user);
	}

	@Override
	public String selectUserName(String loginName) {
		return userService.selectUserName(loginName);
	}
}
