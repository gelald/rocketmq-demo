package com.example.rocketmq.demo.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author WuYingBin
 * Date 2022/6/20
 */
public class OrderConsumer {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        try {
            consumer.setNamesrvAddr("192.168.1.112:9876");
            consumer.setConsumerGroup("order-consumer");
            // 设置消费者第一次启动是从队列头部开始还是队列尾部开始消费
            // 如果不是第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            // 设置消费者订阅的Topic和Tag
            consumer.subscribe("order-topic", "*");
            consumer.setMessageListener((MessageListenerOrderly) (messageExtList, context) -> {
                if (CollectionUtils.isEmpty(messageExtList)) {
                    System.out.println("MQ 接收的消息为空");
                    return ConsumeOrderlyStatus.SUCCESS;
                }
                for (MessageExt messageExt : messageExtList) {
                    String topic = messageExt.getTopic();
                    String keys = messageExt.getKeys();
                    String body = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                    System.out.println("MQ消息topic=" + topic + ", keys=" + keys + ", 消息内容=" + body);
                }
                return ConsumeOrderlyStatus.SUCCESS;
            });
            consumer.start();
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }
}
