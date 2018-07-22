package com.mmr.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mmr.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
	
	private static final String QUEUE_NAME = "test_simple_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		//获取一个连接
		Connection conn = ConnectionUtil.getConnection();
		
		//从连接中获取一个通道
		Channel channel = conn.createChannel();
		
		//声明队列
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		
		String msg = "hello simple";
		
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		
		System.out.println("------- send"+msg);
		
		channel.close();
		conn.close();
	}

}
