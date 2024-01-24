package com.shilaeva.configs;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> replyKafkaTemplate(
            ProducerFactory<String, Object> producerFactory,
            ConcurrentMessageListenerContainer<String, Object> repliesContainer) {
        return new ReplyingKafkaTemplate<>(producerFactory, repliesContainer);
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return props;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> requestProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public ConsumerFactory<String, Object> replyConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
                new JsonDeserializer<Object>());
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, Object> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {
        ConcurrentMessageListenerContainer<String, Object> repliesContainer =
                containerFactory.createContainer(
                        "reply-cat-by-id", "reply-cats-by-name", "reply-cats-by-color",
                        "reply-cats-by-birthdate", "reply-cats-by-breed", "reply-cats-by-color",  "reply-cats-by-owner",
                        "reply-cats-by-name-and-owner", "reply-cats-by-birthdate-and-owner",
                        "reply-cats-by-breed-and-owner", "reply-cats-by-color-and-owner", "reply-cat-owner",
                        "reply-all-cats", "reply-cat-friends", "reply-create-cat", "reply-set-cat-name",
                        "reply-set-cat-birthdate", "reply-set-cat-breed", "reply-set-cat-color", "reply-add-cat-friend",
                        "reply-remove-cat-friend", "reply-delete-cat", "reply-owner-by-id", "reply-owner-by-name",
                        "reply-owner-by-birthdate", "reply-all-owners", "reply-owner-cats", "reply-create-owner",
                        "reply-set-owner-name", "reply-set-owner-birthdate", "reply-add-owner-cat",
                        "reply-remove-owner-cat", "reply-delete-owner", "reply-user-by-id", "reply-user-by-username",
                        "reply-owner-by-userId", "reply-active-users", "reply-not-active-users", "reply-all-users",
                        "reply-register-admin", "reply-register-user", "reply-set-owner-to-user", "reply-delete-user");
        repliesContainer.setConcurrency(42);
        return repliesContainer;
    }

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }
}
