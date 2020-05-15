package com.bs.payment.modules.trade.vo;

import javax.validation.constraints.NotEmpty;

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
	

	@NotEmpty(message="标题不能为空")
	@ApiModelProperty(value="标题")
	private String title;
	

	@NotEmpty(message="版费不能为空")
	@ApiModelProperty(value="版费")
	@JSONField(name="b_fee")
	@JsonProperty("b_fee")
	private String bFee;
	

	@NotEmpty(message="定制费不能为空")
	@ApiModelProperty(value="定制费")
	@JSONField(name="made_fee")
	@JsonProperty("made_fee")
	private String madeFee;
	

	@NotEmpty(message="定做工期不能为空")
	@ApiModelProperty(value="定做工期")
	@JSONField(name="prod_date")
	@JsonProperty("prod_date")
	private String prodDate;

}
