package com.bs.payment.common.exception;

public class RemoteFailException extends BusinessException {
	private static final long serialVersionUID = 125954248161373076L;

	private int code;
	
	public RemoteFailException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
