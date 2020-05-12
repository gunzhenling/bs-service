/**
 * 
 */
package com.bs.payment.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.bs.payment.common.interceptor.jwt.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述：
 * 
 * <pre>
 * TODO(添加描述)
 * </pre>
 * 
 * @author [天明]jiannan@intbee.com
 * @version: 0.0.1 2019年1月20日-下午2:44:51
 *
 */
@Slf4j
public class WebTests {
//	public static String baseHost = "http://10.0.1.193:8083";
	public static String baseHost = "https://test-api.intbee.com";
//	public static String baseHost = "http://localhost:8083";

	private static HttpClient httpClient() {
		HttpClient client = HttpClientBuilder.create()
				.setDefaultHeaders(
//						Arrays.asList(new BasicHeader("Authorization", "bearer " + genJwt("5bea2cdf7663830006f140c2"))))
//				Arrays.asList(new BasicHeader("Authorization", "bearer " + genJwt("5ca42025cff47e0005551ae8"))))//考拉
				Arrays.asList(new BasicHeader("Authorization", "bearer " + genJwt("5ca5c432cff47e0005551af5"))))//考拉
//				Arrays.asList(new BasicHeader("Authorization", "bearer " + genJwt("58a27a340006f99e7cb43086"))))//考拉
//				Arrays.asList(new BasicHeader("Authorization", "bearer " + genJwt("5b2a1535b0bcdf00088d7cea"))))
//				Arrays.asList(new BasicHeader("Authorization", "bearer " + genJwt("5948cbc836dd67762e4209a7"))))
				// Arrays.asList(new BasicHeader("Authorization", "bearer " +
				// genJwt("581c00e12a50f0ff075e4f29"))))
				// Arrays.asList(new BasicHeader("Authorization", "bearer " +
				// genJwt("581c50c9a6a288fd4913d26e"))))
				.build();
		return client;
	}

	/**
	 * 将url参数转换成map
	 * 
	 * @param param
	 *            aa=11&bb=22&cc=33
	 * @return
	 */
	public static Map<String, Object> getUrlParams(String param) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		if (StringUtils.isBlank(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	/**
	 * 将map转换成url
	 * 
	 * @param map
	 * @return
	 */
	public static String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s =  StringUtils.substringBeforeLast(s, "&");
		}
		return s;
	}

	public static ZcResult postForObject(String url, Object request) {
		log.info("请求地址：POST " + baseHost + url);
		log.info("请求数据：" + request);
		ZcResult result = restTemplate().postForObject(baseHost + url, request, ZcResult.class);
		log.info("请求响应：" + result);
		return result;
	}

	public static <T> ZcResult<T> getForObject(String url) {
		log.info("请求地址：GET " +baseHost+ url);
		ZcResult<T> result = restTemplate().getForObject(baseHost + url, ZcResult.class);
		log.info("请求响应：" + result);
		return result;
	}

	public static <T> ZcResult<T> getForObject(String url, Map<String, Object> urlParams) {
		log.info("请求地址：GET " + url);
		ZcResult<T> result = restTemplate().getForObject(baseHost + url, ZcResult.class, urlParams);
		log.info("请求响应：" + result);
		return result;
	}
	public static ZcResult putForObject(String url, Object request) {
		log.info("请求地址：PUT " + url);
		log.info("请求数据：" + request);
		HttpHeaders headers = new HttpHeaders();
		// 请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity requestEntity = new HttpEntity(request, headers);
		ZcResult result = restTemplate().exchange(baseHost + url, HttpMethod.PUT, requestEntity, ZcResult.class)
				.getBody();
		log.info("请求响应：" + result);
		return result;
	}

	public static RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonMessageConverter.setObjectMapper(new JackObjectMapper());
		messageConverters.add(jsonMessageConverter);
		restTemplate.setMessageConverters(messageConverters);
		restTemplate.setRequestFactory(getClientHttpRequestFactory());
		return restTemplate;
	}

	private static HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClient());
		return clientHttpRequestFactory;
	}

	public static String genJwt(String uuid) {
		return JwtUtil.create("nesting", "intbee", uuid, 3600, "fa2jBYYXukgRft4J942F3TDCmMXqedcW");
	}

}
