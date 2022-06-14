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

    @GetMapping("/sendString")
    public Object sendString(String msg) {
        this.rocketMQProducer.send("TestBootTopic", msg);
        return "success";
    }

    @GetMapping("/sendWithTransaction")
    public Object sendWithTransaction(String msg) throws InterruptedException {
        this.rocketMQProducer.sendMessageInTransaction("TestBootTopic", msg);
        return "success";
    }

    @Autowired
    public void setRocketMQProducer(RocketMQProducer rocketMQProducer) {
        this.rocketMQProducer = rocketMQProducer;
    }
}
