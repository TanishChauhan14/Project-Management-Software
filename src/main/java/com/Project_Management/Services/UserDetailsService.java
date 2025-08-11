package com.Project_Management.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.UserDetailsRepo;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserDetailsRepo repo;

   @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = repo.findByUsername(username);
    if(user == null) {
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    System.out.println("🔹 Loading user for login: " + username);
    System.out.println("🔹 Hashed password from DB: " + user.getPassword());

    return new com.Project_Management.Models.UserPrincipal(user);
}




}
