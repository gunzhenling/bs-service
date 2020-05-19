package com.bs.payment.modules.trade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bs.payment.common.constans.Consts;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.service.FileUploadService;
import com.bs.payment.util.BsFileUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {
	
//	file.image-url: /image/
//	图片存储前缀路径
	@Value("${file.image-url}")
	private String fileImageUrlPrefix;

	@Override
	public String uploadImage(MultipartFile pictureFile) {
		 
		String filePath=fileImageUrlPrefix;
		String fileType=Consts.FilelType.IMAGE;
		
		String fileUrl= "";
		try {
			
			Map<String, String> dataMap = BsFileUtil.uploadFile(pictureFile, filePath, fileType);
			String returnCode = dataMap.get("return_code");
			 
			if(!Consts.SUCCESS.equals(returnCode)) {
				log.error("fileUpload-uploadImage-error: 上传图片发生异常 returnCode={} ",returnCode);
				BusinessException.error(2, returnCode);
			}
			
	        fileUrl = dataMap.get("fileUrl");
			
		} catch (Exception e) {
			 
			log.error("fileUpload-uploadImage-error: 上传图片发生异常 err={} ",e.getMessage());
			BusinessException.error(2, "上传图片发生异常 ");
		}
		
		log.info("fileUpload-uploadImage-info: 上传图片成功,fileUrl={}",fileUrl);
		
		return fileUrl;
	}

}
