package com.caiya.test.kafka.controller;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.integration.kafka.support.KafkaHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by wangnan on 16/5/26.
 */
@Controller
@RequestMapping("/test")
public class KafkaController {

    private static final Logger logger = Logger.getLogger(KafkaController.class);

    @Resource
    MessageChannel inputToKafka;

    @Resource
    PollableChannel inputFromKafka;

    @RequestMapping(value = "/test")
    public @ResponseBody Object index(){
        for (int i=0;i<1000;i++){
            inputToKafka.send(MessageBuilder.withPayload("tonight Message-" + i)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, "xxx" + i)
                    .setHeader(KafkaHeaders.TOPIC, "test")
                    .build());
        }

        Message<?> received = inputFromKafka.receive(10000);
        while (received != null) {
            logger.info("======received=====" + JSON.toJSONString(received));
            GenericMessage message = (GenericMessage)received;
            logger.info("-----=-=-="+ JSON.toJSONString(message.getPayload()));
            received = inputFromKafka.receive(10000);
        }
        return "finished";
    }





}
