package com.Project_Management.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.Project_Management.DTO.ManagerDashboardresponse.Teammembers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectTimeline {

    private LocalDateTime starDate;
    private LocalDate deadline;
    private List<Teammembers> members;
    private List<TaskDTO> Tasks;

    
}
