package com.Project_Management.ServicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Project_Management.DTO.ProjectRequestDTO;
import com.Project_Management.DTO.ProjectTimeline;
import com.Project_Management.DTO.TaskDTO;
import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.DTO.ManagerDashboardresponse.Teammembers;
import com.Project_Management.DTO.UsersShowResponse.UserReponses;
import com.Project_Management.Models.ActivityType;
import com.Project_Management.Models.Project;
import com.Project_Management.Models.Projectstatus;
import com.Project_Management.Models.Users;
import com.Project_Management.Models.rankproject;
import com.Project_Management.Repositories.ProjectRepo;
import com.Project_Management.Repositories.UsersAuthRepo;
import com.Project_Management.Services.ActivityServices;
import com.Project_Management.Services.ProjectServices;

import jakarta.transaction.Transactional;

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

    // Get All Project

    @Override
    public List<MyProjectDTO> getallproject() {

        List<Project> allProjects = projectRepo.findAll();

        List<MyProjectDTO> response = allProjects.stream().map(p -> new MyProjectDTO(
                p.getId(),
                p.getName(),
                p.getOwner().getUsername(),
                p.getStatus(),
                p.getDeliverydate(),
                p.getMembers().stream().map(m -> new Teammembers(
                        m.getId(),
                        m.getUsername(),
                        m.getEmail())).toList()))
                .toList();

        return response;

    }

    @Override
    public List<MyProjectDTO> getallprojectpagination(int pagesize, int pagenumber) {

        Pageable page = PageRequest.of(pagenumber, pagesize);

        Page<Project> pages = projectRepo.findAll(page);

        List<Project> rs = pages.getContent();

        List<MyProjectDTO> project = rs.stream()
                .map(p -> new MyProjectDTO(
                        p.getId(),
                        p.getName(),
                        p.getOwner().getUsername(),
                        p.getStatus(),
                        p.getDeliverydate(),

                        p.getMembers().stream().map(m -> new Teammembers(
                                m.getId(),
                                m.getUsername(),
                                m.getEmail())).toList()

                )).toList();

        return project;
    }

    @Override
    public String getprojectbyid(int id) {
        Optional<Project> project = projectRepo.findById(id);

        if (project.isPresent()) {

            Project p = project.get();

            MyProjectDTO projectDTO = new MyProjectDTO(
                    p.getId(),
                    p.getName(),
                    p.getOwner().getUsername(),
                    p.getStatus(),
                    p.getDeliverydate(),
                    p.getMembers().stream().map(m -> new Teammembers(
                            m.getId(), m.getUsername(), m.getEmail())).toList());

            return projectDTO + " ";

        }

        return "Failed To Find User";
    }

    @Override
    @Transactional
    public Boolean delete(int id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = usersAuthRepo.findByUsername(username);

        Optional<Users> user2 = usersAuthRepo.findById(id);
        if (user2.isPresent()) {
            projectRepo.deleteById(id);
            activityServices.addingrecentactivity(ActivityType.PROJECT_DELETED, user);
            return true;
        }

        return false;

    }

    @Override
    public String updateproject(MyProjectDTO projectdetails) {

        Optional<Project> opproject = projectRepo.findById(projectdetails.getProjectid());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = usersAuthRepo.findByUsername(username);
        if (opproject.isPresent()) {
            Project project = opproject.get();

            project.setName(projectdetails.getProjecttitle());
            project.setDeliverydate(projectdetails.getDeadline());
            project.setStatus(projectdetails.getStatus());

            List<Integer> membersids = projectdetails.getTeammembers()
                    .stream()
                    .map(Teammembers::getId)
                    .collect(Collectors.toList());

            List<Users> members = usersAuthRepo.findAllById(membersids);
            project.setMembers(members);

            projectRepo.save(project);
            activityServices.addingrecentactivity(ActivityType.PROJECT_UPDATED, user);
            return projectdetails.getProjecttitle() + " update succesfully";

        }

        return "Error in Updation";
    }

    @Override
    public ProjectTimeline projecttimeline(int id) {
        Optional<Project> opproject = projectRepo.findById(id);

        if(opproject.isPresent()){
            Project project = opproject.get();

            return new ProjectTimeline(


                project.getStartdate(),

                project.getDeliverydate(),

                project.getMembers().stream()
                        .map(m -> new Teammembers(
                            m.getId(),
                            m.getUsername(),
                            m.getEmail()
                        )).collect(Collectors.toList()),

                project.getTasks()
                       .stream()
                       .map(t -> new TaskDTO(
                        t.getId(),
                        t.getCreatedAt(),
                        t.getDescription(),
                        t.getStatus(),
                        new UserReponses(
                            t.getAssignedTo().getId(),
                            t.getAssignedTo().getUsername(),
                            t.getAssignedTo().getEmail(),
                            t.getAssignedTo().getRole(),
                            t.getAssignedTo().isActive()
                        )
                        
                       )).collect(Collectors.toList())
            );

           
        }
         return null ;
    }

}
