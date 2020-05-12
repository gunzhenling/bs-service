package com.bs.payment.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.bs.payment.util.ZcJsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息发送器
 * 
 * @author fanhang
 */
@Slf4j
@Component
public class MessageSender {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * 发送消息
	 * @param topic
	 * @param key
	 * @param obj
	 */
	private void send(String topic, String key, Object obj) {
		String message = ZcJsonUtil.nonNullCaseMapper().toJson(obj);
		kafkaTemplate.send(topic, key, message);
		log.info("message sent: {} # {} : {}", topic, key, message);
	}
}
