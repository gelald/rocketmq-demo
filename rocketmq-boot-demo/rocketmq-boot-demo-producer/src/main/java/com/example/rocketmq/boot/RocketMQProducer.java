package com.example.rocketmq.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author WuYingBin
 * Date 2022/6/2
 */
@Slf4j
@Component
public class RocketMQProducer {
    private RocketMQTemplate rocketMQTemplate;

    public void sendCommonMessage(String destination, String messageBody) {
        Message<String> message = MessageBuilder.withPayload(messageBody).build();
        log.info("发送消息: {}, Topic: {}", message, destination);
        this.rocketMQTemplate.send(destination, message);
    }

    public void sendTransactionalMessage(String destination, String messageBody) {
        Message<String> message = MessageBuilder.withPayload(messageBody).build();
        TransactionSendResult transactionSendResult = this.rocketMQTemplate.sendMessageInTransaction(destination, message, null);
        // 发送状态
        String sendStatus = transactionSendResult.getSendStatus().name();
        // 本地事务执行状态
        String localTxState = transactionSendResult.getLocalTransactionState().name();
        log.info("send tx message payload:{}, sendStatus:{}, localTXState:{}", messageBody, sendStatus, localTxState);
    }

    public void sendOrderMessage(String destination) {
        for (int i = 0; i < 5; i++) {
            Message<String> message = MessageBuilder.withPayload("订单创建" + i).build();
            // 同步顺序消息
            SendResult sendResult = this.rocketMQTemplate.syncSendOrderly(destination, message, String.valueOf(i));
            log.info("发送顺序消息成功:{}", sendResult);

            message = MessageBuilder.withPayload("订单支付" + i).build();
            // 同步顺序消息
            sendResult = this.rocketMQTemplate.syncSendOrderly(destination, message, String.valueOf(i));
            log.info("发送顺序消息成功:{}", sendResult);

            message = MessageBuilder.withPayload("订单发货" + i).build();
            // 同步顺序消息
            sendResult = this.rocketMQTemplate.syncSendOrderly(destination, message, String.valueOf(i));
            log.info("发送顺序消息成功:{}", sendResult);
        }
    }

    @Autowired
    public void setRocketMQTemplate(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }
}
