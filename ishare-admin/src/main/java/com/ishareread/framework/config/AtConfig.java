package com.ishareread.framework.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ishareread.common.utils.Md5Utils;
import com.ishareread.project.system.file.domain.File;

/**
 * 读取项目相关配置
 */
@Component
@ConfigurationProperties(prefix = "at")
public class AtConfig {
	public  String url;

	public  String username;

	public  String password;
	
	public  String tpl;

	public  String apkurl;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTpl() {
		return tpl;
	}

	public void setTpl(String tpl) {
		this.tpl = tpl;
	}

	public String getApkurl() {
		return apkurl;
	}

	public void setApkurl(String apkurl) {
		this.apkurl = apkurl;
	}
	
	/**
	 * 封装安天检查请求参数
	 * @param file
	 * @return
	 */
	public String getJsonAt(File file) {
		JSONObject url = new JSONObject();
		url.put("url", IshareConfig.getNowHost()+"/system/file/download?id="+file.getId());
		url.put("md5", file.getFileMd5());
		
		JSONArray scanlist = new JSONArray();
		scanlist.add(url);
		
		JSONObject request = new JSONObject();
		request.put("scanlist", scanlist);
		request.put("priority", "1");
		
		JSONObject obj = new JSONObject();
		obj.put("request", request);
		return obj.toString();
	}
	
	
	/* 获取AT请求签名
	 * @param params 参数
	 * @return 返回签名
	 */
	public String getSign(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		List<String> keys = new ArrayList<String>();
		Set<Entry<String, String>> entries = params.entrySet();
		for (Entry<String, String> entry : entries) {
			keys.add(entry.getKey());
		}
		//key排序
		Collections.sort(keys);
		//拼接参数
		for (String s : keys) {
			sb.append(s).append("=").append(params.get(s));
		}
		//// 在请求参数字符串末尾拼接上 secure_key
		sb.append("evWkvFzpJ5qVVm");
		String SignString = sb.toString();
		//计算md5
		String sign = Md5Utils.md5Str(SignString);
		return sign;
	}
	
	public String getAtApkResult(String json){
		JSONObject data = JSONObject.parseObject(json);
		int errorCode =  data.get("error_code")==null?2000:(Integer)data.get("error_code");
		if(errorCode>=1000){
			JSONObject scanResult =  (JSONObject)data.get("data");
			JSONObject temp = (JSONObject)scanResult.get("scan_result");
			String virusname = temp.getString("virus_name");
			return virusname;
		}else{
			//执行异常
			return "";
		}
	}
	
	public int getAtApkSafeType(String json){
		JSONObject data = JSONObject.parseObject(json);
		int errorCode =  data.get("error_code")==null?2000:(Integer)data.get("error_code");
		if(errorCode>=1000){
			JSONObject scanResult =  (JSONObject)data.get("data");
			JSONObject temp = (JSONObject)scanResult.get("scan_result");
			return temp.get("safe_type")==null?-1:(Integer)temp.get("safe_type");
		}else{
			//执行异常
			return -1;
		}
	}
	
	/**
	 * 获取md5值
	 * @param json
	 * @return
	 */
	public String getMd5Value(String json) {
		JSONObject data = JSONObject.parseObject(json);
		int errorCode =  data.get("error_code")==null?2000:(Integer)data.get("error_code");
		if(errorCode>=1000){
			JSONObject scanResult =  (JSONObject)data.get("data");
			return (String)scanResult.get("md5");
		}else{
			//执行异常
			return "-1";
		}
	}
}
