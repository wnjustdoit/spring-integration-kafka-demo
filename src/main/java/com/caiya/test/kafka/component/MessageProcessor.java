package com.caiya.test.kafka.component;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by wangnan on 16/6/2.
 */
@Component
public class MessageProcessor {


    private static final Logger logger = Logger.getLogger(MessageProcessor.class);

    @ServiceActivator
    public <K, V> void process(Map<K, V> payload){
        String key = null;
        for(K item : payload.keySet()) {
            key = (String) item;
        }

        Map<K, V> topic = (Map<K, V>)payload.get(key);
        List list = (List)topic.get(0);
        logger.info("----------list----------" + JSON.toJSONString(list));
    }


}
