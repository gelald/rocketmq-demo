package com.example.rocketmq.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * @author WuYingBin
 * Date 2022/6/6
 */
@Slf4j
@Component
public class RocketMQStreamConsumer {

    @StreamListener(Sink.INPUT)
    public void onMessage(String message) {
      log.info("received message: {}, from binding: {}", message, Sink.INPUT);
    }
}
