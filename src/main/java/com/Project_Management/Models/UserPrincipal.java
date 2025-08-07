package com.Project_Management.Models;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails  {

    @Autowired
    Users users;

    public UserPrincipal(Users users){
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         return Collections.singleton(new SimpleGrantedAuthority("Users"));
    }

    @Override
    public String getPassword() {
        
        return users.getPassword();
    }

    @Override
    public String getUsername() {
       
        return users.getUsername();
    }

}
