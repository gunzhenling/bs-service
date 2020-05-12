package com.bs.payment.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bs.payment.common.config.JwtConfig;
import com.bs.payment.common.interceptor.jwt.AllowGuest;
import com.bs.payment.common.interceptor.jwt.JwtDetail;
import com.bs.payment.common.interceptor.jwt.JwtUtil;

/**
 * 权限(Token)验证
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	public static final String ACCESS_TOKEN = "access_token";
	
	public static final String USER_KEY = "userId";

	@Autowired
	private JwtConfig jwtConfig;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			AllowGuest hasToken = handlerMethod.getMethod().getAnnotation(AllowGuest.class);
			if (hasToken != null) {
				return true;
			}
		}
		
		String token = request.getHeader(ACCESS_TOKEN);
		Assert.hasText(token, "access_token must not be Null at: " + request.getRequestURI());
		
		// token 解析
		JwtDetail jwtDetail = JwtUtil.parse(token, jwtConfig.getSecret());
		Assert.isTrue(!jwtDetail.isError(), ACCESS_TOKEN + " parse error");
		Assert.isTrue(!jwtDetail.isExpired(), "auth expired");
		request.setAttribute("jwt", jwtDetail);
		return true;
	}
}
