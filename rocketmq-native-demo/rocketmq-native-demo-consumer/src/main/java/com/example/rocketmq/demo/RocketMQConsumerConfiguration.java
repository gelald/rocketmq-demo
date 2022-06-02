package com.example.rocketmq.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WuYingBin
 * Date 2022/6/1
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RocketMQConsumerProperties.class)
public class RocketMQConsumerConfiguration {

    private RocketMQConsumerProperties rocketMQConsumerProperties;
    private RocketMQConsumeMsgListenerProcessor rocketMQConsumeMsgListenerProcessor;

    @SneakyThrows
    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.consumer", value = "enable", havingValue = "true")
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        log.info("DefaultMQPushConsumer 正在创建 ---------------");
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameserverAddress());
        defaultMQPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName());
        defaultMQPushConsumer.setConsumeThreadMin(rocketMQConsumerProperties.getConsumeThreadMin());
        defaultMQPushConsumer.setConsumeThreadMax(rocketMQConsumerProperties.getConsumeThreadMax());
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(rocketMQConsumerProperties.getConsumeMessageBatchMaxSize());
        // 设置消费者第一次启动是从队列头部开始还是队列尾部开始消费
        // 如果不是第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置消费者订阅的Topic和Tag
        defaultMQPushConsumer.subscribe(rocketMQConsumerProperties.getTopics(), rocketMQConsumerProperties.getTag());
        defaultMQPushConsumer.setMessageListener(rocketMQConsumeMsgListenerProcessor);
        defaultMQPushConsumer.start();
        log.info("DefaultMQPushConsumer 创建完成 订阅的topics: {}", rocketMQConsumerProperties.getTopics());
        return defaultMQPushConsumer;
    }

    @Autowired
    public void setRocketMQConsumerConfiguration(RocketMQConsumerProperties rocketMQConsumerProperties) {
        this.rocketMQConsumerProperties = rocketMQConsumerProperties;
    }

    @Autowired
    public void setRocketMQConsumeMsgListenerProcessor(RocketMQConsumeMsgListenerProcessor rocketMQConsumeMsgListenerProcessor) {
        this.rocketMQConsumeMsgListenerProcessor = rocketMQConsumeMsgListenerProcessor;
    }
}
