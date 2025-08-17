package com.Project_Management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project_Management.Models.ActivityType;
import com.Project_Management.Models.Project;
import com.Project_Management.Models.Task;
import com.Project_Management.Models.TaskStatus;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.ProjectRepo;
import com.Project_Management.Repositories.TaskRepo;
import com.Project_Management.Repositories.UsersAuthRepo;

@Service
public class TaskServices {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UsersAuthRepo usersAuthRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private ActivityServices activityServices;

    public Task createTask(int projectid,int assignedid,Task taskdata){
        Project project =  projectRepo.findById(projectid).orElseThrow(() -> new RuntimeException("Project not found"));
        Users user = usersAuthRepo.findById(assignedid).orElseThrow(() -> new RuntimeException("Employee not found"));
        
        taskdata.setAssignedto(user);
        taskdata.setStatus(TaskStatus.TO_DO);
        taskdata.setProject(project);

        activityServices.addingrecentactivity(ActivityType.TASK_ASSIGNED, user);
        return  taskRepo.save(taskdata);
        
    }
}
