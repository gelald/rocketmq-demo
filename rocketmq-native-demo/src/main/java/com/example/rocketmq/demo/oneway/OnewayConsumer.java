package com.example.rocketmq.demo.oneway;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author WuYingBin
 * Date 2022/6/21
 */
public class OnewayConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr("192.168.1.112:9876");
        consumer.setConsumerGroup("oneway-consumer");
        // 设置消费者第一次启动是从队列头部开始还是队列尾部开始消费
        // 如果不是第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置消费者订阅的Topic和Tag
        consumer.subscribe("oneway-topic", "*");
        consumer.setMessageListener((MessageListenerConcurrently) (messageExtList, context) -> {
            if (CollectionUtils.isEmpty(messageExtList)) {
                System.out.println("MQ 接收的消息为空");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            for (MessageExt messageExt : messageExtList) {
                String topic = messageExt.getTopic();
                String tags = messageExt.getTags();
                String body = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                System.out.println("MQ消息topic=" + topic + ", tags=" + tags + ", 消息内容=" + body);
                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }
}
