package com.Project_Management.Models;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;

@Entity
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenId;
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshToken() {
        super();
    }

    public Instant getExpiry() {
        return expiry;
    }

    public RefreshToken(int tokenId, String refreshToken, Instant expiry, Users users) {
        this.tokenId = tokenId;
        this.refreshToken = refreshToken;
        this.expiry = expiry;
        this.users = users;
    }

    public int getTokenId() {
        return tokenId;
    }

    @Override
    public String toString() {
        return "RefreshToken [tokenId=" + tokenId + ", refreshToken=" + refreshToken + ", expiry=" + expiry + ", users="
                + users + "]";
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }

    private String refreshToken;
    private Instant expiry;

    @OneToOne
    private Users users;
}
