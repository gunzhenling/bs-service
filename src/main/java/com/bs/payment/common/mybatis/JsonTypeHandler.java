package com.bs.payment.common.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import com.bs.payment.util.ZcJsonUtil;

/**
 * MyBatis Json 处理
 * @author fanhang
 *
 * @param <T>
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonTypeHandler extends BaseTypeHandler<Object> {

	public JsonTypeHandler() {
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, this.toJson(parameter));
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return this.toObject(rs.getString(columnName));
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return this.toObject(rs.getString(columnIndex));
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return this.toObject(cs.getString(columnIndex));
	}

	private String toJson(Object object) {
		try {
			return ZcJsonUtil.nonNullCaseMapper().toJson(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object toObject(String content) {
		if (content != null && !content.isEmpty()) {
			try {
				return ZcJsonUtil.nonNullCaseMapper().fromJson(content, LinkedHashMap.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return null;
		}
	}
}
