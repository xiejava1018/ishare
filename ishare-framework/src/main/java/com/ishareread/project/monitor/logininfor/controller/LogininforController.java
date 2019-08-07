package com.ishareread.project.monitor.logininfor.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ishareread.common.annotation.Log;
import com.ishareread.common.base.AjaxResult;
import com.ishareread.common.enums.BusinessType;
import com.ishareread.common.page.TableDataInfo;
import com.ishareread.common.utils.poi.ExcelUtil;
import com.ishareread.framework.web.controller.BaseController;
import com.ishareread.project.monitor.logininfor.domain.Logininfor;
import com.ishareread.project.monitor.logininfor.service.ILogininforService;

/**
 * 系统访问记录
 * 
 *
 */
@Controller
@RequestMapping("/monitor/logininfor")
public class LogininforController extends BaseController {
	private String prefix = "monitor/logininfor";

	@Autowired
	private ILogininforService logininforService;

	@RequiresPermissions("monitor:logininfor:view")
	@GetMapping()
	public String logininfor() {
		return prefix + "/logininfor";
	}

	@RequiresPermissions("monitor:logininfor:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Logininfor logininfor) {
		startPage();
		List<Logininfor> list = logininforService.selectLogininforList(logininfor);
		return getDataTable(list);
	}

	@Log(title = "登陆日志", businessType = BusinessType.EXPORT)
	@RequiresPermissions("monitor:logininfor:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(Logininfor logininfor) {
		List<Logininfor> list = logininforService.selectLogininforList(logininfor);
		ExcelUtil<Logininfor> util = new ExcelUtil<Logininfor>(Logininfor.class);
		return util.exportExcel(list, "logininfor");
	}

	@RequiresPermissions("monitor:logininfor:remove")
	@Log(title = "登陆日志", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(logininforService.deleteLogininforByIds(ids));
	}

	@RequiresPermissions("monitor:logininfor:remove")
	@Log(title = "登陆日志", businessType = BusinessType.CLEAN)
	@PostMapping("/clean")
	@ResponseBody
	public AjaxResult clean() {
		logininforService.cleanLogininfor();
		return success();
	}
}
