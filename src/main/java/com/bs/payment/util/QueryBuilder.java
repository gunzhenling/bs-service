/**
 * 
 */
package com.bs.payment.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import lombok.NoArgsConstructor;

/**
 * 描述：查询构建工具类，字段名必须与数据库表字段一致
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 2019年1月25日-下午3:58:18
 *
 */
@NoArgsConstructor
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryBuilder {

	private QueryWrapper	query	= null;
	private UpdateWrapper	update	= null;

	/**
	 * 
	 * @param orderText
	 *            排序字段: 格式如（name:asc;age:desc 为按照name正序，age倒序,正序可省略）
	 * @return "name ASC,age DESC"
	 */
	public static String orderBySql(String orderText, List<String> orderFields, String defaultOrderText) {
		if (StringUtils.isBlank(orderText)) {
			return defaultOrderText;
		}
		List<String> orders = Lists.newArrayList();
		Splitter.on(";").split(orderText).forEach(o -> {
			List<String> iterator = Splitter.on(":").splitToList(o);
			int orderSize = iterator.size();
			if (orderSize > 0) {
				String field = iterator.get(0);
				String order = "ASC";
				if (orderFields.contains(iterator.get(0))) {
					if (orderSize == 2 && !"ASC".equalsIgnoreCase(iterator.get(1))) {
						order = "DESC";
					}
					orders.add(field + " " + order);
				}
			}
		});
		if (orders.size() == 0) {
			return defaultOrderText;
		}
		return Joiner.on(",").join(orders);
	}

	public static <T> QueryBuilder start() {
		QueryBuilder instance = new QueryBuilder();
		QueryWrapper<T> query = new QueryWrapper<T>();
		instance.query = query;
		return instance;
	}

	public static <T> QueryBuilder startUpdate() {
		QueryBuilder instance = new QueryBuilder();
		UpdateWrapper<T> update = new UpdateWrapper<T>();
		instance.update = update;
		return instance;
	}

	public QueryBuilder eq(boolean condition, String column, Object val) {
		query.eq(condition, column, val);
		return this;
	}

	public QueryBuilder eq(String column, @Nullable Object val) {
		query.eq(null != val, column, val);
		return this;
	}

	public QueryBuilder gt(boolean condition, String column, Object val) {
		query.gt(condition, column, val);
		return this;
	}

	public QueryBuilder gt(String column, @Nullable Object val) {
		query.gt(null != val, column, val);
		return this;
	}

	public QueryBuilder gte(boolean condition, String column, Object val) {
		query.ge(condition, column, val);
		return this;
	}

	public QueryBuilder gte(String column, @Nullable Object val) {
		query.ge(null != val, column, val);
		return this;
	}

	public QueryBuilder lt(String column, @Nullable Object val) {
		query.lt(null != val, column, val);
		return this;
	}

	public QueryBuilder lt(boolean condition, String column, Object val) {
		query.lt(condition, column, val);
		return this;
	}

	public QueryBuilder lte(boolean condition, String column, Object val) {
		query.le(condition, column, val);
		return this;
	}

	public QueryBuilder lte(String column, @Nullable Object val) {
		query.le(null != val, column, val);
		return this;
	}

	public QueryBuilder like(boolean condition, String column, Object val) {
		query.like(condition, column, val);
		return this;
	}

	public QueryBuilder like(String column, @Nullable Object val) {
		query.like(null != val, column, val);
		return this;
	}

	public QueryBuilder likeLeft(boolean condition, String column, Object val) {
		query.likeLeft(condition, column, val);
		return this;
	}

	public QueryBuilder likeLeft(String column, @Nullable Object val) {
		query.likeLeft(null != val, column, val);
		return this;
	}

	public QueryBuilder likeRight(boolean condition, String column, Object val) {
		query.likeRight(condition, column, val);
		return this;
	}

	public QueryBuilder likeRight(String column, @Nullable Object val) {
		query.likeRight(null != val, column, val);
		return this;
	}

	public QueryBuilder in(boolean condition, String column, Object... val) {
		query.in(condition, column, val);
		return this;
	}

	public QueryBuilder in(String column, @Nullable Object... val) {
		query.in(null != val, column, val);
		return this;
	}

	public QueryBuilder in(String column, @Nullable Collection val) {
		query.in(null != val && !val.isEmpty(), column, val);
		return this;
	}

	/**
	 * 不等于
	 * 
	 * @param condition
	 * @param column
	 * @param val
	 * @return
	 */
	public QueryBuilder ne(boolean condition, String column, Object val) {
		query.ne(condition, column, val);
		return this;
	}

	public QueryBuilder ne(String column, @Nullable Object val) {
		query.ne(null != val, column, val);
		return this;
	}

	public QueryBuilder between(String column, @Nullable Object val1, @Nullable Object val2) {
		query.between((null != val1 && null != val2), column, val1, val2);
		return this;
	}

	public QueryBuilder between(boolean condition, String column, Object val1, Object val2) {
		query.between(condition, column, val1, val2);
		return this;
	}

	public QueryBuilder isNull(String column) {
		query.isNull(column);
		return this;
	}

	public QueryBuilder isNotNull(String column) {
		query.isNotNull(column);
		return this;
	}

	public QueryBuilder orderByAsc(@Nullable Object... columns) {
		query.orderByAsc(notNull(columns), columns);
		return this;
	}

	public QueryBuilder orderByDesc(@Nullable Object... columns) {
		query.orderByDesc(notNull(columns), columns);
		return this;
	}

	public QueryBuilder groupBy(@Nullable Object... columns) {
		query.groupBy(notNull(columns), columns);
		return this;
	}

	private boolean notNull(Object... obj) {
		if (null == obj || obj.length < 1) {
			return false;
		}
		return true;
	}

	public QueryBuilder set(String column, Object val) {
		update.eq(checkValNotBlank(val), column, val);
		return this;
	}

	/**
	 * <p>
	 * 判断对象是否为空
	 * </p>
	 *
	 * @param object
	 * @return
	 */
	public static boolean checkValNotBlank(Object object) {
		if (object instanceof CharSequence) {
			return StringUtils.isNotBlank((CharSequence) object);
		}
		return object != null;
	}

	public static <T> UpdateWrapper<T> where(String column, Object val) {
		UpdateWrapper<T> update = new UpdateWrapper<T>();
		update.eq(column, val);
		return update;
	}

	public static <T> UpdateWrapper<T> where(final Object... params) {
		UpdateWrapper<T> update = new UpdateWrapper<T>();
		if (null == params || params.length % 2 != 0) {
			throw new IllegalArgumentException("过滤参数个数必须为偶数个");
		}
		for (int i = 0, l = params.length; i < l; i = i + 2) {
			update.eq(TextKit.camelToUnderline(params[i].toString()), params[i + 1]);
		}
		return update;
	}

	/**
	 * 
	 * @param dto
	 * @param ignoreProperties
	 *            排除的字段
	 * @return 不为null的字段及string isNotBlank 的字段
	 */
	public static List<String> getUpdateField(@NotNull final Object dto, String... ignoreProperties) {
		List<String> updateField = Lists.newArrayList();
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties)
				: Collections.emptyList());
		BeanMap beanMap = BeanMap.create(dto);
		beanMap.forEach((k, v) -> {
			if (null != v && StringUtils.isNotBlank(Objects.toString(v)) && !ignoreList.contains(k)) {
				updateField.add(k.toString());
			}
		});
		return updateField;
	}

	public <T> Wrapper<T> end() {
		return query;
	}

	public <T> UpdateWrapper<T> up() {
		return update;
	}

}
