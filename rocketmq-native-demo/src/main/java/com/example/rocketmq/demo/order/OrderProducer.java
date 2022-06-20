package com.example.rocketmq.demo.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author WuYingBin
 * Date 2022/6/20
 */
public class OrderProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("192.168.1.112:9876");
        producer.setProducerGroup("order-producer");
        producer.setVipChannelEnabled(false);
        producer.setDefaultTopicQueueNums(1);
        producer.start();
        for (int i = 0; i < 4; i++) {
            String body = "订单创建" + i;
            Message message = new Message("order-topic", body.getBytes(StandardCharsets.UTF_8));
            message.setKeys("key-" + i);
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);

            body = "订单支付" + i;
            message = new Message("order-topic", body.getBytes(StandardCharsets.UTF_8));
            message.setKeys("key-" + i);
            sendResult = producer.send(message);
            System.out.println(sendResult);

            body = "订单发货" + i;
            message = new Message("order-topic", body.getBytes(StandardCharsets.UTF_8));
            message.setKeys("key-" + i);
            sendResult = producer.send(message);
            /*sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> messageQueueList, Message msg, Object arg) {
                    Long id = (Long) arg;
                    //使用取模算法确定id存放到哪个队列
                    int index = (int) (id % messageQueueList.size());
                    //index就是要存放的队列的索引
                    return messageQueueList.get(index);
                }
            }, i);*/
            System.out.println(sendResult);
        }
        producer.shutdown();
    }
}
