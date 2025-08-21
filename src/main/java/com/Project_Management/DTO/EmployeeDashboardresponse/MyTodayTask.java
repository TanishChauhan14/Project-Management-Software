package com.Project_Management.DTO.EmployeeDashboardresponse;

import java.time.LocalDate;

import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.Models.TaskStatus;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyTodayTask {

    private int taskid;
    private String tasktitle;
    private MyProjectDTO projectdetails;
    private LocalDate duedate;
    private TaskStatus status;
    private int estimatedays;
}
