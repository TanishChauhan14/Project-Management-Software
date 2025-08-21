package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.TaskResponseDTO;
import com.Project_Management.Models.Task;
import com.Project_Management.Services.TaskServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class TaskController {

    @Autowired
    TaskServices taskServices;

    @PostMapping("createtask")
    public TaskResponseDTO createTask(@RequestBody Task task) {
        
    int projectid = task.getProject().getId();
    int assignedid = task.getAssignedto().getId();
    return taskServices.createTask(projectid, assignedid, task);
    }
    
}
