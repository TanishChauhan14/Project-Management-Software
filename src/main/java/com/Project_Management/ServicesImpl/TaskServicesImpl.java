package com.Project_Management.ServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project_Management.DTO.TaskResponseDTO;
import com.Project_Management.Models.ActivityType;
import com.Project_Management.Models.Project;
import com.Project_Management.Models.Task;
import com.Project_Management.Models.TaskStatus;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.ProjectRepo;
import com.Project_Management.Repositories.TaskRepo;
import com.Project_Management.Repositories.UsersAuthRepo;
import com.Project_Management.Services.ActivityServices;
import com.Project_Management.Services.TaskServices;

@Service
public class TaskServicesImpl implements TaskServices {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UsersAuthRepo usersAuthRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private ActivityServices activityServices;

   @Override
    public TaskResponseDTO createTask(int projectid, int assignedid, Task taskdata) {
        // Find the Project and User entities. If not found, throw an exception.
        Project project = projectRepo.findById(projectid)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Users user = usersAuthRepo.findById(assignedid)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Set the relationships on the task entity
        taskdata.setAssignedto(user);
        taskdata.setStatus(TaskStatus.IN_PROGRESS);
        taskdata.setProject(project);

        // Save the task. This returns the managed entity.
        Task savedTask = taskRepo.save(taskdata);

        // Add an activity log
        activityServices.addingrecentactivity(ActivityType.TASK_ASSIGNED, user);

        // Map the saved entity to a DTO to prevent lazy-loading serialization issues.
        // This is the crucial step to fix the error.
        return new TaskResponseDTO(
            savedTask.getId(),
            savedTask.getTitle(),
            savedTask.getStatus(),
            savedTask.getAssignedto().getUsername(),
            savedTask.getProject().getName()
        );
    }
}
