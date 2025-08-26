package com.Project_Management.Services;

import java.util.List;

import com.Project_Management.DTO.UsersShowResponse.UserReponses;
import com.Project_Management.Models.Users;

public interface UsersAuthServices {

    public Users register(Users user);

    public String login(Users user) throws Exception;

    public String resetpassword(String newpassword) throws Exception;

    public String forgetpassword(String forgetemail, String tempPassword) throws Exception;

    public String generateToken(String username)throws Exception;

    public List<UserReponses> getallUsers();

    public List<UserReponses> getallUsers(int pagesize,int pagenumber);

    public String getuserbyid(int id);

    public Boolean removeUsers(int usersid);

    public String Updateuser(UserReponses users);
}
