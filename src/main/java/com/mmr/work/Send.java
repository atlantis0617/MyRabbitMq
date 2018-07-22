package com.mmr.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mmr.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author Administrator
 *消息发送者
 *                            -----------C1
 *p--------QUEUE
 *							   ------------C2
 */
public class Send {
	
	private static final String QUEUE_NAME = "test_work_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		//获取一个连接
		Connection conn = ConnectionUtil.getConnection();
		
		//从连接中获取一个通道
		Channel channel = conn.createChannel();
		
		//声明队列
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		
		for (int i = 0; i < 50; i++) {
			String msg = "msg"+i;
			
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			Thread.sleep(i*20);
		}
		channel.close();
		conn.close();
	}
}
