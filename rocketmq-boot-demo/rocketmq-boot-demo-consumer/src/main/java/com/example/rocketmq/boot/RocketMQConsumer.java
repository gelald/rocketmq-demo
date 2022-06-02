package com.example.rocketmq.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author WuYingBin
 * Date 2022/6/2
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "rocketmq-boot-consumer", topic = "TestBootTopic")
public class RocketMQConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String string) {
        log.info("接受到消息: {}", string);
    }
}
