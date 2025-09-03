package com.Project_Management.DTO.UsersShowResponse;

import java.util.List;

import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.Models.Address;
import com.Project_Management.Models.EmployeeRoleenum;
import com.Project_Management.Models.Project;
import com.Project_Management.Models.UserRole;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SetUserDTO {

    private int userid;
    private String username;
    private String email;
    private UserRole role;
    private Boolean isactive;
    private String password;
    private String avatar;
    private Boolean availability;
    private EmployeeRoleenum emp_role;
    private List<String> skills;
    private int leavesTaken;
    private float salary;
    private Address address;
    private Project activeProject;

}
