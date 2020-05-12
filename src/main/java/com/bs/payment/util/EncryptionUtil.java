package com.bs.payment.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

 
/**
 * 
 * @Description: 加密工具
 * @Author Young
 * @Version: 0.0.1
 * @CreateAt 2016年9月14日 下午1:05:41
 *
 */
@Slf4j
public class EncryptionUtil {
	
	public static boolean  validSign(Object obj, String salt,String sign) {
		
		String signCenter = getSign(obj,salt);
			 
		Assert.isTrue(sign.equalsIgnoreCase(signCenter), "validate fail:center sign:"+signCenter+" not equal request sign:"+sign);
		return true;
	}
	
	public static String getSign(Object obj, String salt){
		Map<String, Object> map = objectToMapUtil(obj);
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() instanceof Map) {
				ArrayList<String> mapList = new ArrayList<String>();
				Map<String, Object> mapMap = (Map<String, Object>) entry.getValue();
				for (Map.Entry<String, Object> mapEntry : mapMap.entrySet()) {
					if (mapEntry.getValue() != null && mapEntry.getValue() != "") {
						mapList.add(mapEntry.getKey() + "=" + mapEntry.getValue() + "&");
					}
				}
				int size = mapList.size();
				String[] arrayToSort = mapList.toArray(new String[size]);
				Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < size; i++) {
					sb.append(arrayToSort[i]);
				}

				if(size>0) {
					
					list.add(entry.getKey() + "=" + sb.toString());
				}
				continue;
			}
			if (entry.getKey().equals("sign")) {
				continue;
			}
			if (entry.getValue() != null && entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + salt.toString();
		
		log.info("center sign content:"+result);
		
		result = DigestUtils.md5Hex(result);
		return result;
	}
	
	public static String alipayLiteSignObject(Object obj,String key) {

		Map<String, Object> map = objectToMapUtil(obj);
		String result = getSignCheckContent(map);
		result=result+key;
		
		String sign = DigestUtils.md5Hex(result.toString());
		log.info("alipayLiteSignObject: sign-content={};sign={}",result,sign);
		
		return sign;
	
	}
	
	public static String alipayLiteSign(Map<String, Object> map,String key) {

		String result = getSignCheckContent(map);
		result=result+key;
		
		String sign = DigestUtils.md5Hex(result.toString());
		log.info("alipayLiteSign: sign-content={};sign={}",result,sign);
		
		return sign;
	
	}
	
	public static String getSignCheckContent(Map<String, Object> map) {
		
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			
			if (entry.getKey().equals("sign")) {
				continue;
			}
			if (entry.getValue() != null && entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result = result.substring(0, result.length()-1);
		return result;
		
	}
	
	private static Map<String, Object> objectToMapUtil(Object obj) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field subField;
			try {
				subField = obj.getClass().getDeclaredField(fields[i].getName());
				subField.setAccessible(true);
				reMap.put(fields[i].getName(), subField.get(obj));
			} catch (Exception e) {
				log.error("objectToMapUtil fail");
			}
		}
		return reMap;
	}
}
