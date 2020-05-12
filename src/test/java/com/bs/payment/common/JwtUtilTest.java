package com.bs.payment.common;


import static org.junit.Assert.*;

import org.junit.Test;

import com.bs.payment.common.interceptor.jwt.JwtDetail;
import com.bs.payment.common.interceptor.jwt.JwtUtil;

public class JwtUtilTest {
	
	@Test
	public void jwtParse() {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOm51bGwsImF1ZCI6IjEwMSIsInN1YiI6IjViNTgxNzU0ZDU4OTA5MmJmZmNjNjE4MiIsImFwcGlkIjoiMTAxIiwiZXhwIjo5MjEyOTQwNjAyMTk4MywianRpIjoiUFFEWkxsMm5sai1UR0RjTy04UE04USIsImlhdCI6MTUzNTQzOTE5OX0.CctWZk3bCD7CS9crHYd6dn3QUWdakR3C3JA9BI13Ph0";
		JwtDetail jwtDetail = JwtUtil.parse(token, "fa2jBYYXukgRft4J942F3TDCmMXqedcW");
		assertNotNull(jwtDetail);
		assertFalse(jwtDetail.isError());
	}
	
}
