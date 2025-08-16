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
    @PostMapping("/register")
    public Users Register(@RequestBody Users user) {

        Users registereduser = usersAuthServices.register(user);
        return registereduser;

    }

    // login
    @PostMapping("/logins")
    public ResponseEntity<?> login(@RequestBody Users user) throws Exception {
        String jwt = usersAuthServices.login(user); 
        RefreshToken refreshToken = refreshServices.createOrUpdateRefreshToken(user.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("jwt", jwt);
        response.put("refreshToken", refreshToken.getRefreshToken());
        response.put("RefreshtokenId", refreshToken.getTokenId());
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

    @PostMapping("/forgetpassword")
    public String forgotpass(@RequestBody PasswordRequest request) throws Exception {
        if (request.getForgetemail() == null || request.getForgetemail().trim().isEmpty()) {
            return "Email cannot be empty";
        }
        return usersAuthServices.forgetpassword(request.getForgetemail(), request.getTempPassword());
    }

    // Refresh Token
    @PostMapping("/refreshtoken")
public ResponseEntity<?> refreshtoken(@RequestBody RefreshDTO refreshDTO) throws Exception {
    try {
        // 1. Validate the refresh token. This will throw an exception if it's not valid.
        refreshServices.validateToken(refreshDTO.getRefreshToken(), refreshDTO.getTokenId());
        
        // 2. If validation succeeds, get the username from the token.
        String username = refreshServices.getUsernameFromRefreshToken(refreshDTO.getRefreshToken());
        
        // 3. Generate a new JWT (access token).
        String newJwt = usersAuthServices.generateToken(username);

        // 4. Prepare the success response.
        Map<String, Object> response = new HashMap<>();
        response.put("jwt", newJwt);
        return ResponseEntity.ok(response);

    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}

    @PostMapping("/logoutuser")
    public ResponseEntity<?> logout() {
        String response = refreshServices.logoutUser();

        if (response.startsWith("Logout successful")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
