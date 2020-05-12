package com.bs.payment.common.interceptor.jwt;

import lombok.Data;

/**
 * 描述：jwt信息
 * 
 * <pre>
 * 
 * public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
 * 		throws IOException, ServletException {
 * 	HttpServletRequest httpRequest = (HttpServletRequest) request;
 * 	String auth = httpRequest.getHeader(JwtUtil.HEADER_AUTHORIZATION);
 * 	if ((auth != null) && (auth.length() > 7)) {
 * 		String headStr = auth.substring(0, 6).toLowerCase();
 * 		// Authorization bearer jwt-token
 * 		if (headStr.compareTo(JwtUtil.HEADER_BEARER) == 0) {
 * 			auth = auth.substring(7, auth.length());
 * 			JwtDetail jwtDetail = JwtUtil.parse(auth);
 * 			if (jwtDetail.isError()) {
 * 				authErrorResponse(response, ZcErrorMessage.errorResult(31001));
 * 				return;
 * 			}
 * 			if (jwtDetail.isExpired()) {
 * 				// token过期
 * 				authErrorResponse(response, ZcErrorMessage.errorResult(31000));
 * 				return;
 * 			}
 * 			request.setAttribute(JwtDetail.KEY_INTBEE_OPENID, jwtDetail.getUuid());
 * 			chain.doFilter(request, response);
 * 			return;
 * 		}
 * 	}
 * 	authErrorResponse(response, ZcErrorMessage.errorResult(31001));
 * 	return;
 * }
 * 
 * private void authErrorResponse(ServletResponse response, ZcResult result) throws IOException {
 * 	HttpServletResponse httpResponse = (HttpServletResponse) response;
 * 	httpResponse.setCharacterEncoding("UTF-8");
 * 	httpResponse.setContentType("application/json;charset=utf-8");
 * 	httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
 * 	httpResponse.getWriter().write(result.toJson());
 * }
 * </pre>
 * 
 * @author qizai
 * @version: 0.0.1 2018年6月25日-下午5:15:31
 *
 */
@Data
public class JwtDetail {
	public static final String	KEY_INTBEE_OPENID	= "intbee_openid";
	public static final String	KEY_INTBEE_APPID	= "intbee_appid";
	private String				appid;
	private String				uuid;
	private boolean				isExpired			= false;
	private boolean				isError				= false;
}
