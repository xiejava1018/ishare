package com.ishareread.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 *
 */
@Component
@ConfigurationProperties(prefix = "ishare")
public class IshareConfig {
	/** 项目名称 */
	private String name;
	/** 版本 */
	private String version;
	/** 版权年份 */
	private String copyrightYear;
	/** 上传路径 */
	private static String profile;
	/** 获取地址开关 */
	private static boolean addressEnabled;
	/**本机host*/
	private static String host;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCopyrightYear() {
		return copyrightYear;
	}

	public void setCopyrightYear(String copyrightYear) {
		this.copyrightYear = copyrightYear;
	}

	public static String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		IshareConfig.profile = profile;
	}

	public static boolean isAddressEnabled() {
		return addressEnabled;
	}
	
	public static String getNowHost() {
		return host;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setAddressEnabled(boolean addressEnabled) {
		IshareConfig.addressEnabled = addressEnabled;
	}
	

	public static String getAvatarPath() {
		return profile + "avatar/";
	}
	
	/**
	 * 上传临界时文件目录
	 * @return
	 */
	public static String getTmpPath() {
		return profile + "tmp/";
	}
	
	/**
	 * 产品模板目录
	 * @return
	 */
	public static String getModelPath() {
		return profile + "model/";
	}
	
	/**
	 *佐证材料目录
	 * @return
	 */
	public static String getSupMaterialsPath() {
		return profile + "supMaterials/";
	}

	/**
	 * 下载文件目录
	 * @return
	 */
	public static String getDownloadPath() {
		return profile + "download/";
	}
	
	/**
	 * apk目录
	 * @return
	 */
	public static String getApkPath() {
		return profile + "apk/";
	}
	
}
