package com.example.rocketmq.demo.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author WuYingBin
 * date: 2022/6/15
 */
public class TransactionalConsumer {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        try {
            // 设置消费者组、NameServer地址等基本信息
            consumer.setConsumerGroup("transaction-consumer");
            consumer.setNamesrvAddr("localhost:9876");
            // 设置消费者订阅的 Topic、Tag 信息
            consumer.subscribe("transaction-topic", "*");
            // 设置消息监听器（并发监听）
            consumer.setMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
                for (MessageExt messageExt : list) {
                    System.out.println(messageExt);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            // 消费者启动
            consumer.start();

        } catch (MQClientException e) {
            throw new RuntimeException(e);
        } finally {
            consumer.shutdown();
        }
    }
}
