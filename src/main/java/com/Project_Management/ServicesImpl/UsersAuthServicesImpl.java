package com.Project_Management.ServicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Project_Management.DTO.UsersShowResponse.UserReponses;
import com.Project_Management.Models.ActivityType;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.UsersAuthRepo;
import com.Project_Management.Services.ActivityServices;
import com.Project_Management.Services.UsersAuthServices;

@Service
public class UsersAuthServicesImpl implements UsersAuthServices {

    @Autowired
    private UsersAuthRepo repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private Jwtgenerator jwtgenerator;

    @Autowired
    private ActivityServices activityServices;

    @Override
    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Users rs = repo.save(user);
        activityServices.addingrecentactivity(ActivityType.USER_ADDED, user);
        return rs;
    }

    @Override
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

    @Override
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

    @Override
    public String forgetpassword(String forgetemail, String tempPassword) throws Exception {
        Users users = repo.findByEmail(forgetemail);

        if (users == null || users.toString().trim().isEmpty()) {
            return "Users Not Found";
        }
        users.setPassword(encoder.encode(tempPassword));
        repo.save(users);
        SecurityContextHolder.clearContext();

        String newToken = jwtgenerator.JWTgenerator(users.getUsername());

        System.out.println(" New JWT token: " + newToken);

        System.out.println(users);

        return "Your Temporary Password is sent to your Authenticated Email.Please reset your Password within 30 mints"
                + tempPassword;
    }

    @Override
    public String generateToken(String username) throws Exception {
        return jwtgenerator.JWTgenerator(username);
    }

    @Override
    public List<UserReponses> getallUsers() {

        List<Users> AllUsers = repo.findAll();

        List<UserReponses> Response = AllUsers.stream().map(u -> new UserReponses(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getRole(),
                u.isActive())).toList();

        return Response;
    }

    @Override
    public List<UserReponses> getallUsers(int pagesize, int pagenumber) {

        Pageable p = PageRequest.of(pagenumber, pagesize);

        Page<Users> PagePost = repo.findAll(p);

        List<Users> Response = PagePost.getContent();

        List<UserReponses> users = Response.stream().map(u -> new UserReponses(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getRole(),
                u.isActive())).toList();

        return users;
    }

    @Override
    public Boolean removeUsers(int userid) {
        Optional<Users> userOpt = repo.findById(userid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users loggeduser = repo.findByUsername(username);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            user.setActive(false);
            repo.save(user);
            activityServices.addingrecentactivity(ActivityType.USER_REMOVED, loggeduser);
            return true;
        }
        return false;
    }

    @Override
    public String Updateuser(UserReponses users) {

        Optional<Users> DB = repo.findById(users.getUserid());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users loggeduser = repo.findByUsername(username);

        if (DB.isPresent()) {
            Users DBuser = DB.get();
            DBuser.setUsername(users.getUsername());
            DBuser.setEmail(users.getEmail());
            DBuser.setRole(users.getRole());
            DBuser.setActive(users.getIsactive());
            repo.save(DBuser);
            activityServices.addingrecentactivity(ActivityType.USER_UPDATED, loggeduser);
            return users.getUsername()+" Is Updated Successfully";
        }else{
            return users.getUsername()+" User not found";
        }
    }

    @Override
    public String getuserbyid(int id) {
        
        Optional<Users> user = repo.findById(id);
        UserReponses response = new UserReponses();

        if(user.isPresent()){
            Users DBuser = user.get();
            response.setUsername(DBuser.getUsername());
            response.setEmail(DBuser.getEmail());
            response.setIsactive(DBuser.isActive());
            response.setUserid(id);
            response.setRole(DBuser.getRole());

            return "Found : -{ "+response+" }";
        }else{
            return id+" Not Found";
        }
    }

}
