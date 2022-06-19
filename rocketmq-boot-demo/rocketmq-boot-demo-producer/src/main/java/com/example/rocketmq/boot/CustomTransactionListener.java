package com.example.rocketmq.boot;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.StringMessageConverter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author WuYingBin
 * date: 2022/6/12
 */
@Slf4j
@RocketMQTransactionListener
public class CustomTransactionListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        /*Object id = message.getHeaders().get("id");
        String destination = o.toString();
        assert id != null;
        localTrans.put(id, destination);
        org.apache.rocketmq.common.message.Message msg = RocketMQUtil.convertToRocketMessage(new StringMessageConverter(), "UTF-8", destination, message);
        String tags = msg.getTags();
        if (StringUtils.contains(tags, "TagA")) {
            return RocketMQLocalTransactionState.COMMIT;
        } else if (StringUtils.contains(tags, "TagB")) {
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }*/
        log.info("开始执行本地事务");
        try {
            TimeUnit.SECONDS.sleep(1);
            int i = 1 / 0;
            log.info("执行本地事务成功");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("执行本地事务发生异常");
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        // return RocketMQLocalTransactionState.COMMIT;
        log.info("开始回查本地事务");
        try {
            log.info("回查本地事务，本地事务成功");
            TimeUnit.SECONDS.sleep(1);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("回查本地事务，本地事务不成功");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}

