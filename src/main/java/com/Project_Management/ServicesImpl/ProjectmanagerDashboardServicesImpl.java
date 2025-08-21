package com.Project_Management.ServicesImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project_Management.DTO.ManagerDashboardresponse.Managerdashboard;
import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.DTO.ManagerDashboardresponse.Teammembers;
import com.Project_Management.DTO.ManagerDashboardresponse.Upcomingdeadline;
import com.Project_Management.Models.Project;
import com.Project_Management.Models.Projectstatus;
import com.Project_Management.Repositories.ProjectRepo;
import com.Project_Management.Services.ProjectmanagerDashboardServices;

@Service
public class ProjectmanagerDashboardServicesImpl implements ProjectmanagerDashboardServices {

    @Autowired
    ProjectRepo projectRepo;

    @Override
    public Managerdashboard ManagerDashboardresponse(int managerid) {

        Managerdashboard managerdashboard = new Managerdashboard();

        List<Project> projects = projectRepo.findByMembers_Id(managerid);
        System.out.println(projects.size());

        List<MyProjectDTO> myprojects = projects.stream()
                .map(project -> new MyProjectDTO(
                        project.getId(),
                        project.getName(),
                        project.getOwner().getUsername(),
                        project.getStatus(),
                        project.getDeliverydate(),
                        project.getMembers().stream()
                                .map(u -> new Teammembers(
                                        u.getId(),
                                        u.getUsername(),
                                        u.getEmail()))
                                .toList()

                )).toList();

        int completedtasks = projectRepo.countByStatus(Projectstatus.completed);

        int assignedproject = myprojects.size();

        List<Project> deadlineprojects = projectRepo
                .findByStatusIn(List.of(Projectstatus.at_risk, Projectstatus.critical));

        List<Upcomingdeadline> deadlineproject = deadlineprojects.stream()
                .map(deadproject -> new Upcomingdeadline(
                        deadproject.getId(),
                        deadproject.getName(),
                        deadproject.getDeliverydate(),
                        (int) ChronoUnit.DAYS.between(LocalDate.now(), deadproject.getDeliverydate()),
                        deadproject.getMembers().stream()
                                .map(u -> new Teammembers(
                                        u.getId(),
                                        u.getUsername(),
                                        u.getEmail()))
                                .toList()))
                .toList();

        managerdashboard.setMyproject(myprojects);
        managerdashboard.setAssignedproject(assignedproject);
        managerdashboard.setCompletedtask(completedtasks);
        managerdashboard.setDeadline(deadlineproject);

        return managerdashboard;
    }

}
