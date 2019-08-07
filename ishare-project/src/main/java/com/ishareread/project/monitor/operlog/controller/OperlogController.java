package com.ishareread.project.monitor.operlog.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ishareread.common.annotation.Log;
import com.ishareread.common.base.AjaxResult;
import com.ishareread.common.enums.BusinessType;
import com.ishareread.common.page.TableDataInfo;
import com.ishareread.framework.web.controller.BaseController;
import com.ishareread.project.monitor.operlog.domain.OperLog;
import com.ishareread.project.monitor.operlog.service.IOperLogService;

/**
 * 操作日志记录
 * 
 *
 */
@Controller
@RequestMapping("/monitor/operlog")
public class OperlogController extends BaseController {
	private String prefix = "monitor/operlog";

	@Autowired
	private IOperLogService operLogService;

	@RequiresPermissions("monitor:operlog:view")
	@GetMapping()
	public String operlog() {
		return prefix + "/operlog";
	}

	@RequiresPermissions("monitor:operlog:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(OperLog operLog) {
		startPage();
		List<OperLog> list = operLogService.selectOperLogList(operLog);
		return getDataTable(list);
	}


	@RequiresPermissions("monitor:operlog:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(operLogService.deleteOperLogByIds(ids));
	}

	@RequiresPermissions("monitor:operlog:detail")
	@GetMapping("/detail/{operId}")
	public String detail(@PathVariable("operId") Long operId, ModelMap mmap) {
		mmap.put("operLog", operLogService.selectOperLogById(operId));
		return prefix + "/detail";
	}

	@Log(title = "操作日志", businessType = BusinessType.CLEAN)
	@RequiresPermissions("monitor:operlog:remove")
	@PostMapping("/clean")
	@ResponseBody
	public AjaxResult clean() {
		operLogService.cleanOperLog();
		return success();
	}
}