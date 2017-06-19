package com.key.common.plugs.jms.simple;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.key.common.base.entity.User;
import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.key.common.plugs.email.Email;


/**
 * JMS用户变更消息生产者.
 * 
 * 使用jmsTemplate将用户变更消息分别发送到queue与topic.
 * 
 * @author KeYuan
 */

public class NotifyMessageProducer {
	
	private JmsTemplate jmsTemplate;
	private Destination notifyQueue;
	private Destination notifyTopic;
	
	public void sendQueue(final User user) {
		sendMessage(user, notifyQueue);
	}

	public void sendTopic(final User user) {
		sendMessage(user, notifyTopic);
	}

	/**
	 * 使用jmsTemplate最简便的封装convertAndSend()发送Map类型的消息.
	 */
	private void sendMessage(User user, Destination destination) {
		Map map = new HashMap();
		map.put("userName", user.getName());
		map.put("email", user.getEmail());

		jmsTemplate.convertAndSend(destination, map);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}

	public void setNotifyTopic(Destination nodifyTopic) {
		this.notifyTopic = nodifyTopic;
	}
	
	public void sendQueue(final Email mail) {
		sendMessage(mail, notifyQueue);
	}

	public void sendTopic(final Email mail) {
		sendMessage(mail, notifyTopic);
	}
	
	/**
	 * 使用jmsTemplate最简便的封装convertAndSend()发送Map类型的消息.
	 * @throws JMSException 
	 */
	private void sendMessage(Email mail, Destination destination) {
		Map map = new HashMap();
		/*map.put("subject", mail.getSubject());
		map.put("to", mail.getTo());
		map.put("date", mail.getDate());
		map.put("username", mail.getUsername());
		map.put("content", mail.getContent());*/
		map.put("subject", "柯远");
		map.put("to", "xx");
		map.put("date", "2013-03-20");
		map.put("username", "keyuan");
		map.put("content", "activmq测试邮件");
		jmsTemplate.convertAndSend(destination, map);
		
//		jmsTemplate.send(destination,new MessageCreator() {
//			
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				TextMessage	message=session.createTextMessage("textMsg....柯远");
//				message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 65*1000);
//				System.out.println("等65秒后再发");
//				return message;
//			}
//		});
		
	}
	
	public void send(){
		jmsTemplate.send(notifyQueue,new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage	message=session.createTextMessage("textMsg....柯远");
				message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 65*1000);
				System.out.println("等65秒后再发");
				return message;
			}
		});
	}
	
}
