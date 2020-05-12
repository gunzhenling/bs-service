package com.bs.payment.modules.trade.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bs.payment.modules.trade.vo.PayQueryRequestVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付查询监听
 * 
 * @author fanhang
 */
@Slf4j
@Component
public class PayQueryListener implements ApplicationListener<PayQueryListener.Event> {

	/*@Autowired
	private RetryService retryService;*/
	
	@Override
	public void onApplicationEvent(PayQueryListener.Event event) {
		/*try {
			retryService.payQuery(event.reqVO);
		} catch (Exception e) {
			log.warn("payQuery-event execute error: {}", event.reqVO);
		}*/
	}
	
	public static class Event extends ApplicationEvent {
		private static final long serialVersionUID = -7786526681182388578L;
		
		private PayQueryRequestVO reqVO;
		public Event(PayQueryRequestVO reqVO) {
			super(reqVO);
			this.reqVO = reqVO;
		}
	}
}
