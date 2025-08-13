package com.Project_Management.DTO;

public class PasswordRequest {
    private String newPassword;
    private String forgetemail;
    private String tempPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTemppassword(String temppassword) {
        this.tempPassword = temppassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getForgetemail() {
        return forgetemail;
    }

    public void setForgetemail(String forgetemail) {
        this.forgetemail = forgetemail;
    }
}
