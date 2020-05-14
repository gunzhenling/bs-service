/**
 * 
 */
package com.bs.payment.common.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 描述：自动填充处理
 * 
 * @author qizai
 * @version: 0.0.1 2018年10月26日-上午11:04:18
 *
 */
public class MybatisMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		Date now = new Date();
		if (null == this.getFieldValByName("createTime", metaObject)) {
			this.setFieldValByName("createTime", now, metaObject);
		}
		if (null == this.getFieldValByName("status", metaObject)) {
			this.setFieldValByName("status", 0, metaObject);
		}
		if (null == this.getFieldValByName("updateTime", metaObject)) {
			this.setFieldValByName("updateTime", now, metaObject);
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		if (null == this.getFieldValByName("updateTime", metaObject)) {
			this.setFieldValByName("updateTime", new Date(), metaObject);
		}
	}
}
