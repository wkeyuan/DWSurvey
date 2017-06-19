package com.key.common.plugs.jms.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 用来发送消息
 * @author Administrator
 *
 */
public class InvokeMessageProducer {
	private JmsTemplate template;

	private Queue destination;

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public void setDestination(Queue destination) {
		this.destination = destination;
	}

	public void send(InvokeMessage invokeMessage) {
		template.convertAndSend(this.destination, invokeMessage);
	}
	
	public void send() {
		template.send(this.destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message=session.createTextMessage();
				System.out.println("发出消息,延时10秒.");
				String msgId=message.getJMSMessageID();
				System.out.println("msgId:"+msgId);
				message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 20*1000);
				message.setStringProperty("content", "内容content...");
				return message;
			}
		});
	}
}
