package com.Project_Management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.UsersAuthRepo;

@Service
public class UsersAuthServices {

    @Autowired
    private UsersAuthRepo repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired 
    private Jwtgenerator jwtgenerator;

    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        Users rs = repo.save(user);
        return rs;
    }

    public String login(Users user) throws Exception{
        Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtgenerator.JWTgenerator(user.getUsername());
        }else{
            return "Fail";
        }
        
    }
}
