package com.trxyzng.trung.authentication.shared.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
@Data
//@AllArgsConstructor
/**
 * Contain information about a user
 */
public class UserDetail implements UserDetails, Principal {

    private UserEntity userEntity;

    public UserDetail(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public int getUid() {
        return userEntity.getUid();
    }

    public String getEmail() {
        return userEntity.getEmail();
    }

    public String getUsername() {
        return userEntity.getUsername();
    }

    public String getPassword() {
        return userEntity.getPassword();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public String getName() {
        return userEntity.getUsername();
    }
}

