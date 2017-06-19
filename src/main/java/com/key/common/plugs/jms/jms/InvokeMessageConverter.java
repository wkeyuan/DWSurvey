package com.key.common.plugs.jms.jms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * @see MessageConverter 将要发送的pojo转化成activemq可以辨识的类型
 */
public class InvokeMessageConverter implements MessageConverter {
	// 接收消息时候使用
	public Object fromMessage(Message msg) throws JMSException,
			MessageConversionException {
		if (msg instanceof ObjectMessage) {
			if (msg instanceof ObjectMessage) {
				HashMap<String, byte[]> map = (HashMap<String, byte[]>) ((ObjectMessage) msg).getObjectProperty("Map");
				if(map!=null){
					try {
						// POJO must implements Seralizable 
						ByteArrayInputStream bis = new ByteArrayInputStream(map.get("InvokeMessage"));
						ObjectInputStream ois = new ObjectInputStream(bis);
						Object returnObject = ois.readObject();
						return returnObject;
					} catch (IOException e) {
						System.out.println("fromMessage(Message)");
						e.printStackTrace();

					} catch (ClassNotFoundException e) {
						System.out.println("fromMessage(Message)");
						e.printStackTrace();
					}
				}else{
					System.out.println("message is empty");
				}
			}
		} else {
			//throw new JMSException("Msg:[" + msg + "] is not Map");
		}
		return null;
	}

	// 发送消息时候使用
	public Message toMessage(Object obj, Session session) throws JMSException,
			MessageConversionException {

		if (obj instanceof InvokeMessage) {
			ActiveMQObjectMessage objMsg = (ActiveMQObjectMessage) session
					.createObjectMessage();
			long delay=5*1000;
        	System.out.println("延时："+delay/1000+"秒");
        	System.out.println("msgId:"+objMsg.getJMSMessageID());
        	objMsg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
//        	objMsg.setExpiration(2000);
        	
			Map<String, byte[]> map = new HashMap<String, byte[]>();
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos);
				oos.writeObject(obj);
				map.put("InvokeMessage", bos.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
			objMsg.setObjectProperty("Map", map);
			return objMsg;
		} else {
			throw new JMSException("Object:[" + obj + "] is not InvokeMessage");
		}
	}
	
}
