package com.Project_Management.Services;

import java.time.Instant;
import java.util.UUID;
import com.Project_Management.Repositories.UsersAuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project_Management.Models.RefreshToken;
import com.Project_Management.Repositories.RefreshTokenRepo;

@Service
public class RefreshTokenServices {

    // 5 hours in milliseconds
    public Long expirydate = 40000L;

    @Autowired
    private RefreshTokenRepo rTokenRepo;

    @Autowired
    private UsersAuthRepo usersAuthRepo;

    public RefreshToken createOrUpdateRefreshToken(String username) {
    RefreshToken refreshToken = rTokenRepo.findByUsersUsername(username).get();

    if (refreshToken != null) {
        if (refreshToken.getExpiry().isBefore(Instant.now())) {
            refreshToken.setExpiry(Instant.now().plusMillis(expirydate));
        }
        return rTokenRepo.save(refreshToken);
    }

    RefreshToken newToken = RefreshToken.builder()
            .refreshToken(UUID.randomUUID().toString())
            .expiry(Instant.now().plusMillis(expirydate))
            .users(usersAuthRepo.findByUsername(username))
            .build();

    return rTokenRepo.save(newToken);
}



    public boolean validateToken(String token, int tokenId) {
        RefreshToken refreshToken = rTokenRepo.findById(tokenId)
            .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        // Token string match check
        if (!refreshToken.getRefreshToken().equals(token)) {
            throw new RuntimeException("Invalid refresh token");
        }

        

        // Expiry check
        if (refreshToken.getExpiry().isBefore(Instant.now())) {
            rTokenRepo.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");

            
        }
        System.out.println("Current time: " + Instant.now());
        System.out.println("Token expiry: " + refreshToken.getExpiry());

        return true;
    }

    public String getUsernameFromRefreshToken(String token) {
        RefreshToken refreshToken = rTokenRepo.findByRefreshToken(token)
            .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        return refreshToken.getUsers().getUsername();
    }
}

