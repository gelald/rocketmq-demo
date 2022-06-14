package com.example.rocketmq.demo.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;

/**
 * @author WuYingBin
 * date: 2022/6/15
 */
public class TransactionalProducer {
    public static void main(String[] args) throws MQClientException {
        // 设置生产者组、NameServer地址等基本信息
        TransactionMQProducer producer = new TransactionMQProducer("transaction-producer");
        producer.setNamesrvAddr("localhost:9876");
        // 设置MQ事务监听器
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                System.out.println("接收到 MQ 的 half 消息响应，执行本地事务");
                return LocalTransactionState.UNKNOW;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("MQ 长时间无法收到消息的状态，执行补偿事务");
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        });
        // 生产者启动
        producer.start();
        // 消息发送
        Message msg = new Message("transaction-topic", "tag1", ("Hello RocketMQ transaction").getBytes(StandardCharsets.UTF_8));
        SendResult sendResult = producer.sendMessageInTransaction(msg, null);
        System.out.println("发送成功" + sendResult);
    }
}
