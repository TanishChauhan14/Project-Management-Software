package com.Project_Management.DTO;

import com.Project_Management.Models.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {

    private int id;
    private String title;
    private TaskStatus status;
    private String assignedToUsername;
    private String projectName;
}
