package com.Project_Management.Services;

import jakarta.transaction.Transactional;

import com.Project_Management.Models.RefreshToken;

public interface RefreshTokenServices {

    public RefreshToken createOrUpdateRefreshToken(String username);

    public boolean validateToken(String token, int tokenId);

    public String getUsernameFromRefreshToken(String token);

    @Transactional
    // Need to add the @Transactional annotation to your logoutUser() method in the
    // RefreshTokenServices class. This tells Spring to begin a database transaction
    // before the method runs and commit it after the method successfully completes.
    public String logoutUser();
}
