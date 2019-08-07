package com.ishareread.common.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;

/**
 * Map通用处理方法
 * 
 *
 */
public class MapDataUtil {
	public static Map<String, Object> convertDataMap(HttpServletRequest request) {
		Map<String, String[]> properties = request.getParameterMap();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator<?> entries = properties.entrySet().iterator();
		Map.Entry<?, ?> entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry<?, ?>) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	
	
	 /**
     * 使用 Map按key进行排序
     * @param s_2
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> s_2) {
        if (s_2 == null || s_2.isEmpty()) {
            return null;
        }
        
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
	    sortMap.putAll(s_2);
	    return sortMap;
	}
    
    /**
     * 使用 Map按key进行排序
     * @param s_2
     * @return
     */
    public static Map<String, Object> sortMapByKey1(Map<String, Object> s_2) {
        if (s_2 == null || s_2.isEmpty()) {
            return null;
        }
        
        Map<String, Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());
	    sortMap.putAll(s_2);
	    return sortMap;
	}
}
