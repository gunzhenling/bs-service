package com.bs.payment.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class BsFileUtil {
	
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
            
        } catch (Exception e) {
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
    public  static String getRootPath() {
    	
    	String property = System.getProperty("user.dir");
//    	/Users/zhenling/mygit/bs-service
    	return property;
    }
    
    private static String getHostIp(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null 
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":")==-1){
                        System.out.println("本机的IP = " + ip.getHostAddress());
                        String hostIp="http://"+ ip.getHostAddress();
                        return hostIp;
                    } 
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**	 
     * * 替换文本文件中的 非法字符串	 
     * * @param path
     *  * @throws IOException	 */
    public static void replaConfigJsHostIp() {			
    	
    	String rootPath = getRootPath();
    	String hostIp = getHostIp();
    	String path=rootPath+"/wxapp/utils/config.js";
		try {
			replacTextContent(path, hostIp);
		} catch (IOException e) {
			log.warn("bsFileUtil-replaConfigJsHostIp-warn: config js 文件 ip 覆盖失败  err={}",e.getMessage());
		}
		
    }
    
    /**	 
     * * 替换文本文件中的 非法字符串	 
     * * @param path
     *  * @throws IOException	 */
    public static void replacTextContent(String path,String hostIp) throws IOException{			
    	//原有的内容			
    	String srcStr = "";        			
    	//要替换的内容	       
    	String replaceStr = hostIp;     	        
    	// 读  	        
    	File file = new File(path);   	       
    	FileReader in = new FileReader(file);  	       
    	BufferedReader bufIn = new BufferedReader(in);  	        // 内存流, 作为临时流  	       
    	CharArrayWriter  tempStream = new CharArrayWriter();  	        // 替换  	       
    	String line = null;  	       
    	
    	String regex = "http://\\w+(\\.\\w+){3}"; 
    	Pattern p = Pattern.compile(regex);
    	
    	while ( (line = bufIn.readLine()) != null) {  
    		
    		Matcher m = p.matcher(line);
    		while(m.find()){
    			
    			srcStr = m.group();
    		 	System.out.println("====group:"+srcStr);			
    			// 替换每行中, 符合条件的字符串  	           
    		 	line = line.replaceAll(srcStr, replaceStr);  	            // 将该行写入内存  	    
    		}
    		
    		tempStream.write(line);  	            // 添加换行符  	           
    		tempStream.append(System.getProperty("line.separator"));  	    
    	   
    	}  	        // 关闭 输入流  	      
    	bufIn.close();  	        // 将内存中的流 写入 文件  	     
    	FileWriter out = new FileWriter(file);  	       
    	tempStream.writeTo(out);  	        
    	out.close();  	       
    	System.out.println("====path:"+path);			
    	 
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
		/*System.out.println("getAbsolutePath:"+getAbsolutePath());// /Users/zhenling/mygit/bs-service/target/classes
		File upload = new File(getAbsolutePath(),"static/images/upload/");// /Users/zhenling/mygit/bs-service/target/classes/static/images/upload
		
		String property = System.getProperty("user.dir");
		System.out.println("user.dir:"+property);
		///Users/zhenling/mygit/bs-service
		
		 File targetFile = new File(property,"/frontend/public/images/");
         if(!targetFile.exists()){
             targetFile.mkdirs();
         }

         System.out.println("targetFile.getAbsolutePath:"+targetFile.getAbsolutePath());*/
         // /Users/zhenling/mygit/bs-service/frontend/public/images
    	
    	
    	
    	String rootPath = getRootPath();
    	String hostIp = getHostIp();
    	String path=rootPath+"/wxapp/utils/config.js";
		replacTextContent(path, hostIp);
    	 
    		
         
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
