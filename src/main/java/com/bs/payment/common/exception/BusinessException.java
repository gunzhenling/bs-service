package com.bs.payment.common.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -855217302727077301L;

	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Throwable e) {
		super(message, e);
	}
}
