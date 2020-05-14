package com.bs.payment.modules.trade.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定制信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="定制信息")
public class CustomMadeVO {
	

	@ApiModelProperty(value="标题")
	private String title;
	

	@ApiModelProperty(value="版费")
	@JSONField(name="b_fee")
	@JsonProperty("b_fee")
	private String bFee;
	

	@ApiModelProperty(value="定制费")
	@JSONField(name="made_fee")
	@JsonProperty("made_fee")
	private String madeFee;
	

	@ApiModelProperty(value="定做工期")
	@JSONField(name="prod_date")
	@JsonProperty("prod_date")
	private String prodDate;

}
