package com.Project_Management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private Jwtgenerator jwtgenerator;

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Users rs = repo.save(user);
        return rs;
    }

   public String login(Users user) throws Exception {
    Users dbUser = repo.findByUsername(user.getUsername());
    if (dbUser == null) {
        System.out.println(" User not found in DB");
        return "Fail";
    }

    System.out.println("🔹 Username from request: " + user.getUsername());
    System.out.println("🔹 Raw password from request: " + user.getPassword());
    System.out.println("🔹 Hashed password in DB: " + dbUser.getPassword());

    boolean matches = encoder.matches(user.getPassword(), dbUser.getPassword());
    System.out.println(" Direct match check: " + matches);

    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    if (authentication.isAuthenticated()) {
        System.out.println("AuthenticationManager says: AUTHENTICATED");
        return jwtgenerator.JWTgenerator(user.getUsername());
    } else {
        System.out.println(" Authentication failed");
        return "Fail";
    }
}


    public String resetpassword(String newpassword) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println("Logged in as: " + username);

        Users users = repo.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        String encoded = encoder.encode(newpassword);
        users.setPassword(encoded);
        repo.save(users);

    
        SecurityContextHolder.clearContext();
        System.out.println(" Security context cleared.");

        String newToken = jwtgenerator.JWTgenerator(username);
        System.out.println(" New JWT token: " + newToken);

        return "Password reset successful. New token: " + newToken;
    }

    public String forgetpassword(String forgetemail,String tempPassword) throws Exception {
        Users users = repo.findByEmail(forgetemail);

        if(users == null || users.toString().trim().isEmpty()){
            return "Users Not Found";
        }
        users.setPassword(encoder.encode(tempPassword));
        repo.save(users);
        SecurityContextHolder.clearContext();

        String newToken = jwtgenerator.JWTgenerator(users.getUsername());

        System.out.println(" New JWT token: " + newToken);

        System.out.println(users);

        return "Your Temporary Password is sent to your Authenticated Email.Please reset your Password within 30 mints"+tempPassword ;
    }


    public String generateToken(String username)throws Exception {
    return jwtgenerator.JWTgenerator(username);
}

    

}

