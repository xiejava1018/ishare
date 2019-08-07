package com.ishareread.project.system.file.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ishareread.common.base.BaseEntity;

import java.util.Date;

/**
 * 上传文件表 tb_file
 * 
 * @author sl
 * @date 2018-12-24
 */
public class File extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Long id;
	/** app md5 */
	private String fileMd5;
	/** 新文件件名 */
	private String fileName;
	/** 原文件名 */
	private String fileOrg;
	/** 文件大小 */
	private Long fileSize;
	/** 文件路径 */
	private String filePath;
	/** 文件类型(后缀) */
	private String fileType;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileOrg(String fileOrg) {
		this.fileOrg = fileOrg;
	}

	public String getFileOrg() {
		return fileOrg;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("fileName", getFileName()).append("fileOrg", getFileOrg()).append("fileSize", getFileSize())
				.append("filePath", getFilePath()).append("fileType", getFileType()).append("createBy", getCreateBy())
				.append("createTime", getCreateTime()).toString();
	}
}
