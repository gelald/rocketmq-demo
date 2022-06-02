package com.example.rocketmq.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @author WuYingBin
 * Date 2022/6/2
 */
@Slf4j
@RestController
@RequestMapping("/mq-producer")
public class RocketMQProducerController {
    private DefaultMQProducer defaultMQProducer;

    @GetMapping("/sendString")
    public Object send(String msg) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        if (!StringUtils.hasText(msg)) {
            return "success";
        }
        log.info("发送消息到MQ: {}", msg);
        Message message = new Message("TestTopic", "TestTag", msg.getBytes(StandardCharsets.UTF_8));
        SendResult sendResult = defaultMQProducer.send(message);
        log.info("消息发送相应: {}", sendResult.toString());
        return "success";
    }

    @Autowired
    public void setDefaultMQProducer(DefaultMQProducer defaultMQProducer) {
        this.defaultMQProducer = defaultMQProducer;
    }
}
