package com.bs.payment.modules.trade.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件接口
 * @author zhenling
 *
 */
public interface FileUploadService {
	
	/**
	 * 上传文件，返回存储的路劲
	 * @param pictureFile
	 * @return
	 */
	String uploadImage(MultipartFile pictureFile);

}
