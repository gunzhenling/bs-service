package com.bs.payment.modules.trade.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 礼品请求信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="礼品请求信息")
public class BsGiftInfoReqVO  {
	 
	/**
	 * 礼品编码
	 */
	@ApiModelProperty(value="礼品编码")
	@NotNull(message="礼品编码不能为空")
	private Integer giftCode;
	/**
	 * 礼品名称
	 */
	@NotBlank(message="礼品名称不能为空")
	@ApiModelProperty(value="礼品名称")
	private String giftName;
	/**
	 * 礼品类型编码
	 */
	@NotNull(message="礼品类型编码不能为空")
	@ApiModelProperty(value="礼品类型编码")
	private Integer typeCode;
	/**
	 * 单个原始价格
	 */
	@ApiModelProperty(value="单个原始价格")
	private BigDecimal giftPrice;
	/**
	 * 单个实际价格
	 */
	@ApiModelProperty(value="单个实际价格")
	private BigDecimal realGiftPrice; 
	
	/**
	 * 已售数量
	 */
	@ApiModelProperty(value="已售数量")
	private Integer saleNum;
	/**
	 * 定制至少售卖数量
	 */
	@ApiModelProperty(value="定制至少售卖数量")
	private Integer limitNum;
	
	/**
	 * 礼品内容,html
	 * longtext
	 */
	@ApiModelProperty(value="礼品内容,html")
	private String content;
	/**
	 * 规格,json
	 */
	@ApiModelProperty(value="规格列表信息")
	private List<SpecificationVO> specification;
	/**
	 * 定制,json
	 */
	@ApiModelProperty(value="定制列表信息")
	private List<CustomMadeVO> customMade;
	
	/**
	 * 礼品图片 前端接收文件类型
	 */
	@ApiModelProperty(value="礼品图片")
	private MultipartFile pictureFile;
	 
}
