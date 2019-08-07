package com.ishareread.project.system.file.mapper;

import java.util.List;

import com.ishareread.project.system.file.domain.File;

/**
 * 上传文件 数据层
 * 
 * @author sl
 * @date 2018-12-24
 */
public interface FileMapper {
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
	 * 删除上传文件
	 * 
	 * @param id 上传文件ID
	 * @return 结果
	 */
	public int deleteFileById(Long id);

	/**
	 * 批量删除上传文件
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteFileByIds(String[] ids);

}