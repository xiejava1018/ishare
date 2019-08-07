package com.ishareread.project.system.file.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ishareread.common.support.Convert;
import com.ishareread.project.system.file.domain.File;
import com.ishareread.project.system.file.mapper.FileMapper;
import com.ishareread.project.system.file.service.IFileService;

/**
 * 上传文件 服务层实现
 * 
 * @author sl
 * @date 2018-12-24
 */
@Service
public class FileServiceImpl implements IFileService {
	@Autowired
	private FileMapper fileMapper;

	/**
	 * 查询上传文件信息
	 * 
	 * @param id 上传文件ID
	 * @return 上传文件信息
	 */
	@Override
	public File selectFileById(Long id) {
		return fileMapper.selectFileById(id);
	}

	/**
	 * 查询上传文件列表
	 * 
	 * @param file 上传文件信息
	 * @return 上传文件集合
	 */
	@Override
	public List<File> selectFileList(File file) {
		return fileMapper.selectFileList(file);
	}

	/**
	 * 新增上传文件
	 * 
	 * @param file 上传文件信息
	 * @return 结果
	 */
	@Override
	public int insertFile(File file) {
		return fileMapper.insertFile(file);
	}

	/**
	 * 修改上传文件
	 * 
	 * @param file 上传文件信息
	 * @return 结果
	 */
	@Override
	public int updateFile(File file) {
		return fileMapper.updateFile(file);
	}

	/**
	 * 删除上传文件对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteFileByIds(String ids) {
		return fileMapper.deleteFileByIds(Convert.toStrArray(ids));
	}

}
