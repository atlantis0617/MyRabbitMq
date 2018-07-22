package com.mmr.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mmr.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv {
	private static final String QUEUE_NAME = "test_simple_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
				//获取连接
				Connection conn = ConnectionUtil.getConnection();
				
				//创建频道
				Channel channel = conn.createChannel();
				
				//队列申明
				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
				
				//获取到达的消息
				Consumer consumer = new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag, Envelope envelope,BasicProperties properties, byte[] body) throws IOException {
						String msg = new String(body,"utf-8");
						System.out.println("new api recv:"+msg);
					}
				};
				
				channel.basicConsume(QUEUE_NAME, true, consumer);
				
	}
}
