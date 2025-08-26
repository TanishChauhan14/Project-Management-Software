package com.Project_Management.DTO.UsersShowResponse;

import com.Project_Management.Models.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserReponses {

    private int userid;
    private String username;
    private String email;
    private UserRole role;
    private Boolean isactive;


}
