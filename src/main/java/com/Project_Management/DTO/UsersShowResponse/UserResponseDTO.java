package com.Project_Management.DTO.UsersShowResponse;

import java.time.LocalDate;
import java.util.List;

import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.DTO.ManagerDashboardresponse.Teammembers;
import com.Project_Management.Models.Address;
import com.Project_Management.Models.EmployeeRoleenum;
import com.Project_Management.Models.UserRole;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder 
public class UserResponseDTO {

    private int user_id;

    private Teammembers user_details;

    private Boolean availability;

    private String avatar;

    private Boolean status;

    private LocalDate joined_at;

    private MyProjectDTO active_project;

    private Address address;

    private EmployeeRoleenum emp_role;

    private UserRole user_role;

    private int totalleaves_taken;

    private float performance;

    private float salary;

    private List<String> skills;


}
