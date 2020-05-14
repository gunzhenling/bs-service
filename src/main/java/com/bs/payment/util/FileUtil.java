package com.bs.payment.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bs.payment.common.constans.Consts;
import com.mysql.cj.util.Base64Decoder;
import com.thoughtworks.xstream.core.util.Base64Encoder;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传帮助类
 * @author zhenling
 *
 */
@Component
@Slf4j
public class FileUtil {
	
	//文件上传工具类服务方法
    public static Map<String,Object> uploadFile(MultipartFile file, String filePath, String fileType) throws Exception {
 
        Map<String,Object>  dataMap = new HashMap<String,Object> ();
        try {
            String fileName = "";
            String docUrl = "/document/" + fileName;
            if("document".equals(fileType)) {
                if(file == null) {
                    dataMap.put("return_code", "上传文件不能为空");
                    return dataMap;
                }
 
                fileName = file.getOriginalFilename();
                String fileSuf = fileName.substring(fileName.lastIndexOf(".")+1);
 
                if(!"pdf".equals(fileSuf) && !"doc".equals(fileSuf) && !"docx".equals(fileSuf)) {
                    dataMap.put("return_code", "文件类型只能是pdf,doc,docx");
                    return dataMap;
                }
 
                if(file.getSize() > 5242880) {
                    dataMap.put("return_code", "文件大小不能超过5MB");
                    return dataMap;
                }
                docUrl = "/document/" + fileName;
 
            } else if("image".equals(fileType)) {
 
                fileName = file.getOriginalFilename();
                String fileSuf = fileName.substring(fileName.lastIndexOf(".")+1);
 
                if(!"png".equals(fileSuf) && !"jpg".equals(fileSuf) && !"jpeg".equals(fileSuf)) {
                    dataMap.put("return_code", "文件类型只能是png,jpg,jpeg");
                    return dataMap;
                }
 
                if(file.getSize() > 5242880) {
                    dataMap.put("return_code", "图片大小不能超过5MB");
                    return dataMap;
                }
                docUrl = "/company/" + fileName;
            }
            File targetFile = new File(filePath);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
//          放到对应路径目录中
            FileOutputStream out = new FileOutputStream(filePath+fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
            dataMap.put("return_code","success");
            dataMap.put("fileUrl", docUrl);
            
//          返回字节 放入库
            byte[] bytes = file.getBytes();
            dataMap.put("bytes", bytes);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return dataMap;
        }
    }
    
    
  //图片转换为base64 str
    public static  String uploadFileReturnBase64Str(MultipartFile file, String fileType) {
 
    	 if(file == null) {
             
             log.warn("file-uploadFileReturnBase64Str-warn: 上传文件为空 fileType={}",fileType);
             return "";
         }
    	 
    	 String image ="";
        try {
            String fileName = "";
            if(Consts.FilelType.DOCUMENT.equals(fileType)) {
 
                fileName = file.getOriginalFilename();
                String fileSuf = fileName.substring(fileName.lastIndexOf(".")+1);
 
                if(!"pdf".equals(fileSuf) && !"doc".equals(fileSuf) && !"docx".equals(fileSuf)) {
                     
                    log.warn("file-uploadFileReturnBase64Str-warn: 文件类型只能是pdf,doc,docx fileSuf={}",fileSuf);
                    return "";
                }
 
                if(file.getSize() > 5242880) {
                    
                    log.warn("file-uploadFileReturnBase64Str-warn: 文件大小不能超过5MB file.getSize()={}",file.getSize());
                    return "";
                }
 
            } else if(Consts.FilelType.IMAGE.equals(fileType)) {
 
                fileName = file.getOriginalFilename();
                String fileSuf = fileName.substring(fileName.lastIndexOf(".")+1);
 
                if(!"png".equals(fileSuf) && !"jpg".equals(fileSuf) && !"jpeg".equals(fileSuf)) {
                     
                    log.warn("file-uploadFileReturnBase64Str-warn: 文件类型只能是png,jpg,jpeg fileSuf={}",fileSuf);
                    return "";
                }
 
                if(file.getSize() > 5242880) {
                   
                    log.warn("file-uploadFileReturnBase64Str-warn: 文图片大小不能超过5MB file.getSize()={}",file.getSize());
                    return "";
                }
            }
            
//          返回字节 放入库
//            Base64 encoder = new Base64();
//            image = encoder.encode(file.getBytes());
            
            image = Base64.getEncoder().encodeToString(file.getBytes());
          
             
            
        } catch (IOException e) {
            
            log.error("file-uploadFileReturnBase64Str-error: 图片文件解析失败  errMsg={} ",e.getMessage());
            
        }  
        
        return image;
    }
    

    //数据库中的base64数据转换为图片
      public static  String baseDataTransferImage(Object imageBaseData) {
    	  
    	  String pictureStr="";
    	  
    	  byte[] pictureByte = (byte[])imageBaseData; 
    	  byte[] decodeByte = Base64.getDecoder().decode(pictureByte);
    	  pictureStr = new String(decodeByte);
    	  
    	  return pictureStr;
    	  
      }
    

}
