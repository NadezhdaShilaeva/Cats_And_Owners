package com.shilaeva.producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class KafkaProducer {
    @Autowired
    private ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

    public Object sendAndReceive(String requestTopic, String replyTopic, Object data)
            throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>(requestTopic, data);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes()));
        RequestReplyFuture<String, Object, Object> replyFuture = replyingKafkaTemplate.sendAndReceive(record);
        SendResult<String, Object> sendResult = replyFuture.getSendFuture().get();
        System.out.println("Sent ok: " + sendResult.getRecordMetadata());
        ConsumerRecord<String, Object> consumerRecord = replyFuture.get();
        System.out.println("Return value: " + consumerRecord.value());

        return consumerRecord.value();
    }
}
