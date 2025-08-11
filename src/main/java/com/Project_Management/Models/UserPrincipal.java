package com.Project_Management.Models;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    private final Users users;

    public UserPrincipal(Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // ✅ account hamesha active
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // ✅ kabhi lock mat karo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // ✅ password expiry disable
    }

    @Override
    public boolean isEnabled() {
        return true; // ✅ user hamesha enabled
    }
}
