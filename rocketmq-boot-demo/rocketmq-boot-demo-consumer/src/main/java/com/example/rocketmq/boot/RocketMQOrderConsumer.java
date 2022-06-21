package com.example.rocketmq.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author WuYingBin
 * Date 2022/6/2
 */
@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "rocketmq-boot-order-consumer",
        topic = "test-orderly-rocketmq",
        consumeMode = ConsumeMode.ORDERLY)
public class RocketMQOrderConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();
        String content = new String(body, StandardCharsets.UTF_8);
        log.info("接受到消息: {}", content);
    }
}
