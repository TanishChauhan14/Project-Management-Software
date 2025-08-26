package com.Project_Management.DTO;

import java.time.LocalDate;

import com.Project_Management.DTO.UsersShowResponse.UserReponses;
import com.Project_Management.Models.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private int taskid;
    private LocalDate assignedon;
    private String taskdesp;
    private TaskStatus task_status;
    private UserReponses assignedto; 
}
