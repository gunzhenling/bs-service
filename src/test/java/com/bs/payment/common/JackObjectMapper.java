/**
 * 
 */
package com.bs.payment.common;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Description: 自定义JSON输出,定义非驼峰命名字段自动映射至驼峰命名字段，输出统一为非驼峰形式
 * @Author qizai
 * @Version: 0.0.1
 * @CreateAt 2016年5月22日-下午12:31:46
 *
 */
@Slf4j
public class JackObjectMapper extends ObjectMapper {
	public static void main(String[] args) {
	}
	private static final long serialVersionUID = 2839981445693017288L;

	public JackObjectMapper() {
		this(PropertyNamingStrategy.SNAKE_CASE, Include.NON_NULL);
	}

	public JackObjectMapper(PropertyNamingStrategy propertyNamingStrategy, Include include) {
		super();
		// 让jackson支持jaxb注解的配置
//		this.registerModule(new JaxbAnnotationModule());
		// 排序
		// this.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
		// 有属性不能映射的时候不报错
		this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 为null的属性值不映射
		if (null != include) {
			this.setSerializationInclusion(include);
		}
		// 驼峰属性处理
		if (null != propertyNamingStrategy) {
			this.setPropertyNamingStrategy(propertyNamingStrategy);
		}
	}

	/**
	 * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
	 */
	public String toJson(Object object) {
		try {
			return this.writeValueAsString(object);
		} catch (IOException e) {
			log.warn("write to json string error:" + object, e);
		}
		return null;
	}

	public Optional<String> json(Object object) {
		try {
			return Optional.ofNullable(this.writeValueAsString(object));
		} catch (IOException e) {
			log.warn("write to json string error:" + object, e);
		}
		return Optional.empty();
	}

	/**
	 * 美化格式输出JSON
	 * 
	 * @param object
	 * @return
	 */
	public String toJsonPretty(Object object) {
		try {
			return this.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (IOException e) {
			log.warn("write to json string error:" + object, e);
		}
		return null;
	}

	/**
	 * 反序列化POJO或简单Collection如List<String>.
	 * 
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用readValue(String, JavaType)
	 * 
	 * @see #readValue(String, JavaType)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return this.readValue(jsonString, clazz);
		} catch (IOException e) {
			log.warn("parse json string error:" + jsonString, e);
		}
		return null;
	}

	public <T> Optional<T> jsonTo(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return Optional.empty();
		}
		try {
			return Optional.ofNullable(this.readValue(jsonString, clazz));
		} catch (IOException e) {
			log.warn("parse json string error:" + jsonString, e);
		}
		return Optional.empty();
	}
}
