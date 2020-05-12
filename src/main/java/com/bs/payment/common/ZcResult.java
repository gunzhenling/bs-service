package com.bs.payment.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 描述：
 * 
 * <pre>
 * 接口对外响应封装
 * </pre>
 * 
 * @author qizai
 * @version: 0.0.1 2018年5月15日-下午2:19:43
 *
 */
@Data
@Accessors(chain = true)
public class ZcResult<T> {
	public static final String DEFAULT_SUCCESS = "SUCCESS";
	/**
	 * 状态码。0为正确；非0为异常
	 */
	private int code;
	/**
	 * 空为正确；非空为错误原因
	 */
	private String message;
	/**
	 * 空为正确；非空为错误栈
	 */
	private String stackTrace;
	/**
	 * 响应结果集
	 */
	private T result;
	/**
	 * 结果集大小
	 */
	private int size;
	
	public ZcResult() {
		
	}
	
	public ZcResult(int code, String message, T result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public static <T> ZcResult<T> error(int code, String message) {
		return new ZcResult<>(code, message, null);
	}

	public static <T> ZcResult<T> error(int code, String message, String stackTrace) {
		ZcResult<T> result = error(code, message);
		result.setStackTrace(stackTrace);
		return result;
	}

	public static <T> ZcResult<T> ok(T result) {
		return new ZcResult<>(0, null, result);
	}

	public static <T> ZcResult<Object> ok() {
		return new ZcResult<>(0, null, null);
	}
}
