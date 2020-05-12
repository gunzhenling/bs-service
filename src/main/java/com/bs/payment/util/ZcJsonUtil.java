package com.bs.payment.util;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 * 
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 * @since 0.0.1 推荐
 */
public class ZcJsonUtil {

	private static ZcJsonUtil	_nonNullMapper		= new ZcJsonUtil(Include.NON_NULL);
	private static ZcJsonUtil	_nonEmptyMapper		= new ZcJsonUtil(Include.NON_EMPTY);
	private static ZcJsonUtil	_nonDefaultMapper	= new ZcJsonUtil(Include.NON_DEFAULT);
	private static ZcJsonUtil	_nonNullCaseMapper	= new ZcJsonUtil(Include.NON_NULL, true);

	private static Logger		logger				= LoggerFactory.getLogger(ZcJsonUtil.class);

	private ObjectMapper		mapper;

	public ZcJsonUtil() {
		this(null);
	}

	public ZcJsonUtil(Include include) {
		mapper = new ObjectMapper();
		// 设置输出时包含属性的风格
		if (include != null) {
			mapper.setSerializationInclusion(include);
		}
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	/**
	 * 
	 * @param include
	 * @param isCase
	 *            是否把请求参数为下划线的转换为驼峰属性
	 */
	public ZcJsonUtil(Include include, boolean isCase) {
		this(include);
		if (isCase) {
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		}
	}

	/**
	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,不包含默认值，建议在外部接口中使用.
	 */
	public static ZcJsonUtil nonEmptyMapper() {
		return _nonEmptyMapper;
	}

	/**
	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,包含默认值
	 * 
	 * @return
	 */
	public static ZcJsonUtil nonNullMapper() {
		return _nonNullMapper;
	}

	/**
	 * 把请求参数为下划线的转换为驼峰属性，对外接口使用
	 * 
	 * @return
	 */
	public static ZcJsonUtil nonNullCaseMapper() {
		return _nonNullCaseMapper;
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
	 */
	public static ZcJsonUtil nonDefaultMapper() {
		return _nonDefaultMapper;
	}

	/**
	 * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
	 */
	public String toJson(Object object) {

		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 反序列化POJO或简单Collection如List<String>.
	 * 
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
	 * 
	 * @see #fromJson(String, JavaType)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 反序列化复杂Collection如List<Bean>,
	 * 先使用createCollectionType()或contructMapType()构造类型, 然后调用本函数.
	 * 
	 * @see #createCollectionType(Class, Class...)
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, JavaType javaType) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return (T) mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 构造Collection类型.
	 */
	public JavaType contructCollectionType(Class<? extends Collection<?>> collectionClass, Class<?> elementClass) {
		return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
	}

	/**
	 * 构造Map类型.
	 */
	public JavaType contructMapType(Class<? extends Map<?, ?>> mapClass, Class<?> keyClass, Class<?> valueClass) {
		return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
	}

	/**
	 * 构造List类型.
	 */
	public JavaType contructListType(Class<?> elemClass) {
		return mapper.getTypeFactory().constructParametricType(List.class, elemClass);
	}

	/**
	 * 当JSON里只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
	 */
	public void update(String jsonString, Object object) {
		try {
			mapper.readerForUpdating(object).readValue(jsonString);
		} catch (JsonProcessingException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		} catch (IOException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		}
	}

	/**
	 * 輸出JSONP格式数据.
	 */
	public String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}

	/**
	 * 设定是否使用Enum的toString函数來读写Enum, 为False时使用Enum的name()函数來读写Enum, 默认为False.
	 * 注意本函数一定要在Mapper创建后, 所有的读写动作之前调用.
	 */
	public void enableEnumUseToString() {
		mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
	}

	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}

	/**
	 * 
	 * 描述：
	 * 
	 * <pre>
	 * 接口JSON模式：字段命名为：小写+下划线
	 * </pre>
	 * 
	 * @author qizai
	 * @version: 0.0.1 2018年7月4日-下午3:14:14
	 *
	 */
	public static class JsonApiHelper {
		private static ZcJsonUtil NON_NULL_CASE_MAPPER = new ZcJsonUtil(Include.NON_NULL, true);

		public static String toJson(Object object) {
			return NON_NULL_CASE_MAPPER.toJson(object);
		}

		public static <T> T fromJson(String jsonString, Class<T> clazz) {
			return NON_NULL_CASE_MAPPER.fromJson(jsonString, clazz);
		}
	}
}
