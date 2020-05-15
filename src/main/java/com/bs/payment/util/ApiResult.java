/**
 * 
 */
package com.bs.payment.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 描述：
 * 
 * <pre>
 * TODO(添加描述)
 * </pre>
 * 
 * @author 天明
 * @version: 0.0.1 2018年12月3日-下午2:18:24
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "API统一响应参数")
public class ApiResult<T> {
	public final static String	SUCCESS	= "SUCCESS";
	@ApiModelProperty(notes = "响应状态码，非0则表示异常", example = "0")
	private Integer				code;
	@ApiModelProperty(notes = "响应状态描述")
	private String				message;
	@ApiModelProperty(notes = "业务描述")
	private String				subMsg;
	@ApiModelProperty(notes = "响应数据")
	private T					result;

	public static <T> ApiResult<T> ok(T data) {
		ApiResult<T> response = new ApiResult<T>();
		response.setCode(0);
		response.setMessage(SUCCESS);
		response.setResult(data);
		return response;
	}

	public static <T> ApiResult<T> ok() {
		return ok(null);
	}

	/**
	 * code=500
	 * 
	 * @param message
	 * @return
	 */
	public static <T> ApiResult<T> error(String message) {
		return error(500, message);
	}

	public static <T> ApiResult<T> error(int code, String message) {
		ApiResult<T> response = new ApiResult<T>();
		response.setCode(code);
		response.setMessage(message);
		return response;
	}

	public static <T> ApiResult<T> error(int code, String msg, String subMsg) {
		ApiResult<T> response = new ApiResult<T>();
		response.setCode(code);
		response.setMessage(msg);
		response.setSubMsg(subMsg);
		return response;
	}
}
