package com.ishareread.project.system.file.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import com.ishareread.common.utils.Md5Utils;
import com.ishareread.common.utils.file.FileUploadUtils;
import com.ishareread.common.utils.file.FileUtils;
import com.ishareread.common.utils.poi.ExcelUtil;
import com.ishareread.framework.config.IshareConfig;
import com.ishareread.framework.web.controller.BaseController;
import com.ishareread.project.system.file.domain.File;
import com.ishareread.project.system.file.service.IFileService;

/**
 * 上传文件 信息操作处理
 * 
 * @author sl
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/system/file")
public class FileController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(FileController.class);
	
	private String prefix = "system/file";

	@Autowired
	private IFileService fileService;

	@RequiresPermissions("system:file:view")
	@GetMapping()
	public String file() {
		return prefix + "/file";
	}

	/**
	 * 查询上传文件列表
	 */
	@RequiresPermissions("system:file:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(File file) {
		startPage();
		List<File> list = fileService.selectFileList(file);
		return getDataTable(list);
	}

	/**
	 * 导出上传文件列表
	 */
	@RequiresPermissions("system:file:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(File file) {
		List<File> list = fileService.selectFileList(file);
		ExcelUtil<File> util = new ExcelUtil<File>(File.class);
		return util.exportExcel(list, "file");
	}

	/**
	 * 新增上传文件
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存上传文件
	 */
	@RequiresPermissions("system:file:add")
	@Log(title = "上传文件", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(File file) {
		return toAjax(fileService.insertFile(file));
	}

	/**
	 * 修改上传文件
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		File file = fileService.selectFileById(id);
		mmap.put("file", file);
		return prefix + "/edit";
	}

	/**
	 * 修改保存上传文件
	 */
	@RequiresPermissions("system:file:edit")
	@Log(title = "上传文件", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(File file) {
		return toAjax(fileService.updateFile(file));
	}

	/**
	 * 删除上传文件
	 */
	@RequiresPermissions("system:file:remove")
	@Log(title = "上传文件", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(fileService.deleteFileByIds(ids));
	}
	
	
	/**
	 * 公共上传文件
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	@ResponseBody
	public AjaxResult fileUpload(@RequestParam("file") MultipartFile file,@RequestParam("folder") String folder) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if (!file.isEmpty()) {
				//原文件名
				String oldFileName = file.getOriginalFilename();
				//文件后缀(文件类型)
				String extension = oldFileName.substring(oldFileName.lastIndexOf("."),oldFileName.length());
				
				//默认上传文件目录
				String path = IshareConfig.getTmpPath();
				if("model".equals(folder)) {
					//产品模板路径
					path = IshareConfig.getModelPath();
				}else if("supMaterials".equals(folder)) {
					//佐证材料目录
					path = IshareConfig.getSupMaterialsPath();
				}else if("apk".equals(folder)) {
					//apk目录
					path = IshareConfig.getApkPath();
				}else {
					path = IshareConfig.getDownloadPath();
				}
				
				//上传后文件名
				String fileName = FileUploadUtils.upload(path, file,extension);
				
				File entity = new File(); 
				entity.setFileName(fileName);
				entity.setFileOrg(oldFileName);
				entity.setFileSize(file.getSize());
				entity.setFilePath(path);
				entity.setFileType(extension);
				entity.setCreateBy(getLoginName());
				entity.setCreateTime(new Date());
				entity.setFileMd5(Md5Utils.getFileMd5Value(path,fileName));
				fileService.insertFile(entity);
				
				ajaxResult.put("code", 0);
				ajaxResult.put("msg", "上传成功");
				//返回文件部份信息
				ajaxResult.put("id", entity.getId());
				ajaxResult.put("fileName", fileName);
				ajaxResult.put("fileOrg", oldFileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.put("code", 1);
			ajaxResult.put("msg", "上传失败");
		}
		return ajaxResult;
	}


	/**
	 * 下载文件
	 * @param fileName
	 * @param delete
	 * @param response
	 * @param request
	 */
	@RequestMapping("/download")
	public void fileDownload(Long id, HttpServletResponse response,HttpServletRequest request) {
		try {
			File file = fileService.selectFileById(id);
			String filePath = file.getFilePath() + file.getFileName();
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition","attachment;fileName=" + setFileDownloadHeader(request, file.getFileOrg()));
			FileUtils.writeBytes(filePath, response.getOutputStream());
		} catch (Exception e) {
			log.error("下载文件失败");
			e.printStackTrace();
		}
	}
}
