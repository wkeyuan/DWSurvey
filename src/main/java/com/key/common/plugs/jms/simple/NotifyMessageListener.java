package com.key.common.plugs.jms.simple;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.key.common.plugs.email.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 消息的异步被动接收者.
 * 
 * 使用Spring的MessageListenerContainer侦听消息并调用本Listener进行处理.
 * 
 * @author KeYuan
 *
 */
public class NotifyMessageListener implements MessageListener {

	private static Logger logger = LoggerFactory.getLogger(NotifyMessageListener.class);

//	@Autowired(required = false)
//	private SimpleMailService simpleMailService;

	/**
	 * MessageListener回调函数.
	 */
	@Override
	public void onMessage(Message message) {
		try {
			message.getPropertyNames();
			/*MapMessage mapMessage = (MapMessage) message;
			// 打印消息详情
			String subject=mapMessage.getObject("subject").toString();
			String to=mapMessage.getObject("to").toString();
			String date=mapMessage.getObject("date").toString();
			String username=mapMessage.getObject("username").toString();
			String content=mapMessage.getObject("content").toString();*/
			
			String subject= "柯远";
			String to="xx";
			String date="2013-03-20";
			String username="keyuan";
			String content="activmq测试邮件";
			//mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 15*1000);
			System.out.println("收到了消息");
			logger.info("subject:" + subject+";to:"+to+";date:"+date+";username:"+username+";content:"+content+";");
			Email email=new Email();
			email.setContent(content);
			email.setDate(date);
			email.setSubject(subject);
			email.setTo(to);
			email.setUsername(username);
			// 发送邮件
//			if (simpleMailService != null) {
//				simpleMailService.sendNotificationMail("柯远。。。");
//			}
		} catch (JMSException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
