package com.example.rocketmq.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
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
@EnableConfigurationProperties(RocketMQProducerProperties.class)
public class RocketMQProducerConfiguration {

    private RocketMQProducerProperties rocketMQProducerProperties;

    @SneakyThrows
    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.producer", value = "enable", havingValue = "true")
    public DefaultMQProducer defaultMQProducer() {
        log.info("DefaultMQProducer 正在创建 ---------------");
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setNamesrvAddr(rocketMQProducerProperties.getNameserverAddress());
        defaultMQProducer.setVipChannelEnabled(false);
        defaultMQProducer.setProducerGroup(rocketMQProducerProperties.getGroupName());
        defaultMQProducer.setMaxMessageSize(rocketMQProducerProperties.getMaxMessageSize());
        defaultMQProducer.setSendMsgTimeout(rocketMQProducerProperties.getSendMessageTimeOut());
        defaultMQProducer.setRetryTimesWhenSendFailed(rocketMQProducerProperties.getRetryTimesWhenSendFailed());
        defaultMQProducer.start();
        log.info("DefaultMQProducer 创建完成 ---------------");
        return defaultMQProducer;
    }

    @Autowired
    public void setRocketMQProducerProperties(RocketMQProducerProperties rocketMQProducerProperties) {
        this.rocketMQProducerProperties = rocketMQProducerProperties;
    }
}
