package com.bs.payment.modules.trade.vo;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 规格信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="规格信息")
public class SpecificationVO {
	
	@NotNull(message="规格不能为空")
	@ApiModelProperty(value="规格")
	private String standards;

}
