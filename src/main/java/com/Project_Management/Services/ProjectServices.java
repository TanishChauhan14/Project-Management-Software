package com.Project_Management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project_Management.Models.ActivityType;
import com.Project_Management.Models.Project;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.ProjectRepo;
import com.Project_Management.Repositories.UsersAuthRepo;

@Service
public class ProjectServices {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private ActivityServices activityServices;

    @Autowired 
    private UsersAuthRepo UsersAuthRepo;

    public Project createProject(Project project,String username){

        Users owner = UsersAuthRepo.findByUsername(username);

        if(owner == null){
            throw new RuntimeException("User not found");
        }else{
            project.setOwner(owner);

        // Members handle karo
        if (project.getMembers() != null && !project.getMembers().isEmpty()) {
            List<Integer> memberIds = project.getMembers()
                                          .stream()
                                          .map(Users::getId)
                                          .toList();
            List<Users> members = UsersAuthRepo.findAllById(memberIds);
            project.setMembers(members);
        }

            activityServices.addingrecentactivity(ActivityType.PROJECT_CREATED, owner);
            return projectRepo.save(project);
        }

    }
}
