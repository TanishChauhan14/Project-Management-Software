package com.Project_Management.DTO.ManagerDashboardresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Teammembers {
 
    private int id;
    private String username;
    private String email;
}
