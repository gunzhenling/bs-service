package com.bs.payment.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author zhenling
 *
 */
public class StringFilterUtil {
	
	/**
	 * body（商品描述）、subject（商品名称）、
	 *  extra_common_param（公用回传参数）
	 *   不能包含特殊字符（如：#、%、&、+）、敏感词汇，也不能使用外国文字（旺旺不支持的外文，如：韩文、泰语、藏文、蒙古文、阿拉伯语）
	 * @param str
	 * @return
	 */
	public static String stringFilter (String str){
		
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}
