package com.example.rocketmq.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author WuYingBin
 * Date 2022/6/2
 */
@Slf4j
@Component
public class RocketMQProducer {
    private RocketMQTemplate rocketMQTemplate;

    public void send(String topic, String msg) {
        log.info("发送消息: {}, Topic: {}", msg, topic);
        this.rocketMQTemplate.convertAndSend(topic, msg);
    }

    @Autowired
    public void setRocketMQTemplate(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }
}
