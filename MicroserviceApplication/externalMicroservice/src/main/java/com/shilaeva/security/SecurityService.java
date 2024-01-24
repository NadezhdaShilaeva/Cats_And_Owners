package com.shilaeva.security;

import com.shilaeva.dto.OwnerDto;
import com.shilaeva.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

@Component("SecurityService")
public class SecurityService {
    @Autowired
    private KafkaProducer kafkaProducer;

    public boolean hasUserId(@AuthenticationPrincipal UserDetailsImpl user, Long userId) {
        return userId.equals(user.getId());
    }

    public boolean hasOwnerId(@AuthenticationPrincipal UserDetailsImpl user, Long ownerId) {
        return ownerId.equals(user.getOwnerId());
    }

    public boolean hasThisCat(@AuthenticationPrincipal UserDetailsImpl user, Long catId) {
        try {
            return user.getOwnerId().equals(((OwnerDto) kafkaProducer
                    .sendAndReceive("get-cat-owner", "reply-cat-owner", catId)).id());
        } catch (Exception e) {
            return false;
        }
    }
}
