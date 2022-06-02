package com.example.rocketmq.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author WuYingBin
 * Date 2022/6/1
 */
@Data
@ConfigurationProperties(value = "rocketmq.producer")
public class RocketMQProducerProperties {

    private String nameserverAddress;

    private String groupName;

    private Integer maxMessageSize;

    private Integer sendMessageTimeOut;

    private Integer retryTimesWhenSendFailed;

    private Boolean enable;


    public void setNameserverAddress(String nameserverAddress) {
        this.nameserverAddress = nameserverAddress;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setMaxMessageSize(Integer maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public void setSendMessageTimeOut(Integer sendMessageTimeOut) {
        this.sendMessageTimeOut = sendMessageTimeOut;
    }

    public void setRetryTimesWhenSendFailed(Integer retryTimesWhenSendFailed) {
        this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
