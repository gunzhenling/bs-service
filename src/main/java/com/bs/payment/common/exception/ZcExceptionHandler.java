
package com.bs.payment.common.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bs.payment.util.ApiResult;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.exception.ZcBusinessException;

/**
 * 异常处理器
 *
 */
@RestControllerAdvice
@Slf4j
public class ZcExceptionHandler {

	@ExceptionHandler(DuplicateKeyException.class)
	public ApiResult<String> handleDuplicateKeyException(DuplicateKeyException e) {
		return ApiResult.error(500, "数据库中已存在该记录", getError(e));
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ApiResult<String> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException e) {
		return ApiResult.error(500, "数据库中已存在该记录", getError(e));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ApiResult<String> handleException(IllegalArgumentException e) {
		return ApiResult.error(400, "请求参数有误:" + e.getMessage(), getError(e));
	}

	@ExceptionHandler(ValidationException.class)
	public ApiResult<String> handleException(ValidationException e) {
		return ApiResult.error(400, "请求参数有误:" + e.getMessage(), getError(e));
	}

	@ExceptionHandler(BindException.class)
	public ApiResult<String> handleBindException(BindException e) {
		String result = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(","));
		return ApiResult.error(400, result, getError(e));
	}

	@ExceptionHandler(ZcBusinessException.class)
	public ApiResult<String> handleZcBusinessException(ZcBusinessException e) {
		log.warn("ZcBusinessException: {},{}", e.getCode(), e.getMessage());
		return ApiResult.error(400, e.getMessage());
	}

	@ExceptionHandler(BusinessException.class)
	public ApiResult<String> handleBusinessException(BusinessException e) {
		log.warn("BusinessException: msg={},subMsg={}", e.getMessage());
		return ApiResult.error(e.getCode(), e.getMessage());
	}
	/*
	@ExceptionHandler(BusinessException.class)
	public ApiResult<String> handleBusinessException(BusinessException e) {
		log.warn("BusinessException: msg={},subMsg={}", e.getMessage(), e.getSubMsg());
		return ApiResult.error(e.getCode(), e.getMessage(), e.getSubMsg());
	}*/

/*	@ExceptionHandler(IServiceExecption.class)
	public ZcResult<String> ex(IServiceExecption e) {
		log.warn("IServiceExecption: {}", e.toString());
		return ZcResult.error(e.getCode(), e.getMsg(), e.getErrormsg());
	}*/

	/*@ExceptionHandler(RedisLockException.class)
	public ApiResult<String> handleBsLockException(RedisLockException e) {
		return ApiResult.error(400, "处理不过来了，等一会再来吧", getError(e));
	}*/

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ApiResult<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		return ApiResult.error(400, "非法请求", getError(e));
	}

	 
	@ExceptionHandler(Exception.class)
	public ApiResult<String> handleException(Exception e) {
		log.error("",e);
		return ApiResult.error(400, "服务开小差了", getError(e));
	}

	@ExceptionHandler(NullPointerException.class)
	public ApiResult<String> handleException(NullPointerException e) {
		log.error("",e);
		return ApiResult.error(400, "请求发生错误，请联系客服", getError(e));
	}

	/**
	 * 提取异常中包含com.intbee的异常信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getError(Throwable e) {
		String ret = "";
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(); PrintStream pout = new PrintStream(out);) {
			e.printStackTrace(pout);
			ret = new String(out.toByteArray());
			Iterator<String> iterable = Splitter.on(" ").split(ret).iterator();
			List<String> errors = new ArrayList<String>();
			while (iterable.hasNext()) {
				String text = iterable.next();
				if (text.startsWith("com.intbee") || text.startsWith("xyz.nesting")) {
					errors.add(text.replace("\n\tat", ";"));
				}
			}
			ret = Joiner.on("").join(errors);
		} catch (Exception e1) {
			log.warn("Exception:", e1);
		}
		log.warn(e.getClass().getSimpleName().concat(": ").concat(Strings.nullToEmpty(e.getMessage())).concat(" ")
				.concat(ret));
		return ret;
	}

	@ExceptionHandler(IllegalStateException.class)
	public ApiResult<String> handleIllegalStateException(IllegalStateException e) {
		return ApiResult.error(400, e.getMessage(), getError(e));
	}

	@ExceptionHandler(BadSqlGrammarException.class)
	public ApiResult<String> handleBadSqlGrammarException(BadSqlGrammarException e) {
		return ApiResult.error(400, "数据发生异常，请稍后再试", getError(e));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ApiResult<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return ApiResult.error(400, "请求参数有误", getError(e));
	}

	/**
	 * 校验错误拦截处理
	 *
	 * @param exception
	 *            错误信息集合
	 * @return 错误信息
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResult<String> validationBodyException(MethodArgumentNotValidException e) {
		BindingResult result = e.getBindingResult();
		StringBuilder errorMessage = new StringBuilder();
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			Set<String> fieldSet = Sets.newHashSet();
			errors.forEach(p -> {
				FieldError fieldError = (FieldError) p;
				if (!fieldSet.contains(fieldError.getField())) {// 只返回一个错误提示
					fieldSet.add(fieldError.getField());
					log.warn("MethodArgumentNotValidException :" + fieldError.getObjectName() + ","
							+ fieldError.getField() + ":" + fieldError.getDefaultMessage());
					errorMessage.append(fieldError.getField() + ":" + fieldError.getDefaultMessage() + ";");
				}
			});
		}
		return ApiResult.error(400, "您的输入有误，请修正后再提交", errorMessage.toString());
	}

}
