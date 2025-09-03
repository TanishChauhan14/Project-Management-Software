package com.Project_Management.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.DTO.UsersShowResponse.UserReponses;
import com.Project_Management.Models.TaskStatus;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetTaskDTO {

    private int taskid;
    private String tasktitle;
    private String taskdesc;
    private LocalDate createdAt;
    private LocalDate duedate;
    private LocalDateTime latestupdate;
    private MyProjectDTO ParentProject;
    private TaskStatus taskStatus;
    private UserReponses assigned_to;
    private UserReponses assigned_by;
    private int estimatedays;

}
