package com.bs.payment.modules.trade.dto;



import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Data;
/**
 *  留言板
 * 
 * @author zhenling
 */
@Data
@ApiModel(value="留言信息")
public class CommonProblemDto   {
	
	private Long	 id;
	/**
	 * 用户主键id
	 */
	private Long userId;
	/**
	 * 内容
	 */
	private String content;

	@JSONField(name="create_time")
	@JsonProperty("create_time")
	private Date	createTime;

}
