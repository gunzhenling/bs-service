package com.bs.payment.common.exception;

public class RemoteFailException extends BusinessException {
	private static final long serialVersionUID = 125954248161373076L;

	private String code;
	
	public RemoteFailException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
