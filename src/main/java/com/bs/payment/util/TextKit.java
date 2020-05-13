/**
 * 
 */
package com.bs.payment.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

/**
 * 描述：文本处理。1.下划线转化 2. SQL注入检测
 * 
 * @author qizai
 * @version: 0.0.1 2018年11月13日-上午11:09:17
 *
 */
public class TextKit {
	private static final Pattern	UNDERLINE_PATTERN	= Pattern.compile("([A-Za-z\\d]+)(_)?");
	private static final String[]	SQL_KEYWORDS		= { "master", "truncate", "insert", "select", "delete",
			"update", "declare", "alert", "drop" };

	private static final BigDecimal	FEN_100				= new BigDecimal(100);

	/**
	 * 判断对象是否不为null或空，支持Object/CharSequence/Array/Collection/Map
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotBlank(Object obj) {
		return !isBlank(obj);
	}

	/**
	 * 判断对象是否为null或空，支持Object/CharSequence/Array/Collection/Map
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isBlank(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof Optional) {
			return !((Optional) obj).isPresent();
		}
		if (obj instanceof CharSequence) {
			return isBlank((CharSequence) obj);
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		// else
		return false;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is empty (""), null or whitespace only.
	 * </p>
	 *
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is null, empty or whitespace
	 *         only
	 * @since 2.0
	 * @since 3.0 Changed signature from isBlank(String) to
	 *        isBlank(CharSequence)
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param value
	 * @return 金额转化：元转分
	 */
	public static int yuanToFen(BigDecimal value) {
		if (null == value) {
			return 0;
		}
		return FEN_100.multiply(value).intValue();
	}

	/**
	 * 
	 * @param value
	 * @return 金额转化：元转分
	 */
	public static String yuanToFenString(BigDecimal value) {
		if (null == value) {
			return "0";
		}
		return FEN_100.multiply(value).setScale(0).toString();
	}

	/**
	 * 
	 * @param value
	 * @return 金额转化：分转元
	 */
	public static BigDecimal fenToYuan(String value) {
		if (null == value) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(value).divide(FEN_100).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * SQL注入过滤
	 * 
	 * @param str
	 *            待验证的字符串
	 */
	public static String sqlInject(String str) throws IllegalArgumentException {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		// 去掉'|"|;|\字符
		str = StringUtils.replace(str, "'", "");
		str = StringUtils.replace(str, "\"", "");
		str = StringUtils.replace(str, ";", "");
		str = StringUtils.replace(str, "\\", "");
		// 转换成小写
		str = str.toLowerCase();
		// 判断是否包含非法字符
		for (String keyword : SQL_KEYWORDS) {
			if (str.indexOf(keyword) != -1) {
				throw new IllegalArgumentException("包含非法字符");
			}
		}
		return str;
	}

	public static void main(String[] args) {
		System.out.println(underlineToCamel("update_time"));
		System.out.println(underlineToCamel("updateTime"));
	}
	public static final char UNDERLINE='_';
	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

//	/**
//	 * 下划线转驼峰法(默认小驼峰)
//	 *
//	 * @param input
//	 *            源字符串
//	 * @param smallCamel
//	 *            大小驼峰,是否为小驼峰(驼峰，第一个字符是大写还是小写)，默认首字母小写
//	 * @return 转换后的字符串
//	 */
//	public static String underlineToCamel(String input, boolean... smallCamel) {
//		if (input == null || "".equals(input.trim())) {
//			return "";
//		}
//		int length = input.length();
//		StringBuilder result = new StringBuilder(length * 2);
//		Matcher matcher = UNDERLINE_PATTERN.matcher(input);
//		// 匹配正则表达式
//		while (matcher.find()) {
//			String word = matcher.group();
//			// 当是true 或则是空的情况
//			if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0) {
//				result.append(Character.toLowerCase(word.charAt(0)));
//			} else {
//				result.append(Character.toUpperCase(word.charAt(0)));
//			}
//			int index = word.lastIndexOf('_');
//			if (index > 0) {
//				result.append(word.substring(1, index).toLowerCase());
//			} else {
//				result.append(word.substring(1).toLowerCase());
//			}
//		}
//		return result.toString();
//	}

	/**
	 * 驼峰法转下划线
	 *
	 * @param line
	 *            源字符串
	 * @return 转换后的字符串
	 */
	public static String camelToUnderline(String input) {
		if (input == null || "".equals(input.trim())) {
			return "";
		}
		int length = input.length();
		StringBuilder result = new StringBuilder(length * 2);
		int resultLength = 0;
		boolean wasPrevTranslated = false;
		for (int i = 0; i < length; i++) {
			char c = input.charAt(i);
			if (i > 0 || c != '_') // skip first starting underscore
			{
				if (Character.isUpperCase(c)) {
					if (!wasPrevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != '_') {
						result.append('_');
						resultLength++;
					}
					c = Character.toLowerCase(c);
					wasPrevTranslated = true;
				} else {
					wasPrevTranslated = false;
				}
				result.append(c);
				resultLength++;
			}
		}
		return resultLength > 0 ? result.toString() : input;
	}

	/**
	 * 异或加密解密
	 * 
	 * @param text
	 *            加密源
	 * @param key
	 *            密钥
	 * @return
	 */
	public static byte[] xroEncrypt(byte[] bytes, String key) {
		byte[] keyData = key.getBytes(StandardCharsets.UTF_8);
		int keyIndex = 0;
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (bytes[i] ^ keyData[keyIndex]);
			if (++keyIndex == keyData.length) {
				keyIndex = 0;
			}
		}
		return bytes;
	}

	/**
	 * 异或加密
	 * 
	 * @param text
	 * @param key
	 * @return 结果使用hex编码
	 */
	public static String xroEncryptHex(String text, String key) {
		return Hex.encodeHexString(xroEncrypt(text.getBytes(StandardCharsets.UTF_8), key));
	}

	/**
	 * 异或解密
	 * 
	 * @param text
	 *            hex编码的加密结果
	 * @param key
	 * @return 原始加密数据
	 */
	public static String xroDecryptHex(String hexString, String key) throws DecoderException {
		return new String(xroEncrypt(Hex.decodeHex(hexString), key));
	}
}
