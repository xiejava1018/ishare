package com.ishareread.framework.web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ishareread.project.system.dept.domain.Dept;
import com.ishareread.project.system.dept.service.IDeptService;
import com.ishareread.project.system.dict.domain.DictData;
import com.ishareread.project.system.dict.service.IDictDataService;
import com.ishareread.project.system.user.domain.User;
import com.ishareread.project.system.user.service.IUserService;

/**
 * html调用 thymeleaf 实现字典读取
 * 
 *
 */
@Service("dict")
public class DictService {
	@Autowired
	private IDictDataService dictDataService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
    private IDeptService deptService;
	
	/**
	 * 根据字典类型查询字典数据信息
	 * 
	 * @param dictType 字典类型
	 * @return 参数键值
	 */
	public List<DictData> getType(String dictType) {
		return dictDataService.selectDictDataByType(dictType);
	}

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 * 
	 * @param dictType  字典类型
	 * @param dictValue 字典键值
	 * @return 字典标签
	 */
	public String getLabel(String dictType, String dictValue) {
		return dictDataService.selectDictLabel(dictType, dictValue);
	}
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<User> getUser() {
		User user = new User();
		return userService.selectUserList(user);
	}
	
	/**
	 * 获取所有部门
	 * @return
	 */
	public List<Dept> getDept(){
		Dept dept = new Dept();
		return deptService.selectDeptList(dept);
	}
	
	
}
