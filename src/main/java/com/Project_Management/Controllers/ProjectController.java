package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;
import com.Project_Management.Models.Project;
import com.Project_Management.Services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ProjectController {


    @Autowired
    ProjectServices projectServices;



    @PostMapping("createproject")
    public Project createProject(@RequestBody Project projectdata) {
       
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Project project = projectServices.createProject(projectdata, username);
        return project;      
    }
    
}
