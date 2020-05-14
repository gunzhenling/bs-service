package com.bs.payment.modules.trade.vo;

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
	
	@ApiModelProperty(value="规格")
	private String standards;

}
