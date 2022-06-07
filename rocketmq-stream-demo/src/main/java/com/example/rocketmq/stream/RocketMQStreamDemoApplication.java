package com.example.rocketmq.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @author WuYingBin
 * Date 2022/6/6
 */
@SpringBootApplication
@EnableBinding({Source.class, Sink.class})
public class RocketMQStreamDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketMQStreamDemoApplication.class, args);
    }
}
