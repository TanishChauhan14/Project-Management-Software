package com.Project_Management.ServicesImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Project_Management.DTO.GetTaskDTO;
import com.Project_Management.DTO.TaskResponseDTO;
import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.DTO.ManagerDashboardresponse.Teammembers;
import com.Project_Management.DTO.UsersShowResponse.UserReponses;
import com.Project_Management.Models.ActivityType;
import com.Project_Management.Models.Project;
import com.Project_Management.Models.Task;
import com.Project_Management.Models.TaskStatus;
import com.Project_Management.Models.UserRole;
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
    public TaskResponseDTO createTask(int projectid, int assignedto, Task taskdata) {
        // Find the Project and User entities. If not found, throw an exception.
        Project project = projectRepo.findById(projectid)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Users user = usersAuthRepo.findById(assignedto)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Check if assigned To user is part of project members
        boolean isMember = project.getMembers().stream()
                .anyMatch(member -> member.getId() == user.getId());

        if (!isMember) {
            throw new RuntimeException("This user is not a member of the project");
        }

        // Get currently logged in user (assignedBy)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users assignedby = usersAuthRepo.findByUsername(username);

        // Check if assigned To user is part the manager of project.
        boolean isManager = project.getMembers().stream()
                .anyMatch(member -> member.getId() == assignedby.getId() && member.getRole() == UserRole.MANAGER);

        if (!isManager) {
            throw new RuntimeException("This user is not a manager of the project");
        }

        // Set the relationships on the task entity
        taskdata.setAssignedTo(user);
        taskdata.setAssignedBy(assignedby);
        taskdata.setStatus(TaskStatus.IN_PROGRESS);
        taskdata.setProject(project);

        // Save the task. This returns the managed entity.
        Task savedTask = taskRepo.save(taskdata);

        // Add an activity log
        activityServices.addingrecentactivity(ActivityType.TASK_ASSIGNED, assignedby);

        // Map the saved entity to a DTO to prevent lazy-loading serialization issues.
        return new TaskResponseDTO(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getStatus(),
                savedTask.getAssignedTo().getUsername(),
                savedTask.getProject().getName());
    }

    @Override
    public List<GetTaskDTO> taskByProjectId(int project_id) {
        List<Task> task = taskRepo.findByProject_Id(project_id);

        List<GetTaskDTO> Tasks = task.stream().map(t -> new GetTaskDTO(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getCreatedAt(),
                t.getDuedate(),
                t.getUpdatedAt(),
                new MyProjectDTO(
                        t.getProject().getId(),
                        t.getProject().getName(),
                        t.getProject().getOwner().getUsername(),
                        t.getProject().getStatus(),
                        t.getProject().getDeliverydate(),
                        getprojectmembers(t)),
                t.getStatus(),
                new UserReponses(
                        t.getAssignedTo().getId(),
                        t.getAssignedTo().getUsername(),
                        t.getAssignedTo().getEmail(),
                        t.getAssignedTo().getRole(),
                        t.getAssignedTo().isActive()),
                new UserReponses(
                        t.getAssignedBy().getId(),
                        t.getAssignedBy().getUsername(),
                        t.getAssignedBy().getEmail(),
                        t.getAssignedBy().getRole(),
                        t.getAssignedBy().isActive()),
                (int) ChronoUnit.HOURS.between(LocalDateTime.now(), t.getDuedate().atStartOfDay())

        )).toList();

        return Tasks;

    }

    public List<Teammembers> getprojectmembers(Task task) {
        List<Teammembers> members = task.getProject()
                .getMembers()
                .stream()
                .map(m -> new Teammembers(
                        m.getId(),
                        m.getUsername(),
                        m.getEmail()))
                .toList();

        return members;
    }

    @Override
    public String getTaskbyid(int taskid) {
        Optional<Task> optask = taskRepo.findById(taskid);

        if (optask.isPresent()) {
            Task t = optask.get();
            GetTaskDTO Tasks = new GetTaskDTO(
                    t.getId(),
                    t.getTitle(),
                    t.getDescription(),
                    t.getCreatedAt(),
                    t.getDuedate(),
                    t.getUpdatedAt(),
                    new MyProjectDTO(
                            t.getProject().getId(),
                            t.getProject().getName(),
                            t.getProject().getOwner().getUsername(),
                            t.getProject().getStatus(),
                            t.getProject().getDeliverydate(),
                            getprojectmembers(t)),
                    t.getStatus(),
                    new UserReponses(
                            t.getAssignedTo().getId(),
                            t.getAssignedTo().getUsername(),
                            t.getAssignedTo().getEmail(),
                            t.getAssignedTo().getRole(),
                            t.getAssignedTo().isActive()),
                    new UserReponses(
                            t.getAssignedBy().getId(),
                            t.getAssignedBy().getUsername(),
                            t.getAssignedBy().getEmail(),
                            t.getAssignedBy().getRole(),
                            t.getAssignedBy().isActive()),
                    (int) ChronoUnit.HOURS.between(LocalDateTime.now(), t.getDuedate().atStartOfDay()));

            return "Here is the Task/n " + Tasks;

        } else {
            throw new RuntimeException("Task Not Found");
        }
    }

    @Override
    public List<GetTaskDTO> getalltask() {
        List<Task> tasks = taskRepo.findAll();
        List<GetTaskDTO> Tasks = tasks.stream().map(t -> new GetTaskDTO(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getCreatedAt(),
                t.getDuedate(),
                t.getUpdatedAt(),
                new MyProjectDTO(
                        t.getProject().getId(),
                        t.getProject().getName(),
                        t.getProject().getOwner().getUsername(),
                        t.getProject().getStatus(),
                        t.getProject().getDeliverydate(),
                        getprojectmembers(t)),
                t.getStatus(),
                new UserReponses(
                        t.getAssignedTo().getId(),
                        t.getAssignedTo().getUsername(),
                        t.getAssignedTo().getEmail(),
                        t.getAssignedTo().getRole(),
                        t.getAssignedTo().isActive()),
                new UserReponses(
                        t.getAssignedBy().getId(),
                        t.getAssignedBy().getUsername(),
                        t.getAssignedBy().getEmail(),
                        t.getAssignedBy().getRole(),
                        t.getAssignedBy().isActive()),
                (int) ChronoUnit.HOURS.between(LocalDateTime.now(), t.getDuedate().atStartOfDay())

        )).toList();

        return Tasks;

    }

    @Override
    public TaskResponseDTO updatetask(int id, TaskResponseDTO task) {
        
        Optional<Task> optask = taskRepo.findById(id);
        if (optask.isPresent()) {
            Task dbtask = optask.get();
            dbtask.setTitle(task.getTitle());
            dbtask.setStatus(task.getStatus());
            dbtask.setAssignedTo(usersAuthRepo.findByUsername(task.getAssignedToUsername()));

            taskRepo.save(dbtask);
        
            task.setProjectName(dbtask.getProject().getName());

            return task;
        }
        else{
                throw new RuntimeException("Task not found");
        }
    }

}
