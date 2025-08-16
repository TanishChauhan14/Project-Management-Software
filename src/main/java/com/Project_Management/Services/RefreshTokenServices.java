package com.Project_Management.Services;

import java.time.Instant;
import java.util.UUID;
import com.Project_Management.Repositories.UsersAuthRepo;

import jakarta.transaction.Transactional;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Project_Management.Models.RefreshToken;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.RefreshTokenRepo;

@Service
public class RefreshTokenServices {

    public Long expirydate = 80000L;

    @Autowired
    private RefreshTokenRepo rTokenRepo;

    @Autowired
    private UsersAuthRepo usersAuthRepo;


    

    public RefreshToken createOrUpdateRefreshToken(String username) {
        Optional<RefreshToken> existingTokenOptional = rTokenRepo.findByUsersUsername(username);

        if (existingTokenOptional.isPresent()) {

            RefreshToken refreshToken = existingTokenOptional.get();

            if (refreshToken.getExpiry().isBefore(Instant.now())) {
                refreshToken.setExpiry(Instant.now().plusMillis(expirydate));
            }
            return rTokenRepo.save(refreshToken);
        } else {
            Users user = usersAuthRepo.findByUsername(username);
            if (user == null) {
                throw new RuntimeException("User not found: " + username);
            }

            RefreshToken newToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(expirydate))
                    .users(user)
                    .build();
            return rTokenRepo.save(newToken);
        }
    }




    public boolean validateToken(String token, int tokenId) {
        RefreshToken refreshToken = rTokenRepo.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (!refreshToken.getRefreshToken().equals(token)) {
            throw new RuntimeException("Invalid refresh token");
        }

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



    @Transactional
    // Need to add the @Transactional annotation to your logoutUser() method in the RefreshTokenServices class. This tells Spring to begin a database transaction before the method runs and commit it after the method successfully completes.
    public String logoutUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = usersAuthRepo.findByUsername(username);

        long count = rTokenRepo.deleteByUsers(user);

        if (count > 0) {
            return "Logout successful for user: " + user.getUsername();
        } else {
            return "No refresh token found for this user.";
        }
    }
}
