package com.example.rocketmq.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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

    public void sendMessageInTransaction(String topic, String msg) throws InterruptedException {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder.withPayload(msg).build();
            String destination = topic + ":" + tags[i % tags.length];
            log.info("destination:{}", destination);
            TransactionSendResult transactionSendResult = this.rocketMQTemplate.sendMessageInTransaction(destination, message, destination);
            log.info("sendResult:{}", transactionSendResult);
            TimeUnit.SECONDS.sleep(3);
        }
    }

    public void sendTransactionMessage(String destination, String msg) {
        Message<String> message = MessageBuilder.withPayload(msg).build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(destination,
                message, null);
        // 发送状态
        String sendStatus = transactionSendResult.getSendStatus().name();
        // 本地事务执行状态
        String localTxState = transactionSendResult.getLocalTransactionState().name();
        log.info("send tx message payload:{}, sendStatus:{}, localTXState:{}", msg, sendStatus, localTxState);
    }

    @Autowired
    public void setRocketMQTemplate(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }
}
