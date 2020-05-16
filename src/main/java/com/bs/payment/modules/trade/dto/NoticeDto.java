package com.bs.payment.modules.trade.dto;



import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Data;
/**
 * 
 * @author zhenling
 */
@Data
@ApiModel(value="公告信息")
public class NoticeDto   {
	
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	
	@JSONField(name="create_time")
	@JsonProperty("create_time")
	private Date	createTime;

}
