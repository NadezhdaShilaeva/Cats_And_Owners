package com.shilaeva.security;

import com.shilaeva.exceptions.CatException;
import com.shilaeva.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

@Component("SecurityService")
public class SecurityService {
    @Autowired
    private CatService catService;

    public boolean hasUserId(@AuthenticationPrincipal UserDetailsImpl user, Long userId) {
        return userId.equals(user.getId());
    }

    public boolean hasOwnerId(@AuthenticationPrincipal UserDetailsImpl user, Long ownerId) {
        return ownerId.equals(user.getOwnerId());
    }

    public boolean hasThisCat(@AuthenticationPrincipal UserDetailsImpl user, Long catId) {
        try {
            return user.getOwnerId().equals(catService.getOwner(catId).id());
        } catch (CatException e) {
            return false;
        }
    }
}
