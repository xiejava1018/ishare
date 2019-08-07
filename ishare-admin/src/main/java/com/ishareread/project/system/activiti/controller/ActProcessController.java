package com.ishareread.project.system.activiti.controller;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.repository.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ishareread.common.annotation.Log;
import com.ishareread.common.base.AjaxResult;
import com.ishareread.common.enums.BusinessType;
import com.ishareread.common.page.TableDataInfo;
import com.ishareread.common.utils.StringUtils;
import com.ishareread.framework.web.controller.BaseController;
import com.ishareread.project.system.activiti.domain.ProcessDefinitionDto;
import com.ishareread.project.system.activiti.service.ActProcessService;

/**
 * 流程管理 操作处理
 */
@Controller
@RequestMapping("/activiti/process")
public class ActProcessController extends BaseController {
	private String prefix = "activiti/process";

	@Autowired
	private ActProcessService actProcessService;

	@RequiresPermissions("activiti:process:view")
	@GetMapping
	public String process() {
		return prefix + "/process";
	}

	@RequiresPermissions("activiti:process:list")
	@PostMapping("list")
	@ResponseBody
	public TableDataInfo list(ProcessDefinitionDto processDefinitionDto) {
		return actProcessService.selectProcessDefinitionList(processDefinitionDto);
	}

	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	@RequiresPermissions("activiti:process:add")
	@Log(title = "流程管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestParam String category, @RequestParam("file") MultipartFile file)
			throws IOException {
		InputStream fileInputStream = file.getInputStream();
		String fileName = file.getOriginalFilename();
		return actProcessService.saveNameDeplove(fileInputStream, fileName, category);
	}

	@RequiresPermissions("activiti:process:model")
	@GetMapping(value = "/convertToModel/{processId}")
	@ResponseBody
	public com.ishareread.common.base.AjaxResult convertToModel(@PathVariable("processId") String processId) {
		try {
			Model model = actProcessService.convertToModel(processId);
			return success(StringUtils.format("转换模型成功，模型编号[{}]", model.getId()));
		} catch (Exception e) {
			return error("转换模型失败" + e.getMessage());
		}
	}

	@GetMapping(value = "/resource/{imageName}/{deploymentId}")
	public void viewImage(@PathVariable("imageName") String imageName,
			@PathVariable("deploymentId") String deploymentId, HttpServletResponse response) {
		try {
			InputStream in = actProcessService.findImageStream(deploymentId, imageName);
			for (int bit = -1; (bit = in.read()) != -1;) {
				response.getOutputStream().write(bit);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions("activiti:process:remove")
	@PostMapping("/remove")
	@ResponseBody
	public com.ishareread.common.base.AjaxResult remove(String ids) {
		return toAjax(actProcessService.deleteProcessDefinitionByDeploymentIds(ids));
	}
}
