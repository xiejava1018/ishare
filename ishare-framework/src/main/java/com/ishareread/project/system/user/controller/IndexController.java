package com.ishareread.project.system.user.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ishareread.framework.config.IshareConfig;
import com.ishareread.framework.web.controller.BaseController;
import com.ishareread.project.system.menu.domain.Menu;
import com.ishareread.project.system.menu.service.IMenuService;
import com.ishareread.project.system.user.domain.User;

/**
 * 首页 业务处理
 * 
 *
 */
@Controller
public class IndexController extends BaseController
{
    @Autowired
    private IMenuService menuService;

    @Autowired
    private IshareConfig ishareConfig;

    // 系统首页
    @GetMapping("/system/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        User user = getSysUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", ishareConfig.getCopyrightYear());
        return "/system/index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", ishareConfig.getVersion());
        return "/system/main";
    }
}
