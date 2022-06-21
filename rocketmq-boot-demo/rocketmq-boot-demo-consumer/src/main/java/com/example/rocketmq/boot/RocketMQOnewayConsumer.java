package com.example.rocketmq.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author WuYingBin
 * Date 2022/6/21
 */
@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "rocketmq-boot-oneway-consumer",
        topic = "test-oneway-rocketmq"
)
public class RocketMQOnewayConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();
        String content = new String(body, StandardCharsets.UTF_8);
        log.info("接受到消息:{}", content);
    }
}
