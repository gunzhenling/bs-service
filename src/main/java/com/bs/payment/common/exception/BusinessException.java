package com.bs.payment.common.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -855217302727077301L;

	private int	code;
	
	public int getCode() {
		return code;
	}
	
	public BusinessException code(int code) {
		this.code = code;
		return this;
	}

 	
	public BusinessException(String message) {
		super(message);
	}
	
	public static void error(int code, String message) {
		throw new BusinessException(message).code(code);
	}
	
	public BusinessException(String message, Throwable e) {
		super(message, e);
	}
}
