package com.bs.payment.common.exception;

/**
 * 账单文件不存在
 * 
 * @author fanhang
 */
public class BillFileNotExistsException extends RemoteFailException {
	private static final long serialVersionUID = 6402586811537061224L;

	public BillFileNotExistsException(String code, String msg) {
		super(code, msg);
	}
}
