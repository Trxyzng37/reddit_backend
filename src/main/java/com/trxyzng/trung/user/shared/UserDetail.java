package com.trxyzng.trung.user.shared;

import com.trxyzng.trung.user.shared.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
@Data
//@AllArgsConstructor
public class UserDetail implements UserDetails, Principal {
    private UserEntity userEntity;
    public UserDetail(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    public int getId() { return userEntity.getId(); }
    public String getPassword() {
        return userEntity.getPassword();
    }
    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }
    public String getEmail(){ return userEntity.getEmail(); }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
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
        return true;
    }

    public String getName(){
        return this.userEntity.getUsername();
    }
}

