package com.Project_Management.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project_Management.DTO.ProjectRequestDTO;
import com.Project_Management.Models.ActivityType;
import com.Project_Management.Models.Project;
import com.Project_Management.Models.Projectstatus;
import com.Project_Management.Models.Users;
import com.Project_Management.Models.rankproject;
import com.Project_Management.Repositories.ProjectRepo;
import com.Project_Management.Repositories.UsersAuthRepo;
import com.Project_Management.Services.ActivityServices;
import com.Project_Management.Services.ProjectServices;

@Service
public class ProjectServicesImpl implements ProjectServices {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private ActivityServices activityServices;

    @Autowired 
    private UsersAuthRepo usersAuthRepo;

    @Override
   public Project createProject(ProjectRequestDTO dto, String username) {

    // Logged-in user ko owner set kar rahe ho
    Users owner = usersAuthRepo.findByUsername(username);
    if (owner == null) {
        throw new RuntimeException("User not found");
    }

    Project project = new Project();
    project.setName(dto.getName());
    project.setDescription(dto.getDescription());
    project.setOwner(owner);
    project.setDeliverydate(dto.getDeliverydate());
    project.setClientrequirement(dto.getClientrequirement());
    project.setLevelofproject(rankproject.valueOf(dto.getLevelofproject().toUpperCase())); // enum convert
    project.setReferencewebsite(dto.getReferencewebsite());
    project.setApproveddesign(dto.isApproveddesign());
    project.setStatus(Projectstatus.valueOf(dto.getStatus()));

    // ✅ members add karo
    if (dto.getMembers() != null && !dto.getMembers().isEmpty()) {
        List<Users> members = usersAuthRepo.findAllById(dto.getMembers());
        project.setMembers(members);
    }

    activityServices.addingrecentactivity(ActivityType.PROJECT_CREATED, owner);

    return projectRepo.save(project);
}

}
