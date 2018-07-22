package com.mmr.workfair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mmr.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * @author Administrator
 *消费者
 */
public class Recv1 {
	
	private static final String QUEUE_NAME = "test_work_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//获取连接
		Connection conn = ConnectionUtil.getConnection();
		
		//创建频道
		final Channel channel = conn.createChannel();
		
		//队列申明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		//保证一次只发一个
		channel.basicQos(1);
		
		//定义一个消费者
		Consumer consumer1 = new DefaultConsumer(channel) {
			//消息到达触发这个方法
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body,"utf-8");
				System.out.println("【1】 recv msg:"+msg);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					System.out.println("[1] done");
					//手动回执一个消息
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		//自动应答
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, consumer1);
	}
	
}
