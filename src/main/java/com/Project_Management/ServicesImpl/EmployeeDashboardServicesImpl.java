package com.Project_Management.ServicesImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Project_Management.DTO.EmployeeDashboardresponse.Completed_Task;
import com.Project_Management.DTO.EmployeeDashboardresponse.EmployeeDashboardDTO;
import com.Project_Management.DTO.EmployeeDashboardresponse.MyTodayTask;
import com.Project_Management.DTO.ManagerDashboardresponse.MyProjectDTO;
import com.Project_Management.DTO.ManagerDashboardresponse.Teammembers;
import com.Project_Management.Models.Task;
import com.Project_Management.Models.TaskStatus;
import com.Project_Management.Repositories.TaskRepo;
import com.Project_Management.Services.EmployeeDashboardServices;

@Service
@Transactional(readOnly = true)
public class EmployeeDashboardServicesImpl implements EmployeeDashboardServices {

    @Autowired
    private TaskRepo taskRepo;

    @Override
    public EmployeeDashboardDTO EmployeeDashboardresponse(int employeeid) {
        EmployeeDashboardDTO employeeDashboardDTO = new EmployeeDashboardDTO();
        
        // Fetch all assigned tasks in a single database query.
        List<Task> tasks = taskRepo.findByAssignedto_Id(employeeid);

        // Filter and map today's tasks directly within the stream to create the DTOs.
        // This avoids making additional database calls for each task's project.
        List<MyTodayTask> TodayTasks = tasks.stream()
            .filter(task -> task.getAssigneddate().isEqual(LocalDate.now()))
            .map(task -> {
                MyProjectDTO projectDetails = new MyProjectDTO(
                    task.getProject().getId(),
                    task.getProject().getName(),
                    task.getProject().getOwner().getUsername(),
                    task.getProject().getStatus(),
                    task.getProject().getDeliverydate(),
                    task.getProject().getMembers().stream()
                        .map(user -> new Teammembers(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail()))
                        .toList()
                );

                return new MyTodayTask(
                    task.getId(),
                    task.getTitle(),
                    projectDetails,
                    task.getDuedate(),
                    task.getStatus(),
                    (int) ChronoUnit.DAYS.between(LocalDate.now(), task.getDuedate()) * 24
                );
            }).toList();
            
        // Get metrics for the dashboard
        int totaltasks = tasks.size();
        int currenttasks = TodayTasks.size();
        int TaskTodo = (int) tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.TO_DO)
                .count();

        Completed_Task completed_Task = new Completed_Task(totaltasks, currenttasks, TaskTodo);

        employeeDashboardDTO.setMytodaytask(TodayTasks);
        employeeDashboardDTO.setTaskMetrics(completed_Task);
        
        return employeeDashboardDTO;
    }

}
