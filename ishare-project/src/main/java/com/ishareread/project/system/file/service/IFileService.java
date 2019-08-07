package com.ishareread.project.system.file.service;

import java.util.List;

import com.ishareread.project.system.file.domain.File;

/**
 * 上传文件 服务层
 * 
 * @author sl
 * @date 2018-12-24
 */
public interface IFileService {
	/**
	 * 查询上传文件信息
	 * 
	 * @param id 上传文件ID
	 * @return 上传文件信息
	 */
	public File selectFileById(Long id);

	/**
	 * 查询上传文件列表
	 * 
	 * @param file 上传文件信息
	 * @return 上传文件集合
	 */
	public List<File> selectFileList(File file);

	/**
	 * 新增上传文件
	 * 
	 * @param file 上传文件信息
	 * @return 结果
	 */
	public int insertFile(File file);

	/**
	 * 修改上传文件
	 * 
	 * @param file 上传文件信息
	 * @return 结果
	 */
	public int updateFile(File file);

	/**
	 * 删除上传文件信息
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteFileByIds(String ids);

}
