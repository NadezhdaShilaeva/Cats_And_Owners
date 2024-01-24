package com.shilaeva.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaRequestTopicConfig {
    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("get-cat-by-id")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("get-cats-by-name")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name("get-cats-by-birthdate")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic4() {
        return TopicBuilder.name("get-cats-by-breed")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic5() {
        return TopicBuilder.name("get-cats-by-color")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic6() {
        return TopicBuilder.name("get-cats-by-name-and-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic7() {
        return TopicBuilder.name("get-cats-by-birthdate-and-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic8() {
        return TopicBuilder.name("get-cats-by-breed-and-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic9() {
        return TopicBuilder.name("get-cats-by-color-and-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic10() {
        return TopicBuilder.name("get-cats-by-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic11() {
        return TopicBuilder.name("get-cat-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic12() {
        return TopicBuilder.name("get-all-cats")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic13() {
        return TopicBuilder.name("get-cat-friends")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic14() {
        return TopicBuilder.name("create-cat")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic15() {
        return TopicBuilder.name("set-cat-name")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic16() {
        return TopicBuilder.name("set-cat-birthdate")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic17() {
        return TopicBuilder.name("set-cat-breed")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic18() {
        return TopicBuilder.name("set-cat-color")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic19() {
        return TopicBuilder.name("add-cat-friend")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic20() {
        return TopicBuilder.name("remove-cat-friend")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic21() {
        return TopicBuilder.name("delete-cat")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic22() {
        return TopicBuilder.name("get-owner-by-id")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic23() {
        return TopicBuilder.name("get-owner-by-name")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic24() {
        return TopicBuilder.name("get-owner-by-birthdate")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic25() {
        return TopicBuilder.name("get-all-owners")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic26() {
        return TopicBuilder.name("get-owner-cats")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic27() {
        return TopicBuilder.name("create-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic28() {
        return TopicBuilder.name("set-owner-name")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic29() {
        return TopicBuilder.name("set-owner-birthdate")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic30() {
        return TopicBuilder.name("add-owner-cat")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic31() {
        return TopicBuilder.name("remove-owner-cat")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic32() {
        return TopicBuilder.name("delete-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic33() {
        return TopicBuilder.name("get-user-by-id")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic34() {
        return TopicBuilder.name("get-user-by-username")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic35() {
        return TopicBuilder.name("get-owner-by-userId")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic36() {
        return TopicBuilder.name("get-active-users")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic37() {
        return TopicBuilder.name("get-not-active-users")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic38() {
        return TopicBuilder.name("get-all-users")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic39() {
        return TopicBuilder.name("register-admin")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic40() {
        return TopicBuilder.name("register-user")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic41() {
        return TopicBuilder.name("set-owner-to-user")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic42() {
        return TopicBuilder.name("delete-user")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
