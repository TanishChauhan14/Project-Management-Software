package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.PasswordRequest;
import com.Project_Management.DTO.RefreshDTO;
import com.Project_Management.Models.RefreshToken;
import com.Project_Management.Models.Users;
import com.Project_Management.Services.RefreshTokenServices;
import com.Project_Management.Services.UsersAuthServices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    @Autowired
    private UsersAuthServices usersAuthServices;

    @Autowired
    private RefreshTokenServices refreshServices;

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
@PostMapping("logins")
public ResponseEntity<?> login(@RequestBody Users user) throws Exception {
    String jwt = usersAuthServices.login(user); // JWT generate after auth
    RefreshToken refreshToken = refreshServices.createOrUpdateRefreshToken(user.getUsername());

    Map<String, Object> response = new HashMap<>();
    response.put("jwt", jwt);
    response.put("refreshToken", refreshToken.getRefreshToken());
    response.put("tokenId", refreshToken.getTokenId());
    response.put("Expiry", refreshToken.getExpiry());

    return ResponseEntity.ok(response);
}



    // Reset PASSWORD

    @PostMapping("/resetpassword")
    public String resetpassword(@RequestBody PasswordRequest request) throws Exception {
        if (request.getNewPassword() == null || request.getNewPassword().trim().isEmpty()) {
            return "New password cannot be empty";
        }
        return usersAuthServices.resetpassword(request.getNewPassword());
    }

    // Forget

    @PostMapping("forgetpassword")
    public String forgotpass(@RequestBody PasswordRequest request) throws Exception {
        if (request.getForgetemail() == null || request.getForgetemail().trim().isEmpty()) {
            return "Email cannot be empty";
        }
        return usersAuthServices.forgetpassword(request.getForgetemail(), request.getTempPassword());
    }

    // Refresh Token
@PostMapping("refreshtoken")
public ResponseEntity<?> refreshtoken(@RequestBody RefreshDTO refreshDTO) throws Exception {
    // Validate karega, agar invalid/expired hai to exception
    refreshServices.validateToken(refreshDTO.getRefreshToken(), refreshDTO.getTokenId());

    // Agar valid hai → username nikalo
    String username = refreshServices.getUsernameFromRefreshToken(refreshDTO.getRefreshToken());

    // Naya JWT generate karo
    String newJwt = usersAuthServices.generateToken(username);

    Map<String, Object> response = new HashMap<>();
    response.put("jwt", newJwt);

    return ResponseEntity.ok(response);
}

}
