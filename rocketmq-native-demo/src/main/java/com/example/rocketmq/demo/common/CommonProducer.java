package com.example.rocketmq.demo.common;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @author WuYingBin
 * date: 2022/6/15
 */
public class CommonProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("192.168.1.112:9876");
        producer.setProducerGroup("common-producer");
        producer.setVipChannelEnabled(false);
        producer.start();
        Message message = new Message("TestTopic", "TestTag", "hello".getBytes(StandardCharsets.UTF_8));
        SendResult sendResult = producer.send(message);
        System.out.println("消息发送响应: " + sendResult);
    }
}
