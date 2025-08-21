package com.Project_Management.DTO.ManagerDashboardresponse;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Managerdashboard {

    private List<MyProjectDTO> myproject;
    private int completedtask;
    private int assignedproject;
    private List<Upcomingdeadline> deadline;
}
