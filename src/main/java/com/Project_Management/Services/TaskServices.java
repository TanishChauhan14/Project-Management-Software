package com.Project_Management.Services;

import org.springframework.stereotype.Service;

import com.Project_Management.DTO.TaskResponseDTO;

import com.Project_Management.Models.Task;

@Service
public interface TaskServices {

    public TaskResponseDTO createTask(int projectid, int assignedid, Task taskdata);
}
