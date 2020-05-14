/**
 * 
 */
package com.bs.payment.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述：对象复制工具类
 * 
 * <pre>
 * 1. BeanCopier只拷贝名称和类型都相同的属性
 * 2. 当目标类的setter数目比getter少时，创建BeanCopier会失败而导致拷贝不成功
 * 3. Asm只拷贝私有的变量且字段类型必须一致，否则异常
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 2019年2月11日-上午10:08:43
 *
 */
@Slf4j
public abstract class BeanKit {
	private static final ConcurrentHashMap<String, BeanCopier>	mapCaches		= new ConcurrentHashMap<>();
	@SuppressWarnings("rawtypes")
	private static ConcurrentHashMap<Class, MethodAccess>		methodMap		= new ConcurrentHashMap<Class, MethodAccess>();
	private static ConcurrentHashMap<String, Integer>			methodIndexMap	= new ConcurrentHashMap<String, Integer>();
	@SuppressWarnings("rawtypes")
	private static ConcurrentHashMap<Class, List<String>>		fieldMap		= new ConcurrentHashMap<Class, List<String>>();

	/**
	 * 
	 * @param source
	 * @param ignoreProperties
	 *            排除的字段
	 * @return 不为null的字段及string isNotBlank 的字段
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<String> getNotBlank(@NotNull final T source, String... ignoreProperties) {
		List<String> updateField = Lists.newArrayList();
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties)
				: Collections.emptyList());
		BeanMap beanMap = BeanMap.create(source);
		beanMap.forEach((k, v) -> {
			if (ignoreList.contains(k) || null == v || StringUtils.isBlank(Objects.toString(v))) {
				// undo
			} else {
				updateField.add(k.toString());
			}
		});
		return updateField;
	}

	/**
	 * 获取对象中为空的字段名，用于进行对象复制时过滤
	 * 
	 * @param source
	 * @param ignoreProperties
	 *            默认排除的字段
	 * @return
	 */
	public static <T> String[] getBlankPropertyNames(@NotNull final T source, String... ignoreProperties) {
		BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties)
				: Collections.emptyList());
		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			if (ignoreList.contains(pd.getName())) {
				emptyNames.add(pd.getName());
				continue;
			}
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null || StringUtils.isBlank(srcValue.toString())) {
				emptyNames.add(pd.getName());
			}
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 获取对象中不为空的字段名
	 * 
	 * @param source
	 * @param ignoreProperties
	 *            明确不需要判断是否为空的字段，不在结果中返回
	 * @return
	 */
	public static String[] getNotBlankPropertyNames(@NotNull final Object source, String... ignoreProperties) {
		BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties)
				: Collections.emptyList());
		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			String name = pd.getName();
			if (ignoreList.contains(name)) {
				continue;
			}
			Object srcValue = src.getPropertyValue(name);
			if (srcValue != null && StringUtils.isNotBlank(srcValue.toString()))
				emptyNames.add(name);
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 
	 * @param source
	 * @param ignoreProperties
	 *            排除的字段
	 * @return 不为null的字段
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<String> getNotNull(@NotNull final T source, String... ignoreProperties) {
		List<String> updateField = Lists.newArrayList();
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties)
				: Collections.emptyList());
		BeanMap beanMap = BeanMap.create(source);
		beanMap.forEach((k, v) -> {
			if (ignoreList.contains(k) || null == v) {
				// undo
			} else {
				updateField.add(k.toString());
			}
		});
		return updateField;
	}

	/**
	 * 基于反射机制的对象属性复制
	 * 
	 * @param source
	 *            源数据
	 * @param target
	 *            目标数据
	 * @param ignoreProperties
	 *            忽略的字段
	 * @return source->target
	 */
	public static <O, T> T copyReflect(O source, T target, String... ignoreProperties) {
		BeanUtils.copyProperties(source, target, ignoreProperties);
		return target;
	}

	/**
	 * 基于反射机制的对象属性复制
	 * 
	 * @param source
	 *            源数据
	 * @param target
	 *            目标数据
	 * @param def
	 *            source 为null时的默认值
	 * @param ignoreProperties
	 *            忽略的字段
	 * @return source->target
	 */
	public static <O, T> T copyReflectNull(O source, T target, T def, String... ignoreProperties) {
		if (null == source) {
			return def;
		}
		BeanUtils.copyProperties(source, target, ignoreProperties);
		return target;
	}

	/**
	 * 基于反射机制的对象属性复制
	 * 
	 * @param source
	 *            源数据
	 * @param target
	 *            目标数据
	 * @param ignoreProperties
	 *            默认不复制的字段
	 * @return source->target
	 */
	public static <O, T> T copyNotBlank(O source, T target, String... ignoreProperties) {
		BeanUtils.copyProperties(source, target, getBlankPropertyNames(source, ignoreProperties));
		return target;
	}

	/**
	 * 基于cglib实现对象属性复制，字段名称和类型必须一致
	 * 
	 * @param source
	 *            源数据
	 * @param target
	 *            目标数据
	 * @return source->target
	 */
	public static <O, T> T copyCglib(O source, T target) {
		String baseKey = source.getClass().toString() + target.getClass().toString();
		BeanCopier copier;
		if (!mapCaches.containsKey(baseKey)) {
			copier = BeanCopier.create(source.getClass(), target.getClass(), false);
			mapCaches.put(baseKey, copier);
		} else {
			copier = mapCaches.get(baseKey);
		}
		copier.copy(source, target, null);

		return target;
	}

	/**
	 * 使用reflectasm进行属性复制，仅复制私有的非静态变量
	 * 
	 * @param source
	 *            源数据
	 * @param target
	 *            目标数据
	 * @param ignoreProperties
	 *            忽略的字段
	 * @return source->target
	 */
	public static <O, T> T copyAsm(O source, T target, String... ignoreProperties) {
		MethodAccess sourceMethodAccess = methodMap.get(source.getClass());
		if (sourceMethodAccess == null) {
			sourceMethodAccess = cacheMethodAccess(source);
		}
		MethodAccess targetMethodAccess = methodMap.get(target.getClass());
		if (targetMethodAccess == null) {
			targetMethodAccess = cacheMethodAccess(target);
		}
		List<String> fieldList = fieldMap.get(target.getClass());
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties)
				: Collections.emptyList());
		for (String field : fieldList) {
			if (ignoreList.contains(StringUtils.uncapitalize(field))) {
				continue;
			}
			String getKey = source.getClass().getName() + "." + "get" + field;
			String setkey = target.getClass().getName() + "." + "set" + field;
			Integer setIndex = methodIndexMap.get(setkey);
			Integer getIndex = methodIndexMap.get(getKey);
			if (null == setIndex || null == getIndex) {
				continue;
			}
			Object sourceValue = sourceMethodAccess.invoke(source, getIndex);
			if (null != sourceValue) {
				try {
					targetMethodAccess.invoke(target, setIndex.intValue(), sourceValue);
				} catch (ClassCastException e) {
					log.debug("copyAsm ClassCastException " + e.getMessage(), e);
				}
			}
		}
		return target;
	}

	private static MethodAccess cacheMethodAccess(Object target) {
		synchronized (target.getClass()) {
			MethodAccess methodAccess = MethodAccess.get(target.getClass());
			Field[] fields = target.getClass().getDeclaredFields();
			List<String> fieldList = new ArrayList<String>(fields.length);
			for (Field field : fields) {
				if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) { // 是否是私有的，是否是静态的
					// 非公共私有变量
					String fieldName = StringUtils.capitalize(field.getName()); // 获取属性名称
					int getIndex = methodAccess.getIndex("get" + fieldName); // 获取get方法的下标
					int setIndex = methodAccess.getIndex("set" + fieldName); // 获取set方法的下标
					methodIndexMap.put(target.getClass().getName() + "." + "get" + fieldName, getIndex); // 将类名get方法名，方法下标注册到map中
					methodIndexMap.put(target.getClass().getName() + "." + "set" + fieldName, setIndex); // 将类名set方法名，方法下标注册到map中
					fieldList.add(fieldName); // 将属性名称放入集合里
				}
			}
			fieldMap.put(target.getClass(), fieldList); // 将类名，属性名称注册到map中
			methodMap.put(target.getClass(), methodAccess);
			return methodAccess;
		}
	}

	public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
		List<T> list = new ArrayList<>(sources.size());
		for (S source : sources) {
			T t = target.get();
			BeanUtils.copyProperties(source, t);
			list.add(t);
		}
		return list;
	}
}
