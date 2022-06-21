package com.example.rocketmq.demo.oneway;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @author WuYingBin
 * Date 2022/6/21
 */
public class OnewayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("192.168.1.112:9876");
        producer.setProducerGroup("oneway-producer");
        producer.start();
        Message message = new Message("oneway-topic", "单向发送的消息".getBytes(StandardCharsets.UTF_8));
        producer.sendOneway(message);
        System.out.println("消息发送成功");
        producer.shutdown();
    }
}
