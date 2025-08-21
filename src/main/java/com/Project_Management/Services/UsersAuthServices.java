package com.Project_Management.Services;

import com.Project_Management.Models.Users;

public interface UsersAuthServices {

    public Users register(Users user);

    public String login(Users user) throws Exception;

    public String resetpassword(String newpassword) throws Exception;

    public String forgetpassword(String forgetemail, String tempPassword) throws Exception;

     public String generateToken(String username)throws Exception;

}
