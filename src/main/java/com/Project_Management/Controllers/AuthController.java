package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.UsersAuthRepo;
import com.Project_Management.Services.UsersAuthServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class AuthController {

    @Autowired
    private UsersAuthServices usersAuthServices;

// Testing Api
@GetMapping("/")
public String Test() {
    return "Hello this is just testing!";
}

// Register
@PostMapping("register")
public Users Register(@RequestBody Users user) {
   
    Users registereduser = usersAuthServices.register(user);
    return registereduser;
    
}

// login 
@PostMapping("login")
public String Login(@RequestBody Users user) {
    
    return usersAuthServices.Login(user) ;
}




}
