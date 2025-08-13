package com.Project_Management.DTO;

public class RefreshDTO {

    private String refreshToken;
    private int tokenId;

    
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public int getTokenId() {
        return tokenId;
    }
    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

}
