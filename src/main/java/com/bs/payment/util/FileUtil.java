package com.bs.payment.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.bs.payment.common.constans.Consts;

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
    @SuppressWarnings("finally")
	public static Map<String,String> uploadFile(MultipartFile file, String fileUrlPrefix, String fileType) throws Exception {
 
    	log.info("FileUtil-uploadFile-info: request fileUrlPrefix={},fileType={}",fileUrlPrefix,fileType);
    	
        Map<String,String>  dataMap = new HashMap<String,String> ();
        try {
            String fileName = "";
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
            }
            
            String rootPath = getRootPath();
//           绝对地址加上前缀地址 
            File targetFile = new File(rootPath,fileUrlPrefix);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
//          放到对应路径目录中
            String filePath = rootPath+fileUrlPrefix+fileName;
//            String filePath = fileUrlPrefix+fileName;
            FileOutputStream out = new FileOutputStream(filePath);
            out.write(file.getBytes());
            out.flush();
            out.close();
            dataMap.put("return_code",Consts.SUCCESS);
//         	返回前端路径，不包含根路径
            String returnFileUrl=fileUrlPrefix+fileName;
            dataMap.put("fileUrl", returnFileUrl);
             
            log.info("FileUtil-uploadFile-info:  放到对应路径目录filePath={},returnFileUrl={}",filePath,returnFileUrl);
            
//          返回字节 放入库
//            byte[] bytes = file.getBytes();
//            dataMap.put("bytes", bytes);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	
        	log.info("FileUtil-uploadFile-info: return  dataMap={}",JSON.toJSONString(dataMap));
        	
            return dataMap;
        }
    }
    
    public static void deleteFileImage(String fileUrlPrefix) {
    	

        String rootPath = getRootPath();
//       绝对地址加上前缀地址 
        File targetFile = new File(rootPath,fileUrlPrefix);
        if(!targetFile.exists()) {
    		 return;
    	}
//        targetFile.deleteOnExit();
        boolean delete = targetFile.delete();

    	log.info("FileUtil-deleteFileImage-info:  删除文件  boo={}",delete);
    	
    }
    
    /**
     * 获取项目根路径
     * bs-service
     * @return
     */
    public static String getRootPath() {
    	
    	String property = System.getProperty("user.dir");
//    	/Users/zhenling/mygit/bs-service
    	return property;
    }
    
    /**
     * 获取项目的绝对路径 精确到classes
     * /Users/zhenling/mygit/bs-service/target/classes
     * @return
     */
    private static  String getAbsolutePath() {
    	
    	File path = null;
		try {
			
			path = new File(ResourceUtils.getURL("classpath:").getPath());
			
			
		} catch (FileNotFoundException e) {
			 
			e.printStackTrace();
		}
    	if(!path.exists()) {
    		path = new File("");
    	}
    	String absolutePath = path.getAbsolutePath();
    	
    	return absolutePath;
    }
    
    public static void main(String[] args) throws IOException {
		System.out.println("getAbsolutePath:"+getAbsolutePath());// /Users/zhenling/mygit/bs-service/target/classes
		File upload = new File(getAbsolutePath(),"static/images/upload/");// /Users/zhenling/mygit/bs-service/target/classes/static/images/upload
		
		String property = System.getProperty("user.dir");
		System.out.println("user.dir:"+property);
		///Users/zhenling/mygit/bs-service
		
		 File targetFile = new File(property,"/frontend/public/images/");
         if(!targetFile.exists()){
             targetFile.mkdirs();
         }

         System.out.println("targetFile.getAbsolutePath:"+targetFile.getAbsolutePath());
         // /Users/zhenling/mygit/bs-service/frontend/public/images
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
      
      //根据库中地址拼上项目绝对路径=图片完整路径
      public static  String localUrlTransferImage(Object imageBaseData) {
    	  
    	  String pictureStr="";
    	  
    	  byte[] pictureByte = (byte[])imageBaseData; 
    	  byte[] decodeByte = Base64.getDecoder().decode(pictureByte);
    	  pictureStr = new String(decodeByte);
    	  
    	  return pictureStr;
    	  
      }
    

}
