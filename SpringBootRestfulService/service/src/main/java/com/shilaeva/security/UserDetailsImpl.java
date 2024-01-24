package com.shilaeva.security;

import com.shilaeva.models.Status;
import com.shilaeva.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private boolean isActive;
    private GrantedAuthority role;
    private Long ownerId;

    public static UserDetailsImpl asUserDetails(User user) {
        Long ownerId = null;
        if (user.getOwner() != null) {
            ownerId = user.getOwner().getId();
        }

        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(),
                user.getStatus().equals(Status.ACTIVE), user.getRole(), ownerId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
