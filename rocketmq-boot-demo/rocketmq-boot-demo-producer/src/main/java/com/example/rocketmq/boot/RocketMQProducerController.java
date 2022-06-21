package com.example.rocketmq.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WuYingBin
 * Date 2022/6/2
 */
@Slf4j
@RestController
@RequestMapping("/mq-producer")
public class RocketMQProducerController {
    private RocketMQProducer rocketMQProducer;

    @GetMapping("/sendCommonMessage")
    public Object sendCommonMessage(String messageBody) {
        this.rocketMQProducer.sendCommonMessage("test-common-rocketmq", messageBody);
        return "success";
    }

    @GetMapping("/sendOnewayMessage")
    public Object sendOnewayMessage(String messageBody) {
        this.rocketMQProducer.sendOnewayMessage("test-oneway-rocketmq", messageBody);
        return "success";
    }

    @GetMapping("/sendTransactionalMessage")
    public Object sendTransactionalMessage(String messageBody) {
        this.rocketMQProducer.sendTransactionalMessage("test-tx-rocketmq", messageBody);
        return "success";
    }

    @GetMapping("/sendOrderMessage")
    public Object sendOrderMessage() {
        this.rocketMQProducer.sendOrderMessage("test-orderly-rocketmq");
        return "success";
    }

    @Autowired
    public void setRocketMQProducer(RocketMQProducer rocketMQProducer) {
        this.rocketMQProducer = rocketMQProducer;
    }
}
