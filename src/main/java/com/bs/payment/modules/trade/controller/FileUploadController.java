package com.bs.payment.modules.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bs.payment.common.ZcResult;
import com.bs.payment.modules.trade.service.FileUploadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传接口
 * 
 * @author gunzhenling
 */
@Slf4j
@RestController
@RequestMapping("/bs/file")
@Api(tags = "文件上传接口")
public class FileUploadController {
	
	@Autowired
	private  FileUploadService fileUploadService;
	
	@GetMapping("/upload/image")
	@ApiOperation(value = "上传图片接口")
	public ZcResult<String> uploadImage(@RequestParam(value="picture_file",required=true) MultipartFile pictureFile) throws Exception {
		
		log.info("fileUpload-uploadImage-info: request ");
		
		String pictureUrl = fileUploadService.uploadImage(pictureFile);
		
		log.info("fileUpload-uploadImage-info: return  pictureUrl={}",pictureUrl);
		
		return ZcResult.ok(pictureUrl);
	}

}
