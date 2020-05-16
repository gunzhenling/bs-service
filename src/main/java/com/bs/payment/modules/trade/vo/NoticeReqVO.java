package com.bs.payment.modules.trade.vo;
 
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 
 * @author zhenling
 */
@Data
@ApiModel(value="公告请求信息")
public class NoticeReqVO   {
	
	@ApiModelProperty(value="公告主键id-修改公告接口必传")
	private Long id;
	/**
	 * 标题
	 */
	@NotEmpty(message="标题不能为空")
	@ApiModelProperty(value="标题")
	private String title;
	/**
	 * 内容
	 */
	@NotEmpty(message="内容不能为空")
	@ApiModelProperty(value="内容")
	private String content;

}
