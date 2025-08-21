package com.Project_Management.DTO.ManagerDashboardresponse;

import java.time.LocalDate;
import java.util.List;

import com.Project_Management.Models.Projectstatus;
import com.Project_Management.Models.Users;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyProjectDTO {
 
    private int projectid;

    private String projecttitle;

    private String client;
    
    @Enumerated(EnumType.STRING)
    private Projectstatus status;

    private LocalDate deadline;

    private List<Teammembers> teammembers; 

}
