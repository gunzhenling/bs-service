package com.bs.payment.common;

import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bs.payment.common.exception.RemoteFailException;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一异常处理
 * 
 * @author fanhang
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {

	private static final int PARAM_ERROR = 3000;
	private static final int REMOTE_ERROR = 5000;
	private static final int UNKNOW_ERROR = 9000;
	
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ZcResult<?> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
		log.warn("MissingServletRequestParameterException: {}", ex.getMessage());
		return ZcResult.error(PARAM_ERROR, "param " + ex.getParameterName() + " error", ex.getMessage());
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ZcResult<?> illegalArgumentException(IllegalArgumentException ex) {
		log.warn("IllegalArgumentException: {}", ex.getMessage());
		return ZcResult.error(PARAM_ERROR, ex.getMessage());
	}

	@ExceptionHandler({ IllegalStateException.class })
	public ZcResult<?> illegalStateException(IllegalStateException ex) {
		log.warn("IllegalStateException: {}", ex.getMessage());
		return ZcResult.error(PARAM_ERROR, ex.getMessage());
	}
	
	@ExceptionHandler({ UnsupportedOperationException.class })
	public ZcResult<?> unsupportedOperationException(UnsupportedOperationException ex) {
		log.warn("UnsupportedOperationException: {}", ex.getMessage());
		return ZcResult.error(REMOTE_ERROR, ex.getMessage());
	}
	
	@ExceptionHandler({ RemoteFailException.class })
	public ZcResult<?> remoteFailException(RemoteFailException ex) {
		log.warn("RemoteFailException: {}", ex.getMessage());
		return ZcResult.error(REMOTE_ERROR, ex.getMessage());
	}

	@ExceptionHandler({ HttpMessageConversionException.class })
	public ZcResult<?> httpMessageConversionException(HttpMessageConversionException ex) {
		log.warn("HttpMessageConversionException:{}", ex.getMessage());
		return ZcResult.error(PARAM_ERROR, ex.getMessage());
	}
	
	/**
	 * 根据 javax-validation 注解, 使用 spring 自动验证参数合法性
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ZcResult<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.warn("MethodArgumentNotValidException: {} {}", ex.getBindingResult().getAllErrors().get(0).getObjectName(),
				ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
		return ZcResult.error(PARAM_ERROR, ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}

	@ExceptionHandler({ Throwable.class })
	public ZcResult<?> errorThrowable(Throwable ex) {
		log.error("unkonw exception: ", ex);
		return ZcResult.error(UNKNOW_ERROR, ex.getMessage());
	}
}