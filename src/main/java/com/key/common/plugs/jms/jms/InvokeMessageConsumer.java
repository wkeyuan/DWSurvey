package com.key.common.plugs.jms.jms;

import javax.jms.Message;

/** 
 *消费消息 
 * @author kongqz 
 */  
public class InvokeMessageConsumer{  
  
    /** 
     * @author Administrator 
     * */  
    public void printMyOut(InvokeMessage invokeMessage) {  
        System.out.println("等待1秒再处理");  
        /*try {
            Thread.sleep(1000);  
        } catch (InterruptedException e) {
            e.printStackTrace();  
        }  */
        //System.out.println("执行业务操作["+invokeMessage.getName()+"],["+invokeMessage.getOperate()+"],["+invokeMessage.getMsg()+"]");
        System.out.println("消息消费11");
    }  
    
    public void printMyOut01(Message message){
    	System.out.println("执行业务操作。。。");
    }
  
}  