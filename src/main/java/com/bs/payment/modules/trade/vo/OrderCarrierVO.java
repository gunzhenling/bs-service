package com.bs.payment.modules.trade.vo;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单提交信息
 * @author zhenling
 *
 */
@Data
@ApiModel(value="订单提交信息")
public class OrderCarrierVO {
	
	/**
	 *  订单号
	 */
	@ApiModelProperty(value="订单号")
	@JSONField(name="order_no")
	@JsonProperty("order_no")
	private String orderNo;
	
	/**
	 *  物流类型，0：售前订单物流 1：售后退款物流
	 */
	@ApiModelProperty(value="物流类型,0：售前订单物流 1：售后退款物流")
	@JSONField(name="carrier_type")
	@JsonProperty("carrier_type")
	private int carrierType;
	/**
	 *  快递公司名称
	 */
	@ApiModelProperty(value="快递公司名称")
	@JSONField(name="company_name")
	@JsonProperty("company_name")
	private String companyName;
	/**
	 *  快递公司编码
	 */
	@ApiModelProperty(value="快递公司编码")
	@JSONField(name="company_no")
	@JsonProperty("company_no")
	private String companyNo;
	/**
	 *  快递单号
	 */
	@ApiModelProperty(value="快递单号")
	@JSONField(name="carrier_no")
	@JsonProperty("carrier_no")
	private String carrierNo;
	/**
	 *  创建时间
	 */
	@ApiModelProperty(value=" 创建时间")
	@JSONField(name="create_time")
	@JsonProperty("create_time")
	private Date createTime;
	/**
	 *  更新时间
	 */
	@ApiModelProperty(value="更新时间")
	@JSONField(name="update_time")
	@JsonProperty("update_time")
	private Date updateTime;
	
	 
	@ApiModelProperty(value="物流状态链")
	@JSONField(name="carrier_tracks")
	@JsonProperty("carrier_tracks")
	private List<CarrierTracksVO> carrierTracks;
	
	

}
