package com.example.rocketmq.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author WuYingBin
 * Date 2022/6/1
 */
@Data
@ConfigurationProperties(value = "rocketmq.consumer")
public class RocketMQConsumerProperties {

    private String nameserverAddress;

    private String groupName;

    private String topics;

    private String tag;

    private Integer consumeThreadMin;

    private Integer consumeThreadMax;

    private Integer consumeMessageBatchMaxSize;

    private Boolean enable;


    public void setNameserverAddress(String nameserverAddress) {
        this.nameserverAddress = nameserverAddress;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setConsumeThreadMin(Integer consumeThreadMin) {
        this.consumeThreadMin = consumeThreadMin;
    }

    public void setConsumeThreadMax(Integer consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }

    public void setConsumeMessageBatchMaxSize(Integer consumeMessageBatchMaxSize) {
        this.consumeMessageBatchMaxSize = consumeMessageBatchMaxSize;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
