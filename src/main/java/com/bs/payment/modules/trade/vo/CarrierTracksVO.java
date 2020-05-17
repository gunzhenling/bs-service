package com.bs.payment.modules.trade.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 物流状态链
 * @author DELL
 *
 */
@Data
@ApiModel(value="物流状态链")
public class CarrierTracksVO {
	 
	@ApiModelProperty(value="到达驿站描述")
	@JSONField(name="accept_station")
	@JsonProperty("accept_station")
	private String acceptStation;
	 
	
	@ApiModelProperty(value="到达时间")
	@JSONField(name="accept_time")
	@JsonProperty("accept_time")
	private Date acceptTime;

}
