package com.bs.payment.modules.trade.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.modules.trade.dao.GiftTypeMapper;
import com.bs.payment.modules.trade.entity.BsGiftTypeEntity;
import com.bs.payment.modules.trade.service.GiftTypeyService;
import com.bs.payment.util.QueryBuilder;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GiftTypeyServiceImpl  extends ServiceImpl< GiftTypeMapper, BsGiftTypeEntity> implements GiftTypeyService {

	@Autowired
	private GiftTypeMapper giftTypeMapper;

	@Autowired
	private ResourceLoader	resourceLoader;

	@Value("${bs.gift.type}")
	private String giftTypePath;
	
	
	@Override
	public List<BsGiftTypeEntity> getGiftTypes() {
		

		List<BsGiftTypeEntity> selectList = giftTypeMapper.selectList(QueryBuilder.where("status",0));
		if(CollectionUtils.isEmpty(selectList)) {
			selectList = Lists.newArrayList();
		}
		
		return selectList;
	}


	@Override
	public void initGiftType() {
		 
 		List<BsGiftTypeEntity> selectList = giftTypeMapper.selectList(QueryBuilder.where("status",0));
 		
		if (CollectionUtils.isEmpty(selectList)) { 

//			TODO 环境 读取
			Resource resource = resourceLoader.getResource("classpath:"+giftTypePath);
			log.info("GiftTypeyService-initGiftType-info: 文件名称={}", resource.getFilename());
			try {
				Long filelength = resource.contentLength();
				java.io.InputStreamReader fileReader = new java.io.InputStreamReader(resource.getInputStream());
				char[] cbuf = new char[filelength.intValue()];
				fileReader.read(cbuf, 0, cbuf.length);
				fileReader.close();
				String productJson = new String(cbuf);
				List<BsGiftTypeEntity> clients= JSON.parseArray(productJson, BsGiftTypeEntity.class);
				
				for(BsGiftTypeEntity client:clients){
					giftTypeMapper.insert(client);
				}
				 
				log.info("GiftTypeyService-initGiftType-info: 默认-礼品类型配置-基础信息添加完成");
			} catch (Exception e) {

				log.error("GiftTypeyService-initGiftType-error: 礼品类型配置-基础信息初始化异常={}", e.getMessage());
			}
		}
		
	}
 
}
