package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.ProjectRequestDTO;
import com.Project_Management.Models.Project;
import com.Project_Management.Services.ProjectServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ProjectController {


    @Autowired
    ProjectServices projectServices;



    @PostMapping("createproject")
    public ResponseEntity<Project> createProject(@RequestBody ProjectRequestDTO dto) {
       
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Project project = projectServices.createProject(dto, username);
        return ResponseEntity.ok(project);      
    }
    
}
