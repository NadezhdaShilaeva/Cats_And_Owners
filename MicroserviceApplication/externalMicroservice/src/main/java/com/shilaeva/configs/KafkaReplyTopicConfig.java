package com.shilaeva.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaReplyTopicConfig {
    @Bean
    public NewTopic replyTopic1() {
        return TopicBuilder.name("reply-cat-by-id")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic2() {
        return TopicBuilder.name("reply-cats-by-name")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic3() {
        return TopicBuilder.name("reply-cats-by-birthdate")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic4() {
        return TopicBuilder.name("reply-cats-by-breed")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic5() {
        return TopicBuilder.name("reply-cats-by-color")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic6() {
        return TopicBuilder.name("reply-cats-by-name-and-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic7() {
        return TopicBuilder.name("reply-cats-by-birthdate-and-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic8() {
        return TopicBuilder.name("reply-cats-by-breed-and-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic9() {
        return TopicBuilder.name("reply-cats-by-color-and-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic10() {
        return TopicBuilder.name("reply-cats-by-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic11() {
        return TopicBuilder.name("reply-cat-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic12() {
        return TopicBuilder.name("reply-all-cats")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic13() {
        return TopicBuilder.name("reply-cat-friends")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic14() {
        return TopicBuilder.name("reply-create-cat")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic15() {
        return TopicBuilder.name("reply-set-cat-name")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic16() {
        return TopicBuilder.name("reply-set-cat-birthdate")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic17() {
        return TopicBuilder.name("reply-set-cat-breed")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic18() {
        return TopicBuilder.name("reply-set-cat-color")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic19() {
        return TopicBuilder.name("reply-add-cat-friend")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic20() {
        return TopicBuilder.name("reply-remove-cat-friend")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic21() {
        return TopicBuilder.name("reply-delete-cat")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic22() {
        return TopicBuilder.name("reply-owner-by-id")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic23() {
        return TopicBuilder.name("reply-owner-by-name")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic24() {
        return TopicBuilder.name("reply-owner-by-birthdate")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic25() {
        return TopicBuilder.name("reply-all-owners")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic26() {
        return TopicBuilder.name("reply-owner-cats")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic27() {
        return TopicBuilder.name("reply-create-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic28() {
        return TopicBuilder.name("reply-set-owner-name")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic29() {
        return TopicBuilder.name("reply-set-owner-birthdate")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic30() {
        return TopicBuilder.name("reply-add-owner-cat")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic31() {
        return TopicBuilder.name("reply-remove-owner-cat")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic32() {
        return TopicBuilder.name("reply-delete-owner")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic replyTopic33() {
        return TopicBuilder.name("reply-user-by-id")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic34() {
        return TopicBuilder.name("reply-user-by-username")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic35() {
        return TopicBuilder.name("reply-owner-by-userId")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic36() {
        return TopicBuilder.name("reply-active-users")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic37() {
        return TopicBuilder.name("reply-not-active-users")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic38() {
        return TopicBuilder.name("reply-all-users")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic39() {
        return TopicBuilder.name("reply-register-admin")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic40() {
        return TopicBuilder.name("reply-register-user")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic41() {
        return TopicBuilder.name("reply-set-owner-to-user")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic replyTopic42() {
        return TopicBuilder.name("reply-delete-user")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
